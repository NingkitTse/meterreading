package com.wasion.meterreading.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TelecomProperties.TELECOM_PREFIX)
public class TelecomProperties {

	public static final String TELECOM_PREFIX = "10086.iot";
	
}
