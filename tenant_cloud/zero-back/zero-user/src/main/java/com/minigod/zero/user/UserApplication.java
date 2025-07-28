
package com.minigod.zero.user;

import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 用户启动器
 *
 * @author Chill
 */
@EnableAsync
@ZeroCloudApplication
public class UserApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.APPLICATION_USER_NAME, UserApplication.class, args);
	}

}
