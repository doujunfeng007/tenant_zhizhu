package com.minigod.zero.biz.task.jobhandler.platform;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 月结单发送失败或退信邮件发送至香港SMTP（每月5号5:00生成月结单文件后发送）
 * @author Zhe.Xiao
 */
@Slf4j
@Component
public class MonthlyStatementSendHkSmtpJob {

	@Resource
	private IPlatformMsgClient platformMsgClient;

	private final String MONTHLY = "monthly";

	@XxlJob("monthlyStatementSendHkSmtpJob")
	public void monthlyStatementSendHkSmtpJob() {
		try {
			platformMsgClient.statementSendHkSmtp(MONTHLY);
			XxlJobHelper.handleSuccess();
		} catch (Exception e) {
			log.error("月结单邮件发送Smtp服务JOB异常", e);
			XxlJobHelper.handleFail();
		}
	}

}
