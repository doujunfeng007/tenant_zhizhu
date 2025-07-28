package com.minigod.zero.dbs;

import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * 启动程序
 *
 * @author zsdp
 */
@CrossOrigin
@EnableCaching
@ZeroCloudApplication
@ComponentScan(basePackages = "com.minigod")
@EnableFeignClients(basePackages = "com.minigod.zero.dbs.feign")
public class DbsGatewayApplication {

    public static void main(String[] args) {
		ZeroApplication.run("zero-biz-dbs", DbsGatewayApplication.class, args);
    }

}
