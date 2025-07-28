package com.minigod.zero.bpm.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
	value = AppConstant.SERVICE_BIZ_BPM_NAME
)
public interface IBpmSyncClient {
	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/task";
	String OPEN_ACCOUNT_JOB = API_PREFIX + "/open_account_job";
	String ACCOUNT_CA_AUTH_JOB = API_PREFIX + "/account_ca_auth_job";
	String CHANGE_ACCOUNT_JOB = API_PREFIX + "/change_account_job";
	String CLIENT_DEPOSIT_FUND_APPLY_JOB = API_PREFIX + "/client_deposit_fund_apply_job";
	String CLIENT_EDDA_INFO_JOB = API_PREFIX + "/client_edda_info_job";
	String CLIENT_FUND_WITHDRAW_APPLY_JOB = API_PREFIX + "/client_fund_withdraw_apply_job";

	@PostMapping(OPEN_ACCOUNT_JOB)
	R executeOpenAccount();

	@PostMapping(CHANGE_ACCOUNT_JOB)
	R executeChangeAccount();

	@PostMapping(CLIENT_DEPOSIT_FUND_APPLY_JOB)
	R executeClientDepositFundApplyJob();

	@PostMapping(CLIENT_EDDA_INFO_JOB)
	R executeClientEddaInfoJob();

	@PostMapping(CLIENT_FUND_WITHDRAW_APPLY_JOB)
	R executeClientFundWithdrawApplyJob();
}
