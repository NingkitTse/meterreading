package com.wasion.meterreading.enums;

/**
 * <ul>
 * <li>1. 命令等待（wait）</li>
 * <li>2. 命令取消（cancle）</li>
 * <li>3. 命令已发往设备（send）</li>
 * <li>4. 命令过期（expired）</li>
 * <li>5. 命令下发成功（success）</li>
 * <li>6. 命令下发失败（failed）</li>
 * <li>8. 其他未知错误（undefined）</li>
 * </ul>
 * 
 * @author w24882 xieningjie
 * @date 2019年12月12日
 */
public enum MobileSendStatusEnum {

	WAIT(1, "WAIT"),

	CANCEL(2, "CANCEL"),

	SEND(3, "SEND"),

	EXPIRED(4, "EXPIRED"),

	SUCCESS(5, "SUCCESS"),

	FAILED(6, "FAILED"),

	UNDEFINED(8, "UNDEFINED");

	private int code;
	private String name;

	private MobileSendStatusEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static MobileSendStatusEnum of(int code) {
		for (MobileSendStatusEnum el : MobileSendStatusEnum.values()) {
			if (el.getCode() == code) {
				return el;
			}
		}
		return null;
	}

}
