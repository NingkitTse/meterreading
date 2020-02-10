package com.wasion.meterreading.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
			return parseDate(source.trim(), "yyyy-MM-dd");
		}
		else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
			return parseDate(source.trim(), "yyyy-MM-dd HH:mm:ss");
		}
		else if (source.matches("^\\d{4}\\d{1,2}\\d{1,2}T\\d{1,2}\\d{1,2}\\d{1,2}$Z")) {
			return parseDate(source.trim(), "yyyyMMddTHHmmssZ");
		}
		else if (source.matches("^\\d{2}\\d{2}\\d{2}\\d{2}\\d{2}$")) {
			return parseDate(source.trim(), "yyMMddHHmm");
		}
		throw new IllegalArgumentException("Invalid value '" + source + "'");
	}

	public Date parseDate(String dateStr, String format) {
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
