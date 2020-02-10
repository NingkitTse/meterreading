package com.wasion.meterreading.exception;

/**
 * MeterReading 异常枚举类
 * 
 * @author w24882 xieningjie
 * @date 2019年12月12日
 */
public enum MRExceptionEnum {

	DeviceExsit("DeviceExsit", "设备已存在"),
	
	DeviceNotExsit("DeviceNotExsit", "设备不存在"),

	CommunicationException("CommunicationException", "通信异常"),

	SystemConfigurationException("SystemConfigurationException", "系统配置异常"),

	CommnadTaskNotExsit("CommnadTaskNotExsit", "命令任务不存在"),

	CommandParamLenInconsistent("CommandParamLenInconsistent", "命令入参长度与配置不一致"),

	InvalidReportData("InvalidReportData", "无效上报数据"),

	QueryDeviceFailed("QueryDeviceFailed", "查询设备失败"),
	
	FrameValueTraverseException("FrameValueTraverseException", "帧数据遍历异常"),
	
	CommandParamFormatInvalid("CommandParamFormatInvalid", "命令参数格式不正确"),

	CommandBaseUrlEmpty("CommandBaseUrlEmpty", "命令BaseUrl为空"),
	
	NotSupportedCacheKey("NotSupportedCacheKey", "暂不支持该数据的缓存"),
	
	InSecureIpBlank("InSecureIpBlank", "非法访问，IP不能为空"),
	
	IPAccessExccedUpperLimit("IPAccessExccedUpperLimit", "IP访问超过最大上限"),
	;

	private String code;
	private String msg;

	private MRExceptionEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
