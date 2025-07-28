
package com.minigod.zero.log;

import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;

/**
 * 日志服务
 *
 * @author Chill
 */
@ZeroCloudApplication
public class LogApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.APPLICATION_LOG_NAME, LogApplication.class, args);
	}

}
