package com.wasion.meterreading.config;

import java.util.List;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.wasion.meterreading.convert.StringConverter;
import com.wasion.meterreading.convert.StringToDateConverter;
import com.wasion.meterreading.convert.StringToTNTConverter;
import com.wasion.meterreading.util.ContextProvider;

@SpringBootConfiguration
public class WebConfigurer implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		ApplicationContext context = ContextProvider.getContext();
		StringToDateConverter stringToDateConverter = context.getBean(StringToDateConverter.class);
		StringToTNTConverter stringToTNTConverter = context.getBean(StringToTNTConverter.class);
		registry.addConverter(stringToDateConverter);
		registry.addConverter(stringToTNTConverter);
		registry.addConverter(new StringConverter());
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	}

}
