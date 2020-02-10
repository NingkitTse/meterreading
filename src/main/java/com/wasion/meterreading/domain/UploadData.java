package com.wasion.meterreading.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Transient;

/**
 * 上报数据
 * 
 * @author w24882
 * @date 2019年10月14日
 */
public class UploadData extends BaseValue implements Cloneable {
	/**
	 * 序列化版本ID
	 */
	private static final long serialVersionUID = 4934914234032446399L;
	@Transient
	private String seq;
	private String imei;
	@Transient
	private String deviceId;
	@Column(name = "METER_NO")
	private String meterNo;
	@Transient
	private String dataSign;
	private Date registerDate;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDataSign() {
		return dataSign;
	}

	public void setDataSign(String dataSign) {
		this.dataSign = dataSign;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public UploadData() {
		this.registerDate = new Date();
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		UploadData clone = (UploadData) super.clone();
		clone.registerDate = new Date(clone.registerDate.getTime());
		return clone;
	}

}
