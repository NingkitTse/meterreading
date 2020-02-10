package com.wasion.meterreading.domain.dto;

public class ResponseDto<T> {

	private boolean success;

	private String msg;

	private T content;

	public static <U> ResponseDto<U> success(U data) {
		ResponseDto<U> responseDto = new ResponseDto<>();
		responseDto.setSuccess(true);
		responseDto.setContent(data);
		return responseDto;
	}

	public static <U> ResponseDto<U> failure(String msg) {
		ResponseDto<U> responseDto = new ResponseDto<>();
		responseDto.setSuccess(false);
		responseDto.setMsg(msg);
		return responseDto;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

}
