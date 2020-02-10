package com.wasion.meterreading.domain.dto.telecom;

import java.util.Date;

import com.wasion.meterreading.domain.BaseValue;

/**
 * 电信平台服务数据
 * 
 * @author w24882
 * @date 2019年10月28日
 */
public class ServiceData extends BaseValue {
	/**
	 * 序列化版本ID
	 */
	private static final long serialVersionUID = -3633522344855303247L;
	private String serviceId;
	private String serviceType;
	private Date eventTime;
	private Object data;

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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
