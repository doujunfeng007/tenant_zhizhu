
package com.minigod.zero.resource;

import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;

/**
 * 资源启动器
 *
 * @author Chill
 */
@ZeroCloudApplication
public class ResourceApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.APPLICATION_RESOURCE_NAME, ResourceApplication.class, args);
	}

}

