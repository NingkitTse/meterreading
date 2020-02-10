package com.wasion.meterreading.task.process.telecom;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wasion.meterreading.constant.SystemConstant;
import com.wasion.meterreading.domain.dto.ResolveContext;
import com.wasion.meterreading.domain.dto.telecom.TelecomContext;
import com.wasion.meterreading.entity.RtTaskOnlineV1910;
import com.wasion.meterreading.entity.TCfgAppsConfig;
import com.wasion.meterreading.entity.TCfgProtocolCmd;
import com.wasion.meterreading.entity.freezedata.DayFreezeDataValue;
import com.wasion.meterreading.entity.freezedata.PointFreezeDataValue;
import com.wasion.meterreading.exception.MRException;
import com.wasion.meterreading.service.impl.telecom.ddc.TelecomDdcResolver;
import com.wasion.meterreading.task.context.CommandContext;
import com.wasion.meterreading.task.process.CommandSender;
import com.wasion.meterreading.util.ContextProvider;
import com.wasion.meterreading.util.DateUtil;

import lombok.extern.slf4j.Slf4j;
import telecom.sdk.service.dataCollection.QueryDeviceHistoryData;
import telecom.sdk.utils.HttpsUtil;

/**
 * 电信历史上报数据查询Sender
 * 日冻结、点冻结、月冻结查询可现行查看上报历史记录，若历史记录没有查询到才去下命令
 *
 * @author w24882 xieningjie
 * @date 2020年1月7日
 */
@Component
@Slf4j
public class TelecomQueryHisSender implements CommandSender {

    private static HashMap<String, String> map = new HashMap<>();

    static {
        map.put("DailyReport", "GET_DAY_FREEZE_DATA");
        map.put("DotReport", "GET_SEGMENT_FREEZE_DATA");
    }

    @Autowired
    private TelecomDdcResolver telecomDdcResolver;

    /**
     * <pre>
     * {
     *     "totalCount": 5,
     *     "pageNo": 0,
     *     "pageSize": 1,
     *     "deviceDataHistoryDTOs": [
     *         {
     *             "deviceId": "63ca9d2a-ada8-47df-a72d-cd8472cbf87c",
     *             "gatewayId": "63ca9d2a-ada8-47df-a72d-cd8472cbf87c",
     *             "appId": "j9iPSi4dTBEIFG9_Uif5zMisH9ca",
     *             "serviceId": "DailyReport",
     *             "data": {
     *                 "meterEleno": "00113037231817",
     *                 "totalconsumepower": 4294967295,
     *                 "maxworkelectricflow": 65535,
     *                 "minworkelectricflow": 65535,
     *                 "batteryVoltage": "3.65",
     *                 "signalStrength": "-103",
     *                 "SNR": "-1",
     *                 "state": 384,
     *                 "currentReading": 4294967295,
     *                 "dailyActivityTime": 41,
     *                 "Dailyreporttime": "2001070000",
     *                 "Dailydata01": 1000,
     *                 "Dailydata02": 1000,
     *                 "Dailydata03": 1000,
     *                 "Dailydata04": 1000,
     *                 "Dailydata05": 1000,
     *                 "Dailydata06": 4294967295,
     *                 "Dailydata07": 4294967295,
     *                 "MonthFreezeData": 4294967295
     *             },
     *             "timestamp": "20200106T175149Z"
     *         }
     *     ]
     * }
     * </pre>
     *
     * @param context 命令上下文
     * @throws MRException
     */
    @Override
    public void sendCommand(CommandContext context) throws MRException {
        TCfgProtocolCmd protocolCmd = context.getProtocolCmd();
        String method = protocolCmd.getMethod();
        switch (method) {
            case "GET_DAY_FREEZE_DATA": // 日冻结查询
            case "GET_SEGMENT_FREEZE_DATA":
                TCfgAppsConfig appConfig = context.getAppConfig();
                String appid = appConfig.getAppid();
                String baseUrl = appConfig.getBaseUrl();
                String secret = appConfig.getSecret();
                RtTaskOnlineV1910 task = context.getTask();
                String paramJsonStr = task.getParamJsonStr();
                JSONObject jsonObject = JSON.parseObject(paramJsonStr);

                Date startTime = null;
                Date endTime = null;
                Calendar c = Calendar.getInstance();
                try {
                    startTime = DateUtil.parseDataYYMMDDHHMM(jsonObject.getString("dayfreezingstarttime"));
                    c.setTime(startTime);
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    startTime = c.getTime();
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    endTime = c.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String deviceId = context.getDeviceId();

                QueryDeviceHistoryData create = new QueryDeviceHistoryData().build()
                        .setAppid(appid).setBaseUrl(baseUrl).setSecret(secret).setDeviceId(deviceId)
                        .setStartTime(DateUtil.formatDateYYYYMMTDDHHMMSSZ(startTime))
                        .setEndTime(DateUtil.formatDateYYYYMMTDDHHMMSSZ(endTime))
                        .create();
                HttpResponse response = null;
                String httpResponseBody = "";
                try {
                    response = create.process();
                    StatusLine statusLine = response.getStatusLine();
                    httpResponseBody = HttpsUtil.getInstance().getHttpResponseBody(response);

                    int statusCode = statusLine.getStatusCode();
                    if (200 != statusCode) { // 查询接口调用失败
                        log.info("Query history failed. StatusCode: {}.", statusCode);
                        context.process();
                        return;
                    }

                    JSONObject rspObj = JSON.parseObject(httpResponseBody);
                    Long totalCount = rspObj.getLong("totalCount");
                    if (totalCount == 0) { // 查询接口调用成功，但为查询到历史记录
                        log.info("No history record found.");
                        context.process();
                        return;
                    }

                    // 获取当日最晚记录
                    JSONArray deviceDataHistoryDTOs = rspObj.getJSONArray("deviceDataHistoryDTOs");
                    Date lastDate = null;
                    JSONObject lastDto = null;
                    boolean querySucceed = false;
                    for (int i = 0; i < totalCount; ++i) {
                        JSONObject dto = deviceDataHistoryDTOs.getJSONObject(i);
                        String timestamp = dto.getString("timestamp");
                        Date time = DateUtil.parseDateyyyyMMddTHHmmssZ(timestamp);
                        String serviceId = dto.getString("serviceId");
                        String dtoMethod = map.get(serviceId);
                        if (!method.equals(dtoMethod)) {
                            continue;
                        }
                        querySucceed = true;
                        if (lastDate == null || time.after(lastDate)) {
                            lastDto = dto;
                            lastDate = time;
                        }
                    }

                    if (querySucceed) {
                        processHisData(task, context, deviceId, lastDto);
                    } else {
                        log.info("No valid history data found.");
                        context.process();
                    }
                    return;
                } catch (Exception e) {
                    log.error("Query history failed, traceId: {}.", task.getTraceId(), e);
                    context.process();
                } finally {
                    log.info("Query history req: {}, resp: {}", create, httpResponseBody);
                }
                break;
            default:
                context.process();
                break;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void processHisData(RtTaskOnlineV1910 task, CommandContext context, String deviceId, JSONObject lastDto) {
        JSONObject request = new JSONObject();
        JSONArray services = new JSONArray();
        services.add(lastDto);
        request.put("deviceId", deviceId);
        request.put("services", services);
        // 解析数据
        TelecomContext telecomContext = new TelecomContext();
        telecomContext.setDeviceId(deviceId);
        telecomContext.setData(request);
        telecomDdcResolver.resolve(telecomContext);

        // 转化解析数据为命令响应
        List valueList = (List) telecomContext.get(ResolveContext.DataMapKey.FREEZE_DATA_LIST_KEY);
        Object values = valueList.stream().map(e -> {
            if (e instanceof DayFreezeDataValue) {
                DayFreezeDataValue value = (DayFreezeDataValue) e;
                return value.getFreezenValue() + "," + DateUtil.formatDateYYYYMMTDDHHMMSS(value.getId().getFreezenDate());
            } else if (e instanceof PointFreezeDataValue) {
                DayFreezeDataValue value = (DayFreezeDataValue) e;
                return value.getFreezenValue() + "," + DateUtil.formatDateYYYYMMTDDHHMMSS(value.getId().getFreezenDate());
            } else
                return "";
        }).collect(Collectors.joining("|"));
        task.setReturnData(valueList.size() + SystemConstant.TASK_RETURN_DATA_SEPERATOR + values);
        context.success(task, "SUCCESS", null);
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public CommandSender next() {
        return ContextProvider.getBean(TelecomCmdSender.class);
    }

}
