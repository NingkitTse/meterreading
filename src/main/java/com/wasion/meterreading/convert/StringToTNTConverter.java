package com.wasion.meterreading.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.wasion.meterreading.enums.TelecomNotifyType;

/**
 * String 转枚举类型转换器
 * 
 * @author w24882
 * @date 2019年10月28日
 */
@Component
public class StringToTNTConverter implements Converter<String, TelecomNotifyType> {

	@Override
	public TelecomNotifyType convert(String source) {
		return getEnum(source);
	}

	public static TelecomNotifyType getEnum(String source) {
		for (TelecomNotifyType enumObj : TelecomNotifyType.values()) {
			if (source.equals(String.valueOf(enumObj.getNotifyType()))) {
				return enumObj;
			}
		}
		return null;
	}

}
