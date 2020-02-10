package com.wasion.meterreading.service.impl.telecom.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wasion.meterreading.constant.SystemConstant;
import com.wasion.meterreading.domain.dto.ResolveContext;
import com.wasion.meterreading.entity.TMeterDevice;
import com.wasion.meterreading.orm.jpa.TMeterDeviceRepository;
import com.wasion.meterreading.service.NbEcasDeviceServiceI;
import com.wasion.meterreading.service.NotifyResolveServiceI;

/**
 * 设备新增解析器<br>
 * 样例报文：{"notifyType":"deviceAdded","deviceId":"01006f25-ab60-4a7e-8b0a-6dcfa15e43cc","gatewayId":"0010256458",
 * "nodeType":"GATEWAY","deviceInfo":{"deviceType":"xxx","supportedSecurity":"xxx","isSecurity":"xxx",
 * "swVersion":"xxx","serialNumber":"xxx","manufacturerName":"huawei","signalStrength":"xxx",
 * "manufacturerId":"00123158","description":"description","statusDetail":"NOT_ACTIVE",
 * "mute":"xxx","protocolType":"xxx","mac":"xxx","hwVersion":"xxx","sigVersion":"xxx",
 * "bridgeId":"xxx","name":"huawei","location":"xxx","model":"xxx","fwVersion":"xxx",
 * "nodeId":"01006f25-ab60-4a7e-8b0a-6dcfa15e43cc","status":"OFFLINE","batteryLevel":"xxx"}}
 * 
 * @author w24882 xieningjie
 * @date 2019年11月18日
 */
@Service
public class DeviceAddedResolver implements NotifyResolveServiceI {

	@Autowired
	private TMeterDeviceRepository repo;
	@Autowired
	private NbEcasDeviceServiceI nbDeviceService;

	/**
     * <pre>
         {
            "notifyType": "deviceAdded",
            "deviceId": "82dc8843-98e9-4d3d-a08f-8342735644a2",
            "gatewayId": "82dc8843-98e9-4d3d-a08f-8342735644a2",
            "nodeType": "GATEWAY",
            "deviceInfo": {
                "nodeId": "869975030194230",
                "name": null,
                "description": null,
                "manufacturerId": null,
                "manufacturerName": null,
                "mac": null,
                "location": null,
                "deviceType": null,
                "model": null,
                "swVersion": null,
                "fwVersion": null,
                "hwVersion": null,
                "protocolType": null,
                "bridgeId": null,
                "status": "OFFLINE",
                "statusDetail": "NOT_ACTIVE",
                "mute": null,
                "supportedSecurity": null,
                "isSecurity": null,
                "signalStrength": null,
                "sigVersion": null,
                "serialNumber": null,
                "batteryLevel": null,
                "isHD": null
            }
        }
     * </pre>
     */
	@Override
	public void resolve(ResolveContext context) {
		JSONObject data = (JSONObject) context.getData();
		String deviceId = data.getString("deviceId");
		JSONObject deviceInfo = data.getJSONObject("deviceInfo");
		String imei = deviceInfo.getString("nodeId");
		TMeterDevice device = TMeterDevice.of(deviceId, imei, null, SystemConstant.PLATFORM_TELECOM);
		nbDeviceService.registerDevice(imei, deviceId);
		repo.save(device);
	}

}
