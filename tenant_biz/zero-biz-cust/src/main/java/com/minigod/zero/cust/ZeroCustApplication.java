package com.minigod.zero.cust;

import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@ZeroCloudApplication
public class ZeroCustApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.SERVICE_BIZ_CUST_NAME, ZeroCustApplication.class, args);
	}

}
