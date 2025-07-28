/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.zero.customer;

import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * 账户模块启动器
 * @author zsdp
 */
@ZeroCloudApplication
@ComponentScan({"com.minigod.zero.**"})
public class CustomerApplication {

	public static void main(String[] args) {
		ZeroApplication.run("minigod-customer", CustomerApplication.class, args);
	}



	@Bean
	public CommandLineRunner printRedisInfo(@Value("${spring.redis.host}") String redisHost,
											@Value("${spring.redis.port}") String redisPort,
											@Value("${spring.redis.database}") String database,
											@Value("${spring.redis.password:}") String redisPassword
	) {
		return args -> {
			System.out.println("Connected to Redis:");
			System.out.println("Host: " + redisHost);
			System.out.println("Port: " + redisPort);
			System.out.println("database: " + database);
			System.out.println("database: " + database.length());
			System.out.println("Password: " + (redisPassword.isEmpty() ? "No password" : redisPassword));
		};
	}
}

