package com.minigod.zero.biz.task.jobhandler.bpm;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.minigod.zero.bpm.feign.IBpmSyncClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 客户入金数据传送任务
 */
@Slf4j
@Component
public class ClientDepositFundApplyJob {

	@Resource
	private IBpmSyncClient bpmSyncClient;

	@XxlJob("ClientDepositFundApplyJob")
	public void execute() {
		log.debug("ClientDepositFundApply start");
		try {
			bpmSyncClient.executeClientDepositFundApplyJob();
			log.debug("ClientDepositFundApply end");
			XxlJobHelper.handleSuccess();
		} catch (Exception e) {
			log.error("ClientDepositFundApply error", e);
			XxlJobHelper.handleFail();
		}
	}

}
