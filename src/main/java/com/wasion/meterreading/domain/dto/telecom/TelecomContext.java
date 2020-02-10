package com.wasion.meterreading.domain.dto.telecom;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.wasion.meterreading.domain.BaseValue;
import com.wasion.meterreading.domain.dto.ResolveContext;

/**
 * 电信平台上报数据解析总线，保存关键的信息
 * 
 * @author w24882
 * @date 2019年11月7日
 */
public class TelecomContext extends BaseValue implements ResolveContext {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2790363426321474034L;
	private String deviceId;
	private String imei;
	private String imsi;
	private String notifyType;
	private String snMeter;
	private String snDevice;
	private String meterNo;
	private String serviceId;
	private String serviceType;
	private Date eventTime;
	private Object data;
	private Map<DataMapKey, Object> dataMap = new HashMap<>();

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getSnMeter() {
		return snMeter;
	}

	public void setSnMeter(String snMeter) {
		this.snMeter = snMeter;
	}

	public String getSnDevice() {
		return snDevice;
	}

	public void setSnDevice(String snDevice) {
		this.snDevice = snDevice;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public void put(DataMapKey key, Object value) {
		this.dataMap.put(key, value);
	}

	public Object get(DataMapKey key) {
		return this.dataMap.get(key);
	}

	@Override
	public Object getServiceData() {
		return dataMap.get(DataMapKey.SERVICE_DATA_KEY);
	}

	@Override
	public void setServiceData(Object data) {
		dataMap.put(DataMapKey.SERVICE_DATA_KEY, data);
	}
}
