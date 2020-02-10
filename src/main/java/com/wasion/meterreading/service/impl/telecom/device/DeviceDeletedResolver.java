package com.wasion.meterreading.service.impl.telecom.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wasion.meterreading.domain.dto.ResolveContext;
import com.wasion.meterreading.orm.jpa.TMeterDeviceRepository;
import com.wasion.meterreading.service.NbEcasDeviceServiceI;
import com.wasion.meterreading.service.NotifyResolveServiceI;

/**
 * 设备删除解析器<br>
 * {"notifyType":"deviceDeleted","deviceId":"01006f25-ab60-4a7e-8b0a-6dcfa15e43cc","gatewayId":"0010256458"}
 * 
 * @author w24882 xieningjie
 * @date 2019年11月18日
 */
@Service
public class DeviceDeletedResolver implements NotifyResolveServiceI {

    @Autowired
    private TMeterDeviceRepository repo;
    @Autowired
    private NbEcasDeviceServiceI nbDeviceService;

    @Override
    public void resolve(ResolveContext context) {
        JSONObject data = (JSONObject) context.getData();
        String deviceId = data.getString("deviceId");
        repo.deleteByDeviceId(deviceId);
        nbDeviceService.logoutDevice(deviceId);
    }

}
