package com.minigod.zero.customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/22 10:30
 * @description：
 */
@Component
@Configuration
public class RestTemplateAutoConfig {

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
