package com.minigod.zero.biz.task.jobhandler.bpm;


import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.minigod.zero.bpm.feign.IBpmSyncClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 客户线上修改资料 JOB
 */

@Slf4j
@Component
public class SecUserInfoChangeJob {

	@Resource
	private IBpmSyncClient bpmSyncClient;

	@XxlJob("SecUserInfoChangeJob")
	public void execute() {
		log.debug("SecUserInfoChangeJob start");
		try {
			bpmSyncClient.executeChangeAccount();
			log.debug("SecUserInfoChangeJob end");
			XxlJobHelper.handleSuccess();
		} catch (Exception e) {
			log.error("SecUserInfoChangeJob error", e);
			XxlJobHelper.handleFail();
		}
	}
}
