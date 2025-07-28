package com.minigod.zero.bpmn.module.fundTrans.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(
	value = AppConstant.SERVICE_BIZ_BPMN_NAME
)
public interface IFundTransClient {
	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/fundTrans";

	String FUND_TRANS_WITHDRAW_JOB = API_PREFIX + "/fundTransWithdrawJob";

	String FUND_TRANS_DEPOSIT_JOB = API_PREFIX + "/fundTransDepositJob";


	/**
	 * 划拨调度
	 *
	 * @param map
	 * @return
	 */
	@PostMapping(FUND_TRANS_WITHDRAW_JOB)
	R fundTransWithdrawJob(Map<String, Object> map);

	/**
	 * 入账调度
	 * @param map
	 * @return
	 */
	@PostMapping(FUND_TRANS_DEPOSIT_JOB)
	R fundTransDepositJob(Map<String, Object> map);
}
