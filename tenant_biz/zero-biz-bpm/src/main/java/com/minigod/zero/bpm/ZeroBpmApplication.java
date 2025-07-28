package com.minigod.zero.bpm;

import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;

@ZeroCloudApplication
public class ZeroBpmApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.SERVICE_BIZ_BPM_NAME, ZeroBpmApplication.class, args);
	}

}
