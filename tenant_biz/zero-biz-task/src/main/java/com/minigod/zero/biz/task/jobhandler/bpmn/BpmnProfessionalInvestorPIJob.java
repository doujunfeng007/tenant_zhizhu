package com.minigod.zero.biz.task.jobhandler.bpmn;

import com.minigod.zero.bpmn.module.feign.IBpmnProfessionalInvestorPIClient;
import com.minigod.zero.core.tool.api.R;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 专业投资者PI认证流程定时任务同步
 *
 * @author eric
 * @since 2024-05-09 20:57:09
 */
@Slf4j
@Component
public class BpmnProfessionalInvestorPIJob {
	@Resource
	private IBpmnProfessionalInvestorPIClient bpmnProfessionalInvestorPIClient;

	@XxlJob("piFlowCheckJob")
	public void amlCheckJob() {
		log.debug("----piFlowCheckJob start----");
		try {
			R r = bpmnProfessionalInvestorPIClient.piFlowCallBack();
			log.debug("----piFlowCheckJob end----");
			if (r.isSuccess()) {
				XxlJobHelper.handleSuccess();
			} else {
				XxlJobHelper.handleFail();
			}
		} catch (Exception e) {
			log.error("******* piFlowCheckJob error ******", e);
			XxlJobHelper.handleFail();
		}
	}
}
