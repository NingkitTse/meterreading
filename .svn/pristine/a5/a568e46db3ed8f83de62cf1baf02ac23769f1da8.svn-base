package com.wasion.meterreading.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * 安全参数、类型 可通过反射的方式 检测有该注解的地方对参数进行加解密
 * 
 * @author w24882 xieningjie
 * @date 2019年11月18日
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
public @interface SecurityParameter {

	/**
	 * 入参是否解密，默认解密
	 */
	boolean inDecode() default true;

	/**
	 * 出参是否加密，默认加密
	 */
	boolean outEncode() default true;
}
