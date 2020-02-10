package com.wasion.meterreading.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wasion.meterreading.domain.BaseValue;

@Entity
@Table(name = "DT_WTR_METER_INFO")
public class DtWtrMeterInfo extends BaseValue {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8502316503935524828L;
	@Id
	@Column(name = "SN")
	private String sn;
	/**
	 * 水表GUID
	 */
	@Column(name = "SN_METER")
	private String snMeter;
	@Column(name = "REGISTER_DATE")
	private Date registerDate;
	@Column(name = "SN_DEVICE")
	private String snDevice;
	@Column(name = "METER_NO")
	private String meterNo;
	@Column(name = "ICCID")
	private String iccid;
	@Column(name = "IMEI")
	private String imei;
	@Column(name = "IMSI")
	private String imsi;
	@Column(name = "HARDWARE_VER")
	private String hardwareVer;
	@Column(name = "SOFTWARE_VER")
	private String softwareVer;

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

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getSnMeter() {
		return snMeter;
	}

	public void setSnMeter(String snMeter) {
		this.snMeter = snMeter;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

}
