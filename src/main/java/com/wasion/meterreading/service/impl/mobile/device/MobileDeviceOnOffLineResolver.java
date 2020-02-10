package com.wasion.meterreading.service.impl.mobile.device;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wasion.meterreading.domain.dto.ResolveContext;
import com.wasion.meterreading.service.NotifyResolveServiceI;

/**
 * 移动上下线解析器
 * 
 * @author w24882 xieningjie
 * @date 2019年11月21日
 */
@Service
public class MobileDeviceOnOffLineResolver implements NotifyResolveServiceI {

	private static final Logger LOG = LoggerFactory.getLogger(MobileDeviceOnOffLineResolver.class);

	/**
	 * {"at":1576156220123
	 * ,"login_type":10,"imei":"869975037677104","type":2,"dev_id":576096445,"status":0
	 * }
	 */
	@Override
	public void resolve(ResolveContext context) {
		try {
			JSONObject data = (JSONObject) context.getData();
			String deviceStatus = "_";
			if (data.has("status")) {
				int status;
				status = data.getInt("status");
				if (1 == status) {
					deviceStatus = "上";
				} else if (0 == status) {
					deviceStatus = "下";
				}
			}
			String imei = "IMEI";
			if (data.has("imei")) {
				imei = data.getString("imei");
			}
			LOG.info("设备<{}, {}> {}线。", context.getDeviceId(), imei, deviceStatus);
		} catch (JSONException e) {
			LOG.error("获取设备状态失败.", e);
		}
	}

}
