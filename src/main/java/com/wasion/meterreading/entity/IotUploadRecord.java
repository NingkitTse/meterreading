package com.wasion.meterreading.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wasion.meterreading.domain.BaseValue;

/**
 * IOT上报日志
 * 
 * @author w24882
 * @date 2019年11月15日
 */
@Entity
@Table(name = "T_IOT_UPLOAD_RECORD")
public class IotUploadRecord extends BaseValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3384606209342349264L;
	@Id
	@Column(name = "SN", length = 40)
	private String sn;
	@Column(name = "REQUEST_BODY", length = 2000)
	private String requestBody;
	@Column(name = "REMOTE_ADDR", length = 50)
	private String remoteAddr;
	@Column(name = "REQUEST_DATE")
	private Date requestDate;
	@Column(name = "REQUEST_METHOD", length = 255)
	private String requestMethod;
	@Column(name = "PLATFORM", length = 40)
	private String platform;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

}
