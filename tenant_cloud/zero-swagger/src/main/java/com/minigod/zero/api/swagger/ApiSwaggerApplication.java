
package com.minigod.zero.api.swagger;


import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;

/**
 * swagger聚合启动器
 *
 * @author Chill
 */
@ZeroCloudApplication
public class ApiSwaggerApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.APPLICATION_SWAGGER_NAME, ApiSwaggerApplication.class, args);
	}

}
