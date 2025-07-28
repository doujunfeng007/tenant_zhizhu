
package com.minigod.zero.broker;

import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;

/**
 * UReport启动器
 *
 * @author Chill
 */
@ZeroCloudApplication
public class ReportApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.APPLICATION_REPORT_NAME, ReportApplication.class, args);
	}

}
