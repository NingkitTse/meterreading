package com.wasion.meterreading.service.impl.telecom.cr;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wasion.meterreading.constant.SystemConstant;
import com.wasion.meterreading.domain.dto.ResolveContext;
import com.wasion.meterreading.entity.RtTaskOnlineV1910;
import com.wasion.meterreading.entity.TCfgProtocolCmd;
import com.wasion.meterreading.entity.TCfgProtocolCmdParam;
import com.wasion.meterreading.entity.freezedata.DayFreezeDataValue;
import com.wasion.meterreading.entity.freezedata.FreezeDataId;
import com.wasion.meterreading.entity.freezedata.PointFreezeDataValue;
import com.wasion.meterreading.entity.view.VMeterDevice;
import com.wasion.meterreading.orm.jpa.RtTaskOnlineV1910Repository;
import com.wasion.meterreading.orm.jpa.TCfgProtocolCmdParamRepository;
import com.wasion.meterreading.orm.jpa.TCfgProtocolCmdRepository;
import com.wasion.meterreading.orm.jpa.VMeterDeviceRepository;
import com.wasion.meterreading.orm.jpa.freezedata.DayFreezeDataRepository;
import com.wasion.meterreading.orm.jpa.freezedata.PointFreezeDataRepository;
import com.wasion.meterreading.service.NotifyResolveServiceI;
import com.wasion.meterreading.util.CommonUtil;
import com.wasion.meterreading.util.ContextProvider;
import com.wasion.meterreading.util.DateUtil;

/**
 * 电信命令响应解析类
 * 
 * @author w24882 xieningjie
 * @date 2019年12月4日
 */
@Service
public class TeleCommandRspResolver implements NotifyResolveServiceI {

    private static final Logger LOG = LoggerFactory.getLogger(TeleCommandRspResolver.class);
    @Autowired
    private RtTaskOnlineV1910Repository taskRepo;
    @Autowired
    private TCfgProtocolCmdParamRepository cmdParamRepo;
    @Autowired
    private TCfgProtocolCmdRepository cmdRepo;
    @Autowired
    private VMeterDeviceRepository vMeterRepository;

    /**
     * ParamType 出参标识
     */
    private final static String PARAM_TYPE_OUT_PARAM = "0";

    /**
     * 样例数据： { "deviceId": "d2187a76-1a6c-404f-b1e7-2fe7a60fa984", "commandId":
     * "f67384a07a5b4581a87b9c93f29824a5", "result": { "resultCode":
     * "SUCCESSFUL", "resultDetail": { "result": { "result": 1,
     * "getdayfreezingstarttimedata": [ "1912040000", 100, "FFFFFFFFFF",
     * 4294967295, "FFFFFFFFFF", 4294967295, "FFFFFFFFFF", 4294967295,
     * "FFFFFFFFFF", 4294967295, "FFFFFFFFFF", 4294967295, "FFFFFFFFFF",
     * 4294967295 ], "getdayfreezingnumber": 7, "meterEleno": "00113037027741" }
     * } } }
     */
    @Override
    public void resolve(ResolveContext context) {
        JSONObject requestBody = (JSONObject) context.getData();
        String wlwCommandId = requestBody.getString("commandId");
        String deviceId = requestBody.getString("deviceId");
        JSONObject result = requestBody.getJSONObject("result");
        String resultCode = result.getString("resultCode");
        context.setDeviceId(deviceId);
        // 更新状态
        RtTaskOnlineV1910 task = taskRepo.findByCommandId(wlwCommandId);
        task.setWlwCmdStatus(resultCode);
        taskRepo.save(task);

        JSONObject resultDetail = result.getJSONObject("resultDetail");
        if (null == resultDetail) {
            LOG.info("No result detail.", wlwCommandId);
            return;
        }
        JSONObject realResult = resultDetail.getJSONObject("result");
        if (null == realResult) {
            LOG.info("No result detail result.", wlwCommandId);
            return;
        }
        VMeterDevice meterDevice = vMeterRepository.findByDeviceId(deviceId);
        if (null == meterDevice) {
            LOG.warn("Device is not exist.");
            return;
        }
        context.setSnDevice(meterDevice.getSnDevice());
        context.setSnMeter(meterDevice.getSnMeter());
        context.setImei(meterDevice.getImei());

        List<TCfgProtocolCmdParam> params = cmdParamRepo.findByCmdIdAndParamTypeOrderByParamNo(task.getSnProtocolCommand(), "0");
        StringBuffer returnData = new StringBuffer();
        LOG.info("Received command<{}>'s process result.", wlwCommandId);

        Set<String> keySet = realResult.keySet();
        int index = 0;
        for (TCfgProtocolCmdParam param : params) {
            String paramKey = param.getParamKey();

            String paramType = param.getParamType();
            LOG.debug("Param[{}]'s key is {}, type is {}", index, param.getParamKey(), paramType);
            if (!PARAM_TYPE_OUT_PARAM.equals(paramType)) {
                continue;
            }
            if (!keySet.contains(paramKey)) {
                continue;
            }
            returnData.append(realResult.getString(paramKey).replace(",", "|") + SystemConstant.TASK_RETURN_DATA_SEPERATOR); // 与老采集回填参数的风格保持一致
            index++;
        }

        int len = returnData.length();
        if (len > 0) {
            task.setReturnData(returnData.substring(0, len - 1));
        }
        taskRepo.save(task);
        LOG.info("Save comand response succeed! Newly command record is {}.", task);

        // 判断是否需要额外保存数据
        Optional<TCfgProtocolCmd> cmdOp = cmdRepo.findById(task.getSnProtocolCommand());
        if (!cmdOp.isPresent()) {
            return;
        }

        TCfgProtocolCmd cmd = cmdOp.get();
        String method = cmd.getMethod();
        switch (method) {
        case "GET_DAY_FREEZE_DATA":
            enhanceProcessDayFreeze(context, realResult);
            break;
        case "GET_SEGMENT_FREEZE_DATA":
            enhanceProcessPointFreeze(context, realResult);
            break;
        default:
            break;
        }
    }

    /**
     * <h1>增强处理点冻结数据</h1>
     * 
     * 样例数据： { "result": 1, "getfreezingstarttime": "1912040000",
     * "getfreezingdata": [ 1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
     * 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ],
     * "getfreezingnumber": 32, "pointfreezingperiod": 40, "meterEleno":
     * "00113037027741" }
     * 
     * @param context 解析上下文
     * @param realResult 最终带值Json
     * @author w24882 xieningjie
     * @date 2019年12月23日
     */
    private void enhanceProcessPointFreeze(ResolveContext context, JSONObject realResult) {
        PointFreezeDataRepository repo = ContextProvider.getBean(PointFreezeDataRepository.class);
        PointFreezeDataValue freezeDataValue = new PointFreezeDataValue();
        freezeDataValue.setSn(CommonUtil.getUuid());
        String meterEleno = realResult.getString("meterEleno");
        context.setMeterNo(CommonUtil.specifyMeterNo(meterEleno));
        freezeDataValue.setMeterNo(meterEleno);
        freezeDataValue.setRegisterDate(new Date());
        freezeDataValue.setDeviceId(context.getDeviceId());
        freezeDataValue.setImei(context.getImei());
        freezeDataValue.setSnDevice(context.getSnDevice());

        FreezeDataId id = new FreezeDataId();
        Date freezenDate = null;
        try {
            freezenDate = DateUtil.parseDataYYMMDDHHMM(realResult.getString("getfreezingstarttime"));
            id.setFreezenDate(freezenDate);
        } catch (ParseException e) {
            LOG.error("Resolve dot report data failed, parse freezen date failed.", e);
        }
        id.setSnMeter(context.getSnMeter());
        freezeDataValue.setId(id);

        List<PointFreezeDataValue> retList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(freezenDate);
        try {
            int dotFreezingPeriod = realResult.getIntValue("pointfreezingperiod");
            List<Object> deltaDotDatas = realResult.getJSONArray("getfreezingdata");
            int len = deltaDotDatas.size();
            if (len == 0) {
                LOG.warn("There has no dot report data.");
                return;
            }
            double currentData = 0;
            for (int i = 0; i < len; ++i) {
                PointFreezeDataValue clone = (PointFreezeDataValue) freezeDataValue.clone();
                clone.getId().setFreezenDate(c.getTime());
                Double dotDataObjDelta = (Integer) deltaDotDatas.get(i) / 1000.0;
                currentData += dotDataObjDelta;
                clone.setFreezenValue(currentData + "");
                retList.add(clone);
                // 按间隔时间递增
                c.add(Calendar.MINUTE, dotFreezingPeriod);
            }
        } catch (SecurityException | IllegalArgumentException e) {
            LOG.error("Resolve dot report data failed, for reflect exception.", e);
        } catch (CloneNotSupportedException e) {
            LOG.error("Resolve dot report data failed, for clone exception.", e);
        }
        repo.saveAll(retList);
        LOG.info("Enhance process dot report data succeed. Total: {}, data: {}.", retList.size(), retList);
    }

    /**
     * <h1>增强处理日冻结数据</h1>
     * 
     * 样例数据： { "result": 1, "getdayfreezingstarttimedata": [ "1912040000", 100,
     * "FFFFFFFFFF", 4294967295, "FFFFFFFFFF", 4294967295, "FFFFFFFFFF",
     * 4294967295, "FFFFFFFFFF", 4294967295, "FFFFFFFFFF", 4294967295,
     * "FFFFFFFFFF", 4294967295 ], "getdayfreezingnumber": 7, "meterEleno":
     * "00113037027741" }
     * 
     * @param context 解析上下文
     * @param realResult 最终带值Json
     * @author w24882 xieningjie
     * @date 2019年12月23日
     */
    private void enhanceProcessDayFreeze(ResolveContext context, JSONObject realResult) {
        LOG.debug("Start to enhance process day frozen data.");
        DayFreezeDataRepository repo = ContextProvider.getBean(DayFreezeDataRepository.class);

        JSONArray arr = realResult.getJSONArray("getdayfreezingstarttimedata");
        Integer frozenNum = realResult.getInteger("getdayfreezingnumber");
        int size = arr.size();
        if (size != frozenNum * 2 || size % 2 != 0) {
            LOG.warn("Invalid day frozen data format, getdayfreezingstarttimedata's size is not even. Abandoned.");
        }

        List<DayFreezeDataValue> retList = new ArrayList<>();
        FreezeDataId freezeDataId = new FreezeDataId();
        freezeDataId.setSnMeter(context.getSnMeter());
        DayFreezeDataValue dayFreezeDataValue = new DayFreezeDataValue();
        dayFreezeDataValue.setSnDevice(context.getSnDevice());
        dayFreezeDataValue.setId(freezeDataId);
        dayFreezeDataValue.setRegisterDate(new Date());
        dayFreezeDataValue.setValidData(true);

        for (int i = 0; i < size / 2; i += 2) { // 每两个一组
            Object timeObj = arr.get(i);
            Object dataObj = arr.get(i + 1);
            if (!(timeObj instanceof String) || "FFFFFFFFFF".equals(timeObj)) { // 无效时间格式
                continue;
            }
            if (!(dataObj instanceof Integer) || ((Long) dataObj) > Integer.MAX_VALUE) { // 无效数据格式
                continue;
            }
            Date freezenDate;
            try {
                freezenDate = DateUtil.parseDataYYMMDDHHMM((String) timeObj);
            } catch (ParseException e1) {
                LOG.error("Parse frozen date failed, Skipped.");
                continue;
            }
            DayFreezeDataValue clone = null;
            try {
                clone = (DayFreezeDataValue) dayFreezeDataValue.clone();
            } catch (CloneNotSupportedException e) {
                LOG.error("Clone failed, Skipped");
                continue;
            }
            clone.getId().setFreezenDate(freezenDate);
            clone.setFreezenValue((Integer) dataObj / 1000.0 + ""); // 转换成吨
            retList.add(clone);
        }
        repo.saveAll(retList);
        LOG.info("Enhance process day frozen data success. Saved frozen data is {}.", retList);
    }
}
