
package com.minigod.zero.develop;

import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;

/**
 * Develop启动器
 *
 * @author Chill
 */
@ZeroCloudApplication
public class DevelopApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.APPLICATION_DEVELOP_NAME, DevelopApplication.class, args);
	}

}

