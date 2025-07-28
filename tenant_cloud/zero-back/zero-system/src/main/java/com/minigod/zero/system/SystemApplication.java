
package com.minigod.zero.system;

import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.cloud.feign.EnableZeroFeign;
import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;

/**
 * 系统模块启动器
 * @author Chill
 */
@EnableZeroFeign
@ZeroCloudApplication
public class SystemApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.APPLICATION_SYSTEM_NAME, SystemApplication.class, args);
	}

}

