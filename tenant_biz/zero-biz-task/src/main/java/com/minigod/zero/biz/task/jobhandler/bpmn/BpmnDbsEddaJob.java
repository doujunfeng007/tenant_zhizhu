package com.minigod.zero.biz.task.jobhandler.bpmn;

import com.minigod.zero.bpmn.module.feign.IBpmnProfessionalInvestorPIClient;
import com.minigod.zero.bpmn.module.feign.IBpmnSyncClient;
import com.minigod.zero.bpmn.module.feign.IEddaFundClient;
import com.minigod.zero.core.tool.api.R;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName: com.minigod.zero.biz.task.jobhandler.bpmn.BpmnDbsEddaJob
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/17 15:58
 * @Version: 1.0
 */
@Slf4j
@Component
@AllArgsConstructor
public class BpmnDbsEddaJob {
	@Resource
	private IEddaFundClient eddaFundClient;


	/**
	 * edda入金申请
	 */
	@XxlJob("dbsEddaFundPaymentJob")
	public void dbsEddaFundPaymentJob() {
		log.debug("----dbsEddaFundPaymentJob start----");
		try {
			R r = eddaFundClient.dbsEddaFundPaymentTask();
			log.debug("----dbsEddaFundPaymentJob end----");
			if (r.isSuccess()) {
				XxlJobHelper.handleSuccess();
			} else {
				XxlJobHelper.handleFail();
			}
		} catch (Exception e) {
			log.error("******* dbsEddaFundPaymentJob error ******", e);
			XxlJobHelper.handleFail();
		}
	}

	/**
	 * edda授权申请
	 */
	@XxlJob("dbsEddaInfoBindJob")
	public void dbsEddaInfoBindJob() {
		log.debug("----dbsEddaInfoBindTask start----");
		try {
			R r = eddaFundClient.dbsEddaInfoBindTask();
			log.debug("----dbsEddaInfoBindTask end----");
			if (r.isSuccess()) {
				XxlJobHelper.handleSuccess();
			} else {
				XxlJobHelper.handleFail();
			}
		} catch (Exception e) {
			log.error("******* dbsEddaInfoBindTask error ******", e);
			XxlJobHelper.handleFail();
		}
	}
}
