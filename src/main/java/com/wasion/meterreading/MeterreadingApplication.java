package com.wasion.meterreading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.wasion.meterreading, telecom.sdk.service")
public class MeterreadingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeterreadingApplication.class, args);
	}

}
