
package com.minigod.zero.platform;

import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 资源启动器
 *
 * @author Chill
 */
@EnableDiscoveryClient
@EnableFeignClients({"com.minigod"})
@SpringBootApplication(
	scanBasePackages = {"com.minigod.**"}
)
public class PlatformApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.APPLICATION_PLATFORM_NAME, PlatformApplication.class, args);
	}

}

