package com.wasion.meterreading.service.impl.mobile.ddc;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.wasion.meterreading.constant.SystemConstant;
import com.wasion.meterreading.domain.FrameValue;
import com.wasion.meterreading.domain.dto.ResolveContext;
import com.wasion.meterreading.domain.dto.ResolveContext.DataMapKey;
import com.wasion.meterreading.domain.dto.mobile.MobileContext;
import com.wasion.meterreading.entity.RtTaskOnlineV1910;
import com.wasion.meterreading.entity.freezedata.DayFreezeDataValue;
import com.wasion.meterreading.entity.freezedata.FreezeDataId;
import com.wasion.meterreading.entity.freezedata.FreezeDataValue;
import com.wasion.meterreading.entity.freezedata.MonthFreezeDataValue;
import com.wasion.meterreading.entity.freezedata.PointFreezeDataValue;
import com.wasion.meterreading.exception.MRException;
import com.wasion.meterreading.exception.MRExceptionEnum;
import com.wasion.meterreading.orm.jpa.RtTaskOnlineV1910Repository;
import com.wasion.meterreading.orm.jpa.freezedata.DayFreezeDataRepository;
import com.wasion.meterreading.orm.jpa.freezedata.MonthFreezeDataRepository;
import com.wasion.meterreading.orm.jpa.freezedata.PointFreezeDataRepository;
import com.wasion.meterreading.service.impl.mobile.MobileResolveServiceI;
import com.wasion.meterreading.util.ContextProvider;
import com.wasion.meterreading.util.DateUtil;
import com.wasion.meterreading.util.ParseUtil;

/**
 * 抄表指令响应解析类
 * 
 * @author w24882 xieningjie
 * @date 2019年12月12日
 */
@Component
public class ReadFrozenCmdRspResolver implements MobileResolveServiceI {

    private static final Logger LOG = LoggerFactory.getLogger(ReadFrozenCmdRspResolver.class);
    @SuppressWarnings("rawtypes")
    private JpaRepository freezeDataRepo;
    @Autowired
    private RtTaskOnlineV1910Repository taskRepo;

    /**
     * 样例数据：01 01 01 0000101219 00000000 8D16 <br>
     * 序号：01<br>
     * 数据类型：01 日冻结 02月冻结 03点冻结<br>
     * 冻结 冻结数据长度：00 <br>
     * 冻结时间：0000101219<br>
     * 冻结数据：00000000<br>
     * 校验和：48 <br>
     * 结束符：16 <br>
     * 0101010000101219000000008D16
     */
    @SuppressWarnings("unchecked")
    @Override
    public void resolveData(ResolveContext context) {
        MobileContext mobileContext = (MobileContext) context;
        FrameValue frameValue = (FrameValue) mobileContext.get(DataMapKey.FRAME_VALUE_KEY);
        List<FreezeDataValue> valueList = new ArrayList<>();
        String name = "";

        String seq = frameValue.next(2);
        String dataType = frameValue.next(2);
        FreezeDataValue freezeDataValue = null;
        switch (dataType) {
        case "01":
            freezeDataValue = new DayFreezeDataValue();
            name = "day";
            freezeDataRepo = ContextProvider.getBean(DayFreezeDataRepository.class);
            break;
        case "02":
            freezeDataValue = new MonthFreezeDataValue();
            name = "month";
            freezeDataRepo = ContextProvider.getBean(MonthFreezeDataRepository.class);
            break;
        case "03":
            freezeDataValue = new PointFreezeDataValue();
            name = "point";
            freezeDataRepo = ContextProvider.getBean(PointFreezeDataRepository.class);
            break;
        }
        String snMeter = context.getSnMeter();
        String snDevice = context.getSnDevice();
        freezeDataValue.setSnDevice(snDevice);
        FreezeDataId freezeDataId = new FreezeDataId();
        freezeDataId.setSnMeter(snMeter);
        freezeDataValue.setId(freezeDataId);
        freezeDataValue.setValidData(true);
        freezeDataValue.setRegisterDate(new Date());

        Integer hexLen = ParseUtil.hexToInt(frameValue.next(2));
        String freezeDataStr = frameValue.peekTo(frameValue.getRawSize() - 4);
        final int realLen = freezeDataStr.length();
        if (hexLen * 18 != realLen) {
            LOG.error("帧数据格式不正确，应有{}位冻结数据，实际只有{}位冻结数据", hexLen, realLen);
            throw new MRException(MRExceptionEnum.InvalidReportData, "FreezeDataLen");
        }
        // 有多少组数据，9字节一组
        while (hexLen-- > 0) {
            FreezeDataValue clone = null;
            try {
                clone = (FreezeDataValue) freezeDataValue.clone();
            } catch (CloneNotSupportedException e1) {
                LOG.error("Clone freezen data value failed.", e1);
                continue;
            }
            String dayFreezeDateStr = ParseUtil.bcdToString(frameValue.next(10)); // 最近一次日冻结数据时间
            Date dayFreezenDate = null;
            try {
                dayFreezenDate = DateUtil.parseDataYYMMDDHHMM(dayFreezeDateStr);
            } catch (ParseException e) {
                LOG.error("Parse day freezen date failed with value {}.", dayFreezeDateStr);
                dayFreezenDate = new Date(0);
            }
            String freezeFlux = frameValue.next(8);
            if ("FFFFFFFF".equals(freezeFlux)) { // 无效数据
                continue;
            }
            FreezeDataId id = clone.getId();
            id.setFreezenDate(dayFreezenDate);
            clone.setFreezenValue(ParseUtil.hexToDoulbe(freezeFlux) + "");
            valueList.add(clone);
        }
        freezeDataRepo.saveAll(valueList);
        LOG.info("Save {} freeze data<seq: {}> succeed, total: {}, data: {}.", name, valueList.size(), valueList);

        // 额外保存一下命令执行结果
        enchanceProcessCmdRtn(valueList, seq, dataType, snMeter);
    }

    private void enchanceProcessCmdRtn(List<FreezeDataValue> valueList, String seq, String dataType, String snMeter) {
        // 只查近两天下发的任务
        Date validDate = new Date(System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000);
        // 查找对应的下发命令
        RtTaskOnlineV1910 taskOnlineV1910 = taskRepo.findBySnMeterAndSeqAndTimeCreate(snMeter, ParseUtil.hexToInt(seq), validDate);
        if (null == taskOnlineV1910) {
            LOG.info("Cannot find valid task. Skipped enhance process.");
            return;
        }
        String returnData = dataType + SystemConstant.TASK_RETURN_DATA_SEPERATOR + valueList.stream().map(e -> {
            return e.getFreezenValue() + "";
        }).collect(Collectors.joining(","));
        taskOnlineV1910.setReturnData(returnData);
        taskRepo.save(taskOnlineV1910);
        LOG.info("Enhance process command response succeed. Command object is {}.", taskOnlineV1910);
    }

}
