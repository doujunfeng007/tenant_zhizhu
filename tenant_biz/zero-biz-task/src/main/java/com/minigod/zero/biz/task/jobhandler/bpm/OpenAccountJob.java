package com.minigod.zero.biz.task.jobhandler.bpm;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.minigod.zero.bpm.feign.IBpmSyncClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 线上开户JOB
 */
@Slf4j
@Component
public class OpenAccountJob {

	@Resource
	private IBpmSyncClient bpmSyncClient;

	@XxlJob("OpenAccountJob")
	public void execute() {
		log.debug("OpenAccountJob start");
		try {
			bpmSyncClient.executeOpenAccount();
			log.debug("OpenAccountJob end");
			XxlJobHelper.handleSuccess();
		} catch (Exception e) {
			log.error("OpenAccountJob error", e);
			XxlJobHelper.handleFail();
		}
	}
}
