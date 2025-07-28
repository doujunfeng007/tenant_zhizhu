
package com.minigod.zero.flow;

import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;

/**
 * Flowable启动器
 *
 * @author Chill
 */
//@SeataCloudApplication
@ZeroCloudApplication
public class FlowApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.APPLICATION_FLOW_NAME, FlowApplication.class, args);
	}

}

