package com.wasion.meterreading.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.wasion.meterreading.constant.SystemConstant;
import com.wasion.meterreading.domain.BaseValue;
import com.wasion.meterreading.util.CommonUtil;

@Entity
@Table(name = "T_METER_DEVICE")
public class TMeterDevice extends BaseValue {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7510434082608705323L;
	@Column(name = "SN", length = 32, updatable = false)
	private String sn;
	@Column(name = "DEVICE_ID", length = 40)
	private String deviceId;
	@Id
	@NotNull(message = "imei不能为空")
	@Column(name = "IMEI", length = 32)
	private String imei;
	@Column(name = "IMSI", length = 32)
	private String imsi;
	@Column(name = "REGISTER_DATE", updatable = false)
	private Date registerDate;
	@Column(name = "METER_NO", nullable = true)
	private String meterNo;
	@NotNull(message = "平台不能为空")
	@Column(name = "PLATFORM")
	private String platform;
	@Column(name = "LAST_ONLINE_TIME", nullable = true)
	private Date lastOnlineTime;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

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

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public static TMeterDevice of(String deviceId, String imei, String imsi, String platform) {
		TMeterDevice tMobileMeter = new TMeterDevice();
		tMobileMeter.deviceId = deviceId;
		tMobileMeter.imei = imei;
		tMobileMeter.imsi = imsi;
		tMobileMeter.sn = CommonUtil.getUuid();
		tMobileMeter.platform = platform;
		tMobileMeter.registerDate = new Date();
		return tMobileMeter;
	}

	public static TMeterDevice of(String deviceId, String imei, String imsi) {
		TMeterDevice of = of(deviceId, imei, imsi, SystemConstant.PLATFORM_MOBILE);
		return of;
	}

	public Date getLastOnlineTime() {
		return lastOnlineTime;
	}

	public void setLastOnlineTime(Date lastOnlineTime) {
		this.lastOnlineTime = lastOnlineTime;
	}

}
