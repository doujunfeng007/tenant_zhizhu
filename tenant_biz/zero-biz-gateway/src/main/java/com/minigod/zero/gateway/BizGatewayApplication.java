package com.minigod.zero.gateway;

import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.gateway.launch.LauncherConstant;
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
public class BizGatewayApplication {

	public static void main(String[] args) {
		ZeroApplication.run(LauncherConstant.SERVICE_BIZ_GATEWAY_SERVER, BizGatewayApplication.class, args);
	}

}
