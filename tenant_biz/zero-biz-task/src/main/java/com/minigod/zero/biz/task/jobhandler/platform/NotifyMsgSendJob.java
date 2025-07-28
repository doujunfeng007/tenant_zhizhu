package com.minigod.zero.biz.task.jobhandler.platform;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.minigod.zero.platform.feign.IPlatformInvestMsgClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 发送定时推送消息
 */
@Slf4j
@Component
public class NotifyMsgSendJob {

	@Resource
	private IPlatformInvestMsgClient platformInvestMsgClient;


	@XxlJob("notifyMsgSendJob")
	public void notifyMsgSendJob() {
		try {
			platformInvestMsgClient.pushUnsendInvestMsg();
			XxlJobHelper.handleSuccess();
		} catch (Exception e) {
			log.error("发送定时推送消息异常", e);
			XxlJobHelper.handleFail();
		}
	}

}
