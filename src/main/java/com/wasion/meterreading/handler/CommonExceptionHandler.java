package com.wasion.meterreading.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.alibaba.fastjson.JSON;
import com.wasion.meterreading.domain.dto.ResponseDto;
import com.wasion.meterreading.exception.MRException;

/**
 * 统一异常处理类
 * 
 * @author w24882
 * @date 2019年11月6日
 */
@ControllerAdvice
public class CommonExceptionHandler {

	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseDto<String> exceptionHandler(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		List<String> msgs = new ArrayList<>();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		fieldErrors.forEach(error -> {
			msgs.add(error.getDefaultMessage());
		});
		return ResponseDto.failure(JSON.toJSONString(msgs));
	}

	@ResponseBody
	@ExceptionHandler(MRException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseDto<String> exceptionHandler(MRException e) {
		return ResponseDto.failure(JSON.toJSONString(e.getMsg()));
	}

}
