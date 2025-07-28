package com.minigod.zero.biz.task;

import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ZeroCloudApplication
public class BizTaskApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.SERVICE_BIZ_TASK_NAME, BizTaskApplication.class, args);
	}

}
