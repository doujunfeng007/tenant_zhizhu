package com.minigod.zero.manage.proxy;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.feign.ICashBankClient;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wengzejie
 * @ClassName CashBankProxy.java
 * @createTime 2024年10月11日 15:37:00
 */
@RestController
@RequestMapping(AppConstant.PROXY_API_PREFIX + "/cash")
@Api(value = "银行卡内网服务", tags = "银行卡内网服务")
@Slf4j
public class CashBankProxy {

	@Autowired
	private ICashBankClient cashBankClient;

	@GetMapping("/withdraw_bank_list")
	public R withdrawBankList(@RequestParam("bankType") Integer bankType) {
		return cashBankClient.withdrawBankList(bankType);
	}

	@GetMapping("/bank_swift_code")
	public R bankSwiftCode(@RequestParam("bankCode") String bankCode) {
		return cashBankClient.bankSwiftCode(bankCode);
	}

	@GetMapping("/deposit_bank_list")
	public R depositBankList(@RequestParam("bankType") Integer bankType, @RequestParam("currency") Integer currency,
							 @RequestParam("supportType") String supportType, @RequestParam("language") String language) {
		return cashBankClient.depositBankList(bankType, currency, supportType, language);
	}

	@GetMapping("/deposit_bank_detail")
	public R findDepositBankById(@RequestParam("depositId") Long depositId) {
		return cashBankClient.findDepositBankById(depositId);
	}

	@GetMapping("/receiving_account_new")
	public R findReceivingBankNew(@RequestParam("currency") Integer currency, @RequestParam("supportType") String supportType) {
		return cashBankClient.findReceivingBankNew(currency, supportType);
	}

	@GetMapping("/receiving_account")
	public R findReceivingBankById(@RequestParam("payeeBankDetailId") Long payeeBankDetailId) {
		return cashBankClient.findReceivingBankById(payeeBankDetailId);
	}

	@GetMapping("/receiving_account/depositBankById")
	public R findReceivingBankByDepositBankById(@RequestParam("depositBankById") Long depositBankById,
												@RequestParam("currency") String currency, @RequestParam("supportType") String supportType) {
		return cashBankClient.findReceivingBankByDepositBankById(depositBankById, currency, supportType);
	}

	@GetMapping("/deposit_bank_list/depositBankById")
	public R getDepositBankByPayeeBankDetailId(@RequestParam("payeeBankDetailId") Long payeeBankDetailId,
											   @RequestParam("supportType") String supportType, @RequestParam("currency") Integer currency) {
		return cashBankClient.getDepositBankByPayeeBankDetailId(payeeBankDetailId, supportType, currency);
	}

	@GetMapping("/deposit_ways")
	public R selectDepositWay(@RequestParam("currency") Integer currency, @RequestParam("bankType") Integer bankType) {
		return cashBankClient.selectDepositWay(currency, bankType);
	}
}
