package com.wasion.meterreading.service.impl.telecom.ddc;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wasion.meterreading.constant.SystemConstant;
import com.wasion.meterreading.domain.dto.ResolveContext;
import com.wasion.meterreading.domain.dto.telecom.ServiceData;
import com.wasion.meterreading.domain.dto.telecom.ServiceDataDetail;
import com.wasion.meterreading.entity.TCfgIotResolverValue;
import com.wasion.meterreading.entity.TMeterDevice;
import com.wasion.meterreading.entity.view.VMeterDevice;
import com.wasion.meterreading.exception.MRException;
import com.wasion.meterreading.exception.MRExceptionEnum;
import com.wasion.meterreading.orm.jpa.TMeterDeviceRepository;
import com.wasion.meterreading.orm.jpa.VMeterDeviceRepository;
import com.wasion.meterreading.service.NotifyResolveServiceI;
import com.wasion.meterreading.service.impl.cache.ResolverConfigCacheService;
import com.wasion.meterreading.service.impl.telecom.ServiceResolveServerI;
import com.wasion.meterreading.util.ContextProvider;

import telecom.sdk.service.dataCollection.QuerySpecifyDevice;

/**
 * Telecom Device Data Changed Resolver, 电信设备数据变更解析器
 * 
 * @author w24882
 * @date 2019年11月6日
 */
@Service
public class TelecomDdcResolver implements NotifyResolveServiceI {
    private final static Logger LOG = LoggerFactory.getLogger(TelecomDdcResolver.class);

    @Autowired
    private ResolverConfigCacheService serviceCfgCache;
    @Autowired
    private VMeterDeviceRepository vMobileMeterRepository;
    @Autowired
    private TMeterDeviceRepository tMobileMeterRepository;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");

    @Override
    public void resolve(ResolveContext context) {
        JSONObject requestBody = (JSONObject) context.getData();
        final String deviceId = requestBody.getString("deviceId");
        context.setDeviceId(deviceId);
        VMeterDevice device = getVMeterDevice(context, deviceId);
        final String imei = device.getImei();
        context.setImei(imei);
        context.setImei(device.getImsi());
        context.setSnMeter(device.getSnMeter());
        context.setSnDevice(device.getSnDevice());
        JSONArray services = requestBody.getJSONArray("services");

        int len = services.size();
        for (int i = 0; i < len; ++i) {
            JSONObject object = services.getJSONObject(i);
            String serviceType = object.getString("serviceType");
            String serviceId = object.getString("serviceId");
            Date eventTime = null;
            try {
                if (object.containsKey("eventTime")) {
                    eventTime = sdf.parse(object.getString("eventTime"));
                }
            } catch (ParseException e) {
                LOG.error("Parse event time failed.", e);
                // throw new MRException(MRExceptionEnum.CommunicationException);
            }
            String dateString = object.getJSONObject("data").toJSONString();

            ObjectMapper objectMapper = new ObjectMapper();
            ServiceData serviceData = new ServiceData();
            serviceData.setServiceId(serviceId);
            serviceData.setServiceType(serviceType);
            serviceData.setEventTime(eventTime);

            TCfgIotResolverValue serviceConfig = serviceCfgCache.getValue(serviceId);
            if (null == serviceConfig) {
                LOG.error("ServiceId<{}>'s config is not exsit.");
                throw new MRException(MRExceptionEnum.SystemConfigurationException);
            }
            LOG.debug("Get service<{}>'s config {}.", serviceId, serviceConfig);

            // 获取服务解析类
            ServiceResolveServerI resolver = null;
            Object obj = ContextProvider.getBean(serviceConfig.getResolverClass());
            if (!(obj instanceof ServiceResolveServerI)) {
                LOG.error("ServiceId<{}>'s config resolver is not a ServiceResolveServerI instance.");
                throw new MRException(MRExceptionEnum.SystemConfigurationException);
            } else {
                resolver = (ServiceResolveServerI) obj;
            }

            List<Class<?>> serviceDataClasses = serviceConfig.getServiceDataClasses();
            for (Class<?> serviceDataClass : serviceDataClasses) {
                // 获取服务数据
                try {
                    ServiceDataDetail readValue = (ServiceDataDetail) objectMapper.readValue(dateString, serviceDataClass);
                    serviceData.setData(readValue);
                    readValue.setRawObj(JSON.parseObject(dateString));
                    break;
                } catch (IOException e) {
                    LOG.warn("ServiceDataClass {} is not suitable for this json object.");
                    LOG.debug("Stack is: ", e);
                }
            }
            // 如果没有一个符合该Service的serviceDataClass则抛出异常
            if (null == serviceData.getData()) {
                throw new MRException(MRExceptionEnum.SystemConfigurationException);
            }
            resolver.resolveServiceData(context, serviceData);
            LOG.info("Resolve service<{}>'s data succeed.", serviceId);
        }

        // 如果表号为空，重新保存一次
        Optional<TMeterDevice> tMobileMeterOp = tMobileMeterRepository.findById(imei);
        if (!tMobileMeterOp.isPresent()) {
            return;
        }
        TMeterDevice tMobileMeter = tMobileMeterOp.get();
        tMobileMeter.setMeterNo(context.getMeterNo());
        tMobileMeterRepository.save(tMobileMeter);
    }

    /**
     * 根据设备ID获取视图VMeterDevice
     * 
     * @param context 上下文
     * @param deviceId 设备ID
     * @return 设备相关的视图VMeterDevice
     */
    private VMeterDevice getVMeterDevice(ResolveContext context, final String deviceId) {
        VMeterDevice device = vMobileMeterRepository.findByDeviceId(deviceId);
        if (null == device) {
            try {
                String jsonInfo = QuerySpecifyDevice.querySpecifyDevice(deviceId);
                JSONObject object = JSON.parseObject(jsonInfo);
                JSONObject deviceInfo = object.getJSONObject("deviceInfo");
                String imei = deviceInfo.getString("nodeId");
                TMeterDevice tMobileMeter = TMeterDevice.of(deviceId, imei, null, SystemConstant.PLATFORM_TELECOM);
                tMobileMeterRepository.save(tMobileMeter);
                context.setImei(imei);
                device = vMobileMeterRepository.findByDeviceId(deviceId);
            } catch (Exception e) {
                LOG.error("Get device info from telecom platform failed. {}", e);
                throw new MRException(MRExceptionEnum.QueryDeviceFailed);
            }
        }
        return device;
    }

}
