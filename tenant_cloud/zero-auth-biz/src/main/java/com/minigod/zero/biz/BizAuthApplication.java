
package com.minigod.zero.biz;


import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;

/**
 * 用户认证服务器
 *
 * @author Chill
 */
@ZeroCloudApplication
public class BizAuthApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.APPLICATION_AUTH_NAME + "-biz", BizAuthApplication.class, args);
	}

}
