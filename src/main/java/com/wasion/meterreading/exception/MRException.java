package com.wasion.meterreading.exception;

/**
 * MeterReading Exception
 * 
 * @author w24882
 * @date 2019年10月16日
 */
public class MRException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2653429241490668344L;

	private String code;

	private String msg;

	private Object[] args;

	public MRException() {
	}

	public MRException(String code) {
		super();
		this.code = code;
	}

	public MRException(String code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public MRException(MRExceptionEnum e, Object... args) {
		super(e.getMsg());
		this.code = e.getCode();
		this.msg = e.getMsg();
		this.args = args;
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

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

}
