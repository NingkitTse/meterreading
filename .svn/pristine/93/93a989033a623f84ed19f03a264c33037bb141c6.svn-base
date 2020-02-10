package com.wasion.meterreading.enums;

/**
 * <ul>
 * <li>0. SUCCESS(命令执行成功)</li>
 * <li>1. METHOD_NOT_ALLOWED（对象或资源不允许该操作）</li>
 * <li>2. FORBIDDEN（终端未注册）</li>
 * <li>3. NOT_FOUND（未发现该对象或资源）</li>
 * <li>4. INTERNAL_SERVER_ERROR（设备响应码错误等）</li>
 * <li>5. TIME_OUT（设备响应超时）</li>
 * <li>6. REQ_PARAM_ERROR（请求参数错误）</li>
 * <li>7. RESP_ERROR（设备响应报文错误）</li>
 * <li>8. UNAUTHORIZED（访问权限不允许）</li>
 * <li>9. BAD_REQUEST（请求格式错误，如少参数或编码等）</li>
 * <li>10. NOT_ACCEPTABLE（无任何首选的报文格式可以返回）</li>
 * <li>11. UNSUPPORTED_CONTENT_FORMAT（指定的报文格式不支</li>
 * <li>12. OFFLINE（设备未注册）</li>
 * <li>13. ADDR_OCCUPIED（设备的地址被其他设备占用）</li>
 * </ul>
 * 
 * @author w24882 xieningjie
 * @date 2019年12月12日
 */
public enum MobileConfirmStatusEnum {

	SUCCESS(0, "SUCCESS"),

	METHOD_NOT_ALLOWED(1, "METHOD_NOT_ALLOWED"),

	FORBIDDEN(2, "FORBIDDEN"),

	NOT_FOUND(3, "NOT_FOUND"),

	INTERNAL_SERVER_ERROR(4, "INTERNAL_SERVER_ERROR"),

	TIME_OUT(5, "TIME_OUT"),

	REQ_PARAM_ERROR(6, "REQ_PARAM_ERROR"),

	RESP_ERROR(7, "RESP_ERROR"),

	UNAUTHORIZED(8, "UNAUTHORIZED"),

	BAD_REQUEST(9, "BAD_REQUEST"),

	NOT_ACCEPTABLE(10, "NOT_ACCEPTABLE"),

	UNSUPPORTED_CONTENT_FORMAT(11, "UNSUPPORTED_CONTENT_FORMAT"),

	OFFLINE(12, "UNSUPPORTED_CONTENT_FORMAT"),

	ADDR_OCCUPIED(13, "UNSUPPORTED_CONTENT_FORMAT"),

	;

	private int code;
	private String name;

	private MobileConfirmStatusEnum(int code, String name) {
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

	public static MobileConfirmStatusEnum of(int code) {
		for (MobileConfirmStatusEnum el : MobileConfirmStatusEnum.values()) {
			if (el.getCode() == code) {
				return el;
			}
		}
		return null;
	}
}
