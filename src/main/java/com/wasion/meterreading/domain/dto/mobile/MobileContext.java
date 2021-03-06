package com.wasion.meterreading.domain.dto.mobile;

import java.util.HashMap;
import java.util.Map;

import com.wasion.meterreading.domain.BaseValue;
import com.wasion.meterreading.domain.dto.ResolveContext;

/**
 * 移动解析总线上下文
 * 
 * @author w24882 xieningjie
 * @date 2019年11月19日
 */
public class MobileContext extends BaseValue implements ResolveContext {
	private Map<DataMapKey, Object> dataMap = new HashMap<>();
	private Map<String, Object> selfMap = new HashMap<>();
	private Object data;
	/**
	 * 序列化版本ID
	 */
	private static final long serialVersionUID = 6326223905633942560L;

	@Override
	public String getImei() {
		return (String) dataMap.get(DataMapKey.IMEI_KEY);
	}

	@Override
	public void setImei(String imei) {
		dataMap.put(DataMapKey.IMEI_KEY, imei);
	}

	@Override
	public String getSnMeter() {
		return (String) dataMap.get(DataMapKey.SN_METER_KEY);
	}

	@Override
	public void setSnMeter(String snMeter) {
		dataMap.put(DataMapKey.SN_METER_KEY, snMeter);
	}

	@Override
	public String getSnDevice() {
		return (String) dataMap.get(DataMapKey.SN_DEVICE_KEY);
	}

	@Override
	public void setSnDevice(String snDevice) {
		dataMap.put(DataMapKey.SN_DEVICE_KEY, snDevice);
	}

	@Override
	public String getMeterNo() {
		return (String) dataMap.get(DataMapKey.METER_NO_KEY);
	}

	@Override
	public void setMeterNo(String meterNo) {
		dataMap.put(DataMapKey.METER_NO_KEY, meterNo);
	}

	@Override
	public void put(DataMapKey key, Object value) {
		dataMap.put(key, value);
	}

	@Override
	public Object get(DataMapKey key) {
		return dataMap.get(key);
	}

	@Override
	public String getDeviceId() {
		return (String) dataMap.get(DataMapKey.DEVICE_ID_KEY);
	}

	@Override
	public void setDeviceId(String deviceId) {
		dataMap.put(DataMapKey.DEVICE_ID_KEY, deviceId);
	}

	@Override
	public String getNotifyType() {
		return (String) dataMap.get(DataMapKey.NOTIFY_TYPE_KEY);
	}

	@Override
	public void setNotifyType(String serviceType) {
		dataMap.put(DataMapKey.NOTIFY_TYPE_KEY, serviceType);
	}

	public void put(String key, Object value) {
		selfMap.put(key, value);
	}

	public Object get(String key) {
		return selfMap.get(key);
	}

	public class UploadDataConstant {
		public static final String DEVICE_ID_KEY = "deviceId";
		public static final String REGISTER_DATE_KEY = "registerDate";
		public static final String START_CHAR_KEY = "startChar";
		public static final String METER_TYPE_KEY = "meterType";
		public static final String METER_NO_KEY = "meterNo";
		public static final String CONTROL_CODE_KEY = "controlCode";
		public static final String DATA_SIGN_KEY = "dataSign";
		public static final String DATA_STR_KEY = "dataStr";
		public static final String SN_METER_KEY = "snMeter";
		public static final String SN_DEVICE_KEY = "snDevice";
	}

	@Override
	public Object getServiceData() {
		return dataMap.get(DataMapKey.SERVICE_DATA_KEY);
	}

	@Override
	public void setServiceData(Object data) {
		dataMap.put(DataMapKey.SERVICE_DATA_KEY, data);
	}

	@Override
	public Object getData() {
		return data;
	}

	@Override
	public void setData(Object data) {
		this.data = data;
	}

}
