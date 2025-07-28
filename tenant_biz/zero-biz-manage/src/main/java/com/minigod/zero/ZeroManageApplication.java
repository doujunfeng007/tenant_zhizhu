package com.minigod.zero;

import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author Administrator
 */
@CrossOrigin
@ZeroCloudApplication
public class ZeroManageApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.SERVICE_BIZ_MANAGE_NAME, ZeroManageApplication.class, args);
	}

}
