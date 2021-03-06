package com.wasion.meterreading.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * TSysGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CFG_APPS_CONFIG")
@SuppressWarnings("serial")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TCfgAppsConfig implements java.io.Serializable {

	// Fields

	private String appGuid;
	private String baseUrl;	
	private String appid;
	private String secret;
	private String appValue;
	private String appName;
	private String isUsed;
	private String optNo;
	private Date createTime;
	// Constructors

	/** default constructor */
	public TCfgAppsConfig() {
	}


	/** full constructor */
	public TCfgAppsConfig(String appGuid, String baseUrl, String appid,
			String secret, String appValue, String appName,String isUsed,Date createTime,String optNo) {
		this.appGuid = appGuid;
		this.baseUrl = baseUrl;
		this.appid = appid;
		this.secret = secret;
		this.appValue = appValue;
		this.appName = appName;
		this.isUsed = isUsed;
		this.createTime = createTime;
		this.optNo = optNo;
	}

	// Property accessors
	@Id
	@Column(name = "APP_GUID",  nullable = false, length = 40)
	public String getAppGuid() {
		return this.appGuid;
	}

	public void setAppGuid(String appGuid) {
		this.appGuid = appGuid;
	}
	@Column(name = "BASE_URL", nullable = false, length = 100)
	public String getBaseUrl() {
		return baseUrl;
	}


	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Column(name = "APPID", nullable = false, length = 40)
	public String getAppid() {
		return appid;
	}


	public void setAppid(String appid) {
		this.appid = appid;
	}

	@Column(name = "SECRET", nullable = false, length = 40)
	public String getSecret() {
		return secret;
	}


	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Column(name = "APP_VALUE", nullable = false, length = 1)
	public String getAppValue() {
		return appValue;
	}


	public void setAppValue(String appValue) {
		this.appValue = appValue;
	}

	@Column(name = "APP_NAME", nullable = true, length = 40)
	public String getAppName() {
		return appName;
	}


	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Column(name = "IS_USED")
	public String getIsUsed() {
		return isUsed;
	}


	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}
	@Column(name = "OPT_NO", nullable = true, length = 40)
	public String getOptNo() {
		return optNo;
	}


	public void setOptNo(String optNo) {
		this.optNo = optNo;
	}


	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	
}