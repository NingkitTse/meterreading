package com.wasion.meterreading.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 移动平台配置
 * 
 * @author w24882 xieningjie
 * @date 2019年12月12日
 */
@ConfigurationProperties(prefix = OnenetProperties.ONENET_PREFIX)
public class OnenetProperties {

	public static final String ONENET_PREFIX = "10086.iot.v1910";
	private String apiKey;
	private Integer objId;
	private Integer objInstId;
	private Integer readResId;
	private Long expireOffset;
	private String startChar;
	private String meterType;
	private String endChar;
	private Integer writeMode;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public Integer getObjId() {
		return objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public Integer getObjInstId() {
		return objInstId;
	}

	public void setObjInstId(Integer objInstId) {
		this.objInstId = objInstId;
	}

	public Integer getReadResId() {
		return readResId;
	}

	public void setReadResId(Integer readResId) {
		this.readResId = readResId;
	}

	public Long getExpireOffset() {
		return expireOffset;
	}

	public void setExpireOffset(Long expireOffset) {
		this.expireOffset = expireOffset;
	}

	public String getStartChar() {
		return startChar;
	}

	public void setStartChar(String startChar) {
		this.startChar = startChar;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	public String getEndChar() {
		return endChar;
	}

	public void setEndChar(String endChar) {
		this.endChar = endChar;
	}

	public Integer getWriteMode() {
		return writeMode;
	}

	public void setWriteMode(Integer writeMode) {
		this.writeMode = writeMode;
	}

}
