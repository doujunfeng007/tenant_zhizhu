package com.xxl.job.admin;

import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@SpringBootApplication
public class JobAdminApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.APPLICATION_JOB_NAME, JobAdminApplication.class, args);
	}

}
