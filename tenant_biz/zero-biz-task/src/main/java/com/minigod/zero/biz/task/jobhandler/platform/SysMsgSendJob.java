package com.minigod.zero.biz.task.jobhandler.platform;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.minigod.zero.platform.feign.IPlatformSysMsgClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 发送定时系统通知
 */
@Slf4j
@Component
public class SysMsgSendJob {

	@Resource
	private IPlatformSysMsgClient platformSysMsgClient;


	@XxlJob("sysMsgSendJob")
	public void sysMsgSendJob() {
		try {
			platformSysMsgClient.pushUnsendSysMsg();
			XxlJobHelper.handleSuccess();
		} catch (Exception e) {
			log.error("发送定时系统通知异常", e);
			XxlJobHelper.handleFail();
		}
	}
}
