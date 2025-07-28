package com.minigod.zero.biz.task.jobhandler.bpm;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.minigod.zero.bpm.feign.IBpmSyncClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 客户出金数据传送任务
 */
@Slf4j
@Component
public class ClientFundWithdrawApplyJob {

	@Resource
	private IBpmSyncClient bpmSyncClient;

	@XxlJob("ClientFundWithdrawApplyJob")
	public void execute() {
		log.debug("ClientFundWithdrawApply start");
		try {
			bpmSyncClient.executeClientFundWithdrawApplyJob();
			log.debug("ClientFundWithdrawApply end");
			XxlJobHelper.handleSuccess();
		} catch (Exception e) {
			log.error("ClientFundWithdrawApply error", e);
			XxlJobHelper.handleFail();
		}
	}

}
