package com.minigod.zero.cms.feign.oms;

import com.minigod.zero.cms.entity.oms.CashDepositBankCurrencyGuideEntity;
import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
	value = AppConstant.SERVICE_BIZ_CMS_NAME
)
public interface ICashDepositBankCurrencyGuideClient {

	String CASH_DEPOSIT_BANK_CURRENCY_GUIDE_DETAIL = AppConstant.FEIGN_API_PREFIX + "/cashDepositBankCurrencyGuideDetail";

	@PostMapping(CASH_DEPOSIT_BANK_CURRENCY_GUIDE_DETAIL)
	CashDepositBankCurrencyGuideEntity cashDepositBankCurrencyGuideDetail(@RequestBody CashDepositBankCurrencyGuideEntity cashDepositBankCurrencyGuide);

}
