package com.wasion.meterreading.service.impl.mobile.ddc;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wasion.meterreading.config.OnenetProperties;
import com.wasion.meterreading.domain.FrameValue;
import com.wasion.meterreading.domain.dto.ResolveContext;
import com.wasion.meterreading.domain.dto.ResolveContext.DataMapKey;
import com.wasion.meterreading.domain.dto.mobile.MobileContext;
import com.wasion.meterreading.domain.dto.mobile.MobileContext.UploadDataConstant;
import com.wasion.meterreading.entity.TMeterDevice;
import com.wasion.meterreading.entity.view.VMeterDevice;
import com.wasion.meterreading.exception.MRException;
import com.wasion.meterreading.exception.MRExceptionEnum;
import com.wasion.meterreading.orm.jpa.TMeterDeviceRepository;
import com.wasion.meterreading.orm.jpa.VMeterDeviceRepository;
import com.wasion.meterreading.service.NotifyResolveServiceI;
import com.wasion.meterreading.service.impl.MeterServiceImpl;
import com.wasion.meterreading.service.impl.cache.ResolverConfigCacheService;
import com.wasion.meterreading.service.impl.mobile.MobileResolveServiceI;
import com.wasion.meterreading.util.CommonUtil;
import com.wasion.meterreading.util.ContextProvider;
import com.wasion.meterreading.util.ParseUtil;

/**
 * Mobile Device Data changed Resolver, 移动设备数据变化解析器
 * 
 * @author w24882 xieningjie
 * @date 2019年11月20日
 */
@Service
public class MobileDdcResolver implements NotifyResolveServiceI {
    private static final Logger LOG = LoggerFactory.getLogger(MobileDdcResolver.class);
    @Autowired
    private ResolverConfigCacheService cacheService;
    @Autowired
    private VMeterDeviceRepository vMeterRepository;
    @Autowired
    private TMeterDeviceRepository tMeterRepository;
    @Autowired
    private OnenetProperties properties;

    @Override
    public void resolve(ResolveContext context) {
        MobileContext mobileContext = null;
        if (context instanceof MobileContext) {
            mobileContext = (MobileContext) context;
        } else {
            LOG.error("Context is not a mobile context.");
            return;
        }

        JSONObject data = (JSONObject) mobileContext.getData();
        try {
            if (data.has("ds_id")) {
                mobileContext.put("dsId", data.getString("ds_id"));
            }
            mobileContext.put(UploadDataConstant.REGISTER_DATE_KEY, new Date(data.getLong("at")));
            FrameValue frameValue = new FrameValue(data.getString("value"));
            mobileContext.put(DataMapKey.FRAME_VALUE_KEY, frameValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String deviceId = mobileContext.getDeviceId();
        VMeterDevice meterDevice = vMeterRepository.findByDeviceId(deviceId);

        if (null == meterDevice) { // 如果本地库没有该设备就去IOT平台查询
            MeterServiceImpl bean = ContextProvider.getBean(MeterServiceImpl.class);
            meterDevice = bean.queryVMobileMeterFromIot(deviceId);
            if (null == meterDevice) {
                LOG.error("Meter device is not exist from mobile iot platform.");
                return;
            }
        }
        String snMeter = meterDevice.getSnMeter();
        String snDevice = meterDevice.getSnDevice();
        mobileContext.setSnMeter(snMeter);
        mobileContext.setSnDevice(snDevice);

        validateReportFrameValue(mobileContext);
        parseDataReportBaseData(mobileContext);
        parseDataReportServiceData(mobileContext);

        String meterNo = mobileContext.getMeterNo();
        if (!StringUtils.isEmpty(meterNo)) {
            TMeterDevice device = TMeterDevice.of(deviceId, meterDevice.getImei(), meterDevice.getImsi());
            device.setMeterNo(meterNo);
            tMeterRepository.save(device);
        }
    }

    /**
     * 校验上报帧数据
     * 
     * @param mobileContext Mobile解析上下文
     * @author w24882 xieningjie
     * @date 2019年12月12日
     */
    private void validateReportFrameValue(MobileContext mobileContext) {
        LOG.debug("Start to validate mobile report data's frame format.");
        FrameValue frameValueObj = (FrameValue) mobileContext.get(DataMapKey.FRAME_VALUE_KEY);
        String frameValue = frameValueObj.getRaw();
        String startChar = properties.getStartChar();
        String endChar = properties.getEndChar();
        if (StringUtils.isEmpty(frameValue)) {
            LOG.error("Frame value is empty.");
            throw new MRException(MRExceptionEnum.InvalidReportData, "Empty");
        }
        if (!frameValue.startsWith(startChar)) {
            LOG.error("Frame value's start char is invalid.");
            throw new MRException(MRExceptionEnum.InvalidReportData, "EndChar");
        }
        if (!frameValue.endsWith(endChar)) {
            LOG.error("Frame value's start char is invalid.");
            throw new MRException(MRExceptionEnum.InvalidReportData, "StartChar");
        }
        int len = frameValue.length();
        String correctChecksum = ParseUtil.calcChecksum(frameValue.substring(0, len - 4));
        String frameChecksum = frameValue.substring(len - 4, len - 2);
        if (!correctChecksum.equals(frameChecksum)) {
            LOG.error("Frame value's check sum is invalid. Correct checksum is {}, but input checksum is {}.", correctChecksum, frameChecksum);
            throw new MRException(MRExceptionEnum.InvalidReportData, "CheckSum");
        }
    }

    /**
     * 解析数据帧基本数据
     * 
     * @param mobileContext
     * @author w24882 xieningjie
     * @date 2019年12月12日
     */
    private void parseDataReportBaseData(MobileContext mobileContext) {
        FrameValue frameValue = (FrameValue) mobileContext.get(DataMapKey.FRAME_VALUE_KEY);
        int startChar = ParseUtil.hexToInt(frameValue.next(2));
        int meterType = ParseUtil.hexToInt(frameValue.next(2));
        String addressDomain = ParseUtil.bcdToString(frameValue.next(14));
        String controlCode = frameValue.next(2);
        int dataLen = ParseUtil.hexToInt(frameValue.next(2));
        String dataSign = ParseUtil.bcdToString(frameValue.next(4));
        String dataStr = frameValue.peekTo(frameValue.getRawSize());
        mobileContext.put(UploadDataConstant.START_CHAR_KEY, startChar);
        mobileContext.put(UploadDataConstant.METER_TYPE_KEY, meterType);
        mobileContext.put(UploadDataConstant.METER_NO_KEY, CommonUtil.specifyMeterNo(addressDomain));
        mobileContext.put(UploadDataConstant.CONTROL_CODE_KEY, controlCode);
        mobileContext.put("dataLen", dataLen);
        mobileContext.put(UploadDataConstant.DATA_SIGN_KEY, dataSign);
        mobileContext.put(UploadDataConstant.DATA_STR_KEY, dataStr);
    }

    private void parseDataReportServiceData(MobileContext mobileContext) {
        String dataSign = (String) mobileContext.get(UploadDataConstant.DATA_SIGN_KEY);
        // String dataStr = (String)
        // mobileContext.get(UploadDataConstant.DATA_STR_KEY);

        MobileResolveServiceI resolver = cacheService.getResolver(dataSign, MobileResolveServiceI.class);
        resolver.resolveData(mobileContext);
    }
}
