
package com.minigod.zero.auth;


import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;

/**
 * 用户认证服务器
 *
 * @author Chill
 */
@ZeroCloudApplication
public class AuthApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.APPLICATION_AUTH_NAME, AuthApplication.class, args);
	}

}
