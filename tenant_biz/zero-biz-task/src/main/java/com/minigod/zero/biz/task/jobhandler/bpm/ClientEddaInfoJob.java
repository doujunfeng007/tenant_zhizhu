package com.minigod.zero.biz.task.jobhandler.bpm;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.minigod.zero.bpm.feign.IBpmSyncClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 客户edda数据上送
 */
@Slf4j
@Component
public class ClientEddaInfoJob {

	@Resource
	private IBpmSyncClient bpmSyncClient;

	@XxlJob("ClientEddaInfoJob")
	public void execute() {
		log.debug("ClientEddaInfoJob start");
		try {
			bpmSyncClient.executeClientEddaInfoJob();
			log.debug("ClientEddaInfoJob end");
			XxlJobHelper.handleSuccess();
		} catch (Exception e) {
			log.error("ClientEddaInfoJob error", e);
			XxlJobHelper.handleFail();
		}
	}

}
