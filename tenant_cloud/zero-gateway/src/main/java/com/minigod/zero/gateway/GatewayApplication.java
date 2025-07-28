package com.minigod.zero.gateway;

import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 项目启动
 *
 * @author Chill
 */
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.APPLICATION_GATEWAY_NAME, GatewayApplication.class, args);
	}

}
