package com.minigod.zero.bpmn;

import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;

@ZeroCloudApplication
public class ZeroBpmnApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.SERVICE_BIZ_BPMN_NAME, ZeroBpmnApplication.class, args);
	}

}
