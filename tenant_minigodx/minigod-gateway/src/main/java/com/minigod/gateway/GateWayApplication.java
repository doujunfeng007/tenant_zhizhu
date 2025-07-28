/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.gateway;

import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * 项目启动
 *
 * @author zsdp
 */
@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients({"com.minigod"})
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication(
	scanBasePackages = {"com.minigod.**"}
)
public class GateWayApplication {
	public static void main(String[] args) {
		ZeroApplication.run("minigod-gateway", GateWayApplication.class, args);
	}
}
