package com.minigod.zero.bpmn.module.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/15 14:31
 * @description：
 */
@Configuration
public class GolobalAutoConfig {

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
