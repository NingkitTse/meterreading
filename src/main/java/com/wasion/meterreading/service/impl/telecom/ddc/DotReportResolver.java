package com.wasion.meterreading.service.impl.telecom.ddc;

import com.alibaba.fastjson.JSONObject;
import com.wasion.meterreading.domain.dto.ResolveContext;
import com.wasion.meterreading.domain.dto.telecom.ServiceData;
import com.wasion.meterreading.domain.dto.telecom.impl.DotReportData;
import com.wasion.meterreading.domain.dto.telecom.impl.DotReportDataDelta;
import com.wasion.meterreading.entity.freezedata.DayFreezeDataValue;
import com.wasion.meterreading.entity.freezedata.FreezeDataId;
import com.wasion.meterreading.entity.freezedata.PointFreezeDataValue;
import com.wasion.meterreading.orm.jpa.freezedata.PointFreezeDataRepository;
import com.wasion.meterreading.service.impl.telecom.ServiceResolveServerI;
import com.wasion.meterreading.util.CommonUtil;
import com.wasion.meterreading.util.DateUtil;
import com.wasion.meterreading.util.ParseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 点冻结上报解析, 这里吐槽一下, 嵌入式工程师极度不规范, 你们移动电信协议不同就算了,
 * 自己做电信协议的一个点冻结还搞出这么多数据版本,还前后不兼容,佩服佩服
 *
 * @author w24882 xieningjie
 * @date 2019年12月18日
 */
@Component
public class DotReportResolver implements ServiceResolveServerI {

    private static final Logger LOG = LoggerFactory.getLogger(DotReportResolver.class);

    @Autowired
    private PointFreezeDataRepository repo;

    @Transactional
    /**
     * <h1>解析点冻结数据</h1> 样例数据：
     * <ul>
     * <li>类型一：{ "notifyType": "deviceDatasChanged", "requestId": null,
     * "deviceId": "5f987418-9fee-4e34-9199-30eaedb2a197", "gatewayId":
     * "5f987418-9fee-4e34-9199-30eaedb2a197", "services": [ { "serviceId":
     * "DotReport", "serviceType": "DotReport", "data": { "meterEleno":
     * "00113005677280", "totalconsumepower": 4294967295, "maxworkelectricflow":
     * 65535, "minworkelectricflow": 65535, "batteryVoltage": "3.65",
     * "signalStrength": "-70", "SNR": "19", "state": 256, "currentReading":
     * 3000, "dailyActivityTime": 34, "Dotreporttime": "1912120100",
     * "Dotdata01": 3000, "Dotdata02": 3000, "Dotdata03": 3000, "Dotdata04":
     * 3000, "Dotdata05": 3000, "Dotdata06": 3000, "Dotdata07": 3000,
     * "Dotdata08": 3000, "Dotdata09": 3000, "Dotdata10": 3000, "Dotdata11":
     * 3000, "Dotdata12": 3000, "Dotdata13": 3000, "Dotdata14": 3000,
     * "Dotdata15": 3000, "Dotdata16": 3000, "Dotdata17": 3000, "Dotdata18":
     * 3000, "Dotdata19": 3000, "Dotdata20": 3000, "Dotdata21": 3000,
     * "Dotdata22": 3000, "Dotdata23": 3000, "Dotdata24": 3000,
     * "MonthFreezeData": 3000 }, "eventTime": "20191212T190721Z" } ] }</li>
     * <li>类型二： { "notifyType": "deviceDatasChanged", "requestId": null,
     * "deviceId": "4dba963e-61d3-42a2-89f7-7c3e4550f9a7", "gatewayId":
     * "4dba963e-61d3-42a2-89f7-7c3e4550f9a7", "services": [ { "serviceId":
     * "DotReport", "serviceType": "DotReport", "data": { "meterEleno":
     * "00113005677280", "Dotfreezingperiod": 40, "freezingnumber": 36,
     * "Dotreporttime": "1912220040", "Dotdata": [ 1000, 0, 0, 0, 0, 0, 0, 0, 0,
     * 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
     * 0, 0, 0 ] }, "eventTime": "20191223T013619Z" } ] }</li>
     */
    @Override
    public void resolveServiceData(ResolveContext context, ServiceData serviceData) {
        final Object data = serviceData.getData();
        if (data instanceof DotReportData) {
            DotReportData dotReportData = (DotReportData) data;
            processDotReportData(context, dotReportData);
        } else if (data instanceof DotReportDataDelta) {
            DotReportDataDelta dotReportDataDetal = (DotReportDataDelta) data;
            processDotReportDataDelta(context, dotReportDataDetal);
        }
    }

    private void processDotReportDataDelta(ResolveContext context, DotReportDataDelta dotReportDataDetal) {
        PointFreezeDataValue freezeDataValue = new PointFreezeDataValue();
        freezeDataValue.setSn(CommonUtil.getUuid());
        String meterEleno = dotReportDataDetal.getMeterEleno();
        context.setMeterNo(CommonUtil.specifyMeterNo(meterEleno));
        freezeDataValue.setMeterNo(meterEleno);
        freezeDataValue.setRegisterDate(new Date());
        freezeDataValue.setDeviceId(context.getDeviceId());
        freezeDataValue.setImei(context.getImei());
        freezeDataValue.setSnDevice(context.getSnDevice());

        FreezeDataId id = new FreezeDataId();
        Date freezenDate = null;
        try {
            freezenDate = DateUtil.parseDataYYMMDDHHMM(dotReportDataDetal.getDotReportTime());
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
            int dotFreezingPeriod = dotReportDataDetal.getDotFreezingPeriod();
            List<Integer> deltaDotDatas = dotReportDataDetal.getDeltaDotDatas();
            int len = deltaDotDatas.size();
            if (len == 0) {
                LOG.warn("There has no dot report data.");
                return;
            }
            double currentData = 0;
            for (int i = 0; i < len; ++i) {
                PointFreezeDataValue clone = (PointFreezeDataValue) freezeDataValue.clone();
                clone.getId().setFreezenDate(c.getTime());
                Double dotDataObjDelta = deltaDotDatas.get(i) / 1000.0;
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

        // 如果数据库已经存在表计对应日期的冻结数据，或者当前循环变量数据为无效值，则跳过。
        retList = retList.stream().filter(e -> {
            return ParseUtil.isFreezenValueValid(e);
        }).collect(Collectors.toList());
        for (PointFreezeDataValue value : retList) {
            final FreezeDataId valueId = value.getId();
            if (repo.existsById(valueId)) {
                repo.updateFreezenValueById(valueId.getSnMeter(), valueId.getFreezenDate(), value.getFreezenValue());
            } else {
                repo.save(value);
            }
            LOG.info("Save dot report data succeed. Data: {}.", value);
        }
        // 放上下文，供查询历史记录命令的方法检索历史记录，并将其保存至命令结果
        context.put(ResolveContext.DataMapKey.FREEZE_DATA_KEY, retList.size() > 0 ? retList.get(0) : new ArrayList<DayFreezeDataValue>());
        context.put(ResolveContext.DataMapKey.FREEZE_DATA_LIST_KEY, retList);
    }

    private void processDotReportData(ResolveContext context, final DotReportData dotReportData) {
        PointFreezeDataValue freezeDataValue = new PointFreezeDataValue();
        freezeDataValue.setSn(CommonUtil.getUuid());
        String meterEleno = dotReportData.getMeterEleno();
        context.setMeterNo(CommonUtil.specifyMeterNo(meterEleno));
        freezeDataValue.setMeterNo(meterEleno);
        freezeDataValue.setBattery(Double.parseDouble(dotReportData.getBatteryVoltage()));
        freezeDataValue.setRegisterDate(new Date());
        String snr = dotReportData.getSnr();
        String rsrp = dotReportData.getSignalStrength();
        freezeDataValue.setSnr(Integer.parseInt(snr));
        freezeDataValue.setRsrp(Integer.parseInt(rsrp));
        freezeDataValue.setDeviceId(context.getDeviceId());
        freezeDataValue.setImei(context.getImei());
        freezeDataValue.setSnDevice(context.getSnDevice());

        FreezeDataId id = new FreezeDataId();
        Date freezenDate = null;
        try {
            freezenDate = DateUtil.parseDataYYMMDDHHMM(dotReportData.getDotReportTime());
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
            JSONObject rawObj = dotReportData.getRawObj();
            for (int i = 0; i < 24; ++i) {
                PointFreezeDataValue clone = (PointFreezeDataValue) freezeDataValue.clone();
                clone.getId().setFreezenDate(c.getTime());
                Double dotDataObj = rawObj.getInteger("Dotdata" + String.format("%02d", i + 1)) / 1000.0;
                clone.setFreezenValue(dotDataObj + "");
                retList.add(clone);
                // 每小时时间+1
                c.add(Calendar.HOUR, 1);
            }
        } catch (SecurityException | IllegalArgumentException e) {
            LOG.error("Resolve dot report data failed, for reflect exception.", e);
        } catch (CloneNotSupportedException e) {
            LOG.error("Resolve dot report data failed, for clone exception.", e);
        }
        for (PointFreezeDataValue value : retList) {
            // 如果数据库已经存在表计对应日期的冻结数据，或者当前循环变量数据为无效值，则跳过。
            if (repo.existsById(value.getId()) || !ParseUtil.isFreezenValueValid(value)) {
                continue;
            }
            repo.save(value);
            LOG.info("Save dot report data succeed. Data: {}.", value);
        }
    }

}
