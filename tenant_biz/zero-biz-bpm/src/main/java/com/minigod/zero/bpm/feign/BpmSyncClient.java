package com.minigod.zero.bpm.feign;

import com.minigod.zero.bpm.service.ICashDepositFundsService;
import com.minigod.zero.bpm.service.ICashEddaInfoService;
import com.minigod.zero.bpm.service.ICashExtractingMoneyService;
import com.minigod.zero.bpm.service.openAccount.IChangeAccountService;
import com.minigod.zero.bpm.service.openAccount.IOpenAccountService;
import com.minigod.zero.core.tool.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class BpmSyncClient implements IBpmSyncClient{

	@Resource
	private IOpenAccountService openAccountService;

	@Resource
	private IChangeAccountService changeAccountService;

	@Resource
	private ICashDepositFundsService cashDepositFundsService;

	@Resource
	private ICashEddaInfoService cashEddaInfoService;

	@Resource
	private ICashExtractingMoneyService cashExtractingMoneyService;

	@Override
	@PostMapping(OPEN_ACCOUNT_JOB)
	public R executeOpenAccount() {
		return openAccountService.executeOpenAccount();
	}

	@Override
	@PostMapping(CHANGE_ACCOUNT_JOB)
	public R executeChangeAccount() {
		return changeAccountService.executeChangeAccount();
	}

	@Override
	public R executeClientDepositFundApplyJob() {
		return cashDepositFundsService.executeClientDepositFundApplyJob();
	}

	@Override
	public R executeClientEddaInfoJob() {
		return cashEddaInfoService.executeClientEddaInfoJob();
	}

	@Override
	public R executeClientFundWithdrawApplyJob() {
		return cashExtractingMoneyService.executeClientFundWithdrawApplyJob();
	}
}
