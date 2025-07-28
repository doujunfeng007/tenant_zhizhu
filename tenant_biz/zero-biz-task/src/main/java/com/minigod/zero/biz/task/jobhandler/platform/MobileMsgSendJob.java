package com.minigod.zero.biz.task.jobhandler.platform;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.minigod.zero.platform.feign.IPlatformMobileContentClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 发送定时短信
 */
@Slf4j
@Component
public class MobileMsgSendJob {

	@Resource
	private IPlatformMobileContentClient platformMobileContentClient;

	@XxlJob("mobileMsgSendJob")
	public void mobileMsgSendJob() {
		try {
			platformMobileContentClient.pushUnsendMobileMsg();
			XxlJobHelper.handleSuccess();
		} catch (Exception e) {
			log.error("发送定时短信异常", e);
			XxlJobHelper.handleFail();
		}
	}

}
