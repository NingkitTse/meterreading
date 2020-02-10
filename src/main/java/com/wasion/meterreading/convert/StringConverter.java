package com.wasion.meterreading.convert;

import org.springframework.core.convert.converter.Converter;

public class StringConverter implements Converter<String, String> {

	@Override
	public String convert(String source) {
		if (null == source || "null".equals(source) || "undefined".equals(source)) {
			return null;
		}
		return source.toString();
	}

}
