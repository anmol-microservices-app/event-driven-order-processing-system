package com.kafkaproject.orderService.appconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {

	@Bean
	ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
