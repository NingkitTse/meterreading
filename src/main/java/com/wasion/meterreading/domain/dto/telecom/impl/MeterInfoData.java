package com.wasion.meterreading.domain.dto.telecom.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wasion.meterreading.domain.dto.telecom.ServiceDataDetail;

/**
 * 数据样例
 * {"meterEleno":"00113005678602","ICCID":"89861118224006973501","IMEI":"869975033729222","IMSI":"460113005678602","HardwareVer":"S7.820.401","SoftwareVer":"20191106ED1931"}
 * 
 * @author w24882
 * @date 2019年11月15日
 */
public class MeterInfoData extends ServiceDataDetail {

	/**
	 * 序列化版本ID
	 */
	private static final long serialVersionUID = 4309362280060804271L;
	@JsonProperty(value = "meterEleno")
	private String meterEleno;
	@JsonProperty(value = "ICCID")
	private String iccid;
	@JsonProperty(value = "IMEI")
	private String imei;
	@JsonProperty(value = "IMSI")
	private String imsi;
	@JsonProperty(value = "HardwareVer")
	private String hardwareVer;
	@JsonProperty(value = "SoftwareVer")
	private String softwareVer;

	public String getMeterEleno() {
		return meterEleno;
	}

	public void setMeterEleno(String meterEleno) {
		this.meterEleno = meterEleno;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
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

	public String getHardwareVer() {
		return hardwareVer;
	}

	public void setHardwareVer(String hardwareVer) {
		this.hardwareVer = hardwareVer;
	}

	public String getSoftwareVer() {
		return softwareVer;
	}

	public void setSoftwareVer(String softwareVer) {
		this.softwareVer = softwareVer;
	}

}
