package com.wasion.meterreading.task.process.mobile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.wasion.meterreading.config.OnenetProperties;
import com.wasion.meterreading.constant.EcasConstant;
import com.wasion.meterreading.entity.RtTaskOnlineV1910;
import com.wasion.meterreading.entity.TCfgAppsConfig;
import com.wasion.meterreading.entity.TCfgProtocolCmd;
import com.wasion.meterreading.entity.TCfgProtocolCmdParam;
import com.wasion.meterreading.entity.TRunMeter;
import com.wasion.meterreading.entity.TRunMeterDocu;
import com.wasion.meterreading.entity.TRunMeterDocuId;
import com.wasion.meterreading.exception.MRException;
import com.wasion.meterreading.exception.MRExceptionEnum;
import com.wasion.meterreading.orm.jpa.RtTaskOnlineV1910Repository;
import com.wasion.meterreading.orm.jpa.TCfgProtocolCmdParamRepository;
import com.wasion.meterreading.orm.jpa.TRunMeterDocuRepository;
import com.wasion.meterreading.orm.jpa.TRunMeterRepository;
import com.wasion.meterreading.task.context.CommandContext;
import com.wasion.meterreading.task.process.CommandSender;
import com.wasion.meterreading.task.process.telecom.TelecomCmdSender;
import com.wasion.meterreading.util.ParseUtil;

import cmcciot.onenet.nbapi.sdk.api.online.ExecuteOpe;
import cmcciot.onenet.nbapi.sdk.entity.WriteOfflineV1910;

/**
 * 移动通用命令下发Sender
 *
 * @author w24882 xieningjie
 * @date 2020年1月7日
 */
@EnableConfigurationProperties(value = OnenetProperties.class)
@Component
public class MobileCmdSender implements CommandSender {

    private static final Logger LOG = LoggerFactory.getLogger(TelecomCmdSender.class);
    @Autowired
    private TCfgProtocolCmdParamRepository cmdParamRepo; // 命令参数仓库
    @Autowired
    private TRunMeterDocuRepository docuRepo; // 表计拓展参数仓库
    @Autowired
    private TRunMeterRepository meterRepo;
    @Autowired
    private RtTaskOnlineV1910Repository taskRepo;
    @Autowired
    private OnenetProperties properties;

    @Override
    public void sendCommand(CommandContext context) {
        String snMeter = context.getSnMeter();
        String cmdId = context.getCmdId();
        String parameters = context.getParameters();
        TCfgProtocolCmd protocolCmd = context.getProtocolCmd();
        RtTaskOnlineV1910 task = context.getTask();
        TCfgAppsConfig appConfig = context.getAppConfig();
        String appid = appConfig.getAppid();

        List<TCfgProtocolCmdParam> cmdParms = cmdParamRepo.findByCmdIdAndParamTypeOrderByParamNo(cmdId, "1");
        TRunMeter tRunMeter = meterRepo.findById(snMeter).get();
        String meterNo = tRunMeter.getMeterNo();

        // 查询设备唯一标识
        Optional<TRunMeterDocu> docuImeiOp = docuRepo.findById(new TRunMeterDocuId(snMeter, EcasConstant.PROTOCOL_NO_IMEI));
        if (!docuImeiOp.isPresent()) {
            context.fail(task, task.getTraceId(), "Docu配置IMEI不存在");
            return;
        }
        String imei = docuImeiOp.get().getParamValue();

        WriteOfflineV1910 writeOffline = null;
        JSONObject result = null;
        final String traceId = task.getTraceId();
        try {
            // 后续如果出现了多个objId，可以考虑将这几个字段持久化到数据库表T_RUN_METER_DOCU当中。
            Integer objId = properties.getObjId();
            Integer objInstId = properties.getObjInstId();
            Integer writeMode = properties.getWriteMode();
            Date expiredTime = new Date(System.currentTimeMillis() + properties.getExpireOffset());
            writeOffline = new WriteOfflineV1910(imei, objId, objInstId, writeMode, expiredTime);

            // 组装帧数据
            // 681071565022180400010cF300010100000010121920012B16
            StringBuffer frameValue = new StringBuffer();
            frameValue.append(properties.getStartChar());
            frameValue.append(properties.getMeterType());

            String[] data = parameters.split(",");
            // 拼接控制码
            String controlCode = protocolCmd.getServiceId();
            // 拼接数据类型
            String dataType = protocolCmd.getMethod();
            // 拼接数据长度
            int dataTypeLen = dataType.length() / 2; // 数据类型长度
            int serialLen = 1; // 序号长度
            int length = dataTypeLen + serialLen;

            for (int i = 0; i < data.length; i++) {
                length += cmdParms.get(i).getByteLen();
            }
            frameValue.append(ParseUtil.encodeMeterNo(meterNo));
            frameValue.append(ParseUtil.stringToBcd(controlCode));
            frameValue.append(ParseUtil.intToHex(length, 2));
            frameValue.append(ParseUtil.stringToBcd(dataType));
            Integer seq = taskRepo.getSeq();
            task.setSeq(seq);
            // 拼接序号
            frameValue.append(ParseUtil.intToHex(seq % 256, 2));
            // 拼接参数值
            processMobileInParam(cmdParms, frameValue, data);
            frameValue.append(ParseUtil.calcChecksum(frameValue.toString()));
            frameValue.append(properties.getEndChar());
            JSONObject requestBody = new org.json.JSONObject();
            JSONArray jsonArray = new JSONArray();
            JSONObject dataObj = new org.json.JSONObject();
            requestBody.put("data", jsonArray);
            jsonArray.put(dataObj);
            dataObj.put("res_id", properties.getReadResId());
            dataObj.put("val", frameValue);

            ExecuteOpe executeOpe = new ExecuteOpe(appid);
            result = executeOpe.operation(writeOffline, requestBody);

            try {
                int errno = result.getInt("errno");
                if (0 == errno) {
                    String wlwCommandId = result.getJSONObject("data").getString("uuid");
                    context.success(task, "PENDING", wlwCommandId);
                } else {
                    String error = result.getString("error");
                    LOG.error("Publish command to mobile failed, command: {}, reason: {}, traceId: {}", task, error, traceId);
                    context.fail(task, traceId, error);
                }
            } catch (JSONException e) {
                LOG.error("Publish command to mobile failed, command: {}, traceId: {}", task, traceId, e);
                context.fail(task, traceId, "json转换失败");
            }
        } catch (MRException e) {
            context.fail(task, task.getTraceId(), e.getMsg());
            LOG.error("命令下发失败, ", e);
        } catch (Exception e) {
            context.fail(task, task.getTraceId(), "请根据traceId查看日志");
            LOG.error("命令下发失败, ", e);
        } finally {
            LOG.info("命令(traceId: {}) 请求：{}, 返回结果为：{}", traceId, writeOffline, result);
        }
    }

    private void processMobileInParam(List<TCfgProtocolCmdParam> cmdParms, StringBuffer frameValue, String[] data) {
        if (null == cmdParms || data == null || cmdParms.size() != data.length) {
            throw new MRException(MRExceptionEnum.CommandParamLenInconsistent);
        }
        for (int i = 0; i < data.length; i++) {
            TCfgProtocolCmdParam paramDefine = cmdParms.get(i);
            String paramFrameType = paramDefine.getParamFrameType();
            if (StringUtils.isEmpty(paramFrameType)) {
                paramFrameType = "Default";
            }
            Integer byteLen = paramDefine.getByteLen();
            switch (paramFrameType) {
            case "BCD":
                frameValue.append(ParseUtil.stringToBcd(data[i]));
                break;
            case "HEX": // 数据传输超过一个字节时，都要低位在前高位在后
                frameValue.append(ParseUtil.stringToBcd(ParseUtil.intToHex(data[i], byteLen * 2)));
                break;
            case "IP": {
                String[] ip = data[i].split(".");
                for (String s : ip) {
                    frameValue.append(ParseUtil.intToHex(Integer.valueOf(s), byteLen * 2));
                }
            }
            default:
                frameValue.append(data[i]);
                break;
            }
        }
    }

}
