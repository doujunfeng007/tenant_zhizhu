package com.minigod.zero.manage.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.entity.CashDepositWay;
import com.minigod.zero.manage.vo.BankSwiftCodeVO;
import com.minigod.zero.manage.vo.CBank;
import com.minigod.zero.manage.vo.DepositBankVO;
import com.minigod.zero.manage.vo.ReceivingBankVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/17 17:13
 * @description：银行卡处理接口
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_MANAGE_NAME
)
public interface ICashBankClient {
	String QUERY_BANK_SWIFT_CODE = "/cash/bank_swift_code";

	String QUERY_WITHDRAW_BANK_ID = "/cash/withdraw_bank_id";

	String QUERY_WITHDRAW_BANK_LIST = "/cash/withdraw_bank_list";

	String QUERY_DEPOSIT_BANK_LIST = "/cash/deposit_bank_list";

	String QUERY_DEPOSIT_BANK_BY_ID = "/cash/deposit_bank_detail";

	String QUERY_RECEIVING_ACCOUNT = "/case/receiving_account";

	String QUERY_DEPOSIT_WAYS = "/case/deposit_ways";

	String QUERY_RECEIVING_ACCOUNT_NEW = "/cash/receiving_account_new";

	String QUERY_RECEIVING_ACCOUNT_BY_DEPOSIT_BANK_ID = "/cash/receiving_account/depositBankById";

	String QUERY_PAYMENT_ACCOUNT_BY_WITHDRAWALS_BANK_ID = "/cash/payment_account/withdrawalsBankById";

	String QUERY_DEPOSIT_BANK_BY_DEPOSIT_BANK_BY_ID = "/cash/deposit_bank_list/depositBankById";

	@GetMapping(QUERY_WITHDRAW_BANK_LIST)
	R<List<CBank>> withdrawBankList(@RequestParam("bankType") Integer bankType);

	@GetMapping(QUERY_BANK_SWIFT_CODE)
	R<BankSwiftCodeVO> bankSwiftCode(@RequestParam("bankCode") String bankCode);

	@GetMapping(QUERY_WITHDRAW_BANK_ID)
	R<Long> withdrawBankId(@RequestParam("bankCode") String bankCode, @RequestParam("bankId") String bankId);

	@GetMapping(QUERY_DEPOSIT_BANK_LIST)
	R<List<DepositBankVO>> depositBankList(@RequestParam("bankType") Integer bankType,
										   @RequestParam("currency") Integer currency,
										   @RequestParam("supportType") String supportType,
										   @RequestParam("language") String language);

	@GetMapping(QUERY_DEPOSIT_BANK_BY_ID)
	R<DepositBankVO> findDepositBankById(@RequestParam("depositId") Long depositId);

	@GetMapping(QUERY_RECEIVING_ACCOUNT_NEW)
	R<ReceivingBankVO> findReceivingBankNew(@RequestParam("currency") Integer currency,
											@RequestParam("supportType") String supportType);


	@GetMapping(QUERY_RECEIVING_ACCOUNT)
	R<ReceivingBankVO> findReceivingBankById(@RequestParam("payeeBankDetailId") Long payeeBankDetailId);

	@GetMapping(QUERY_RECEIVING_ACCOUNT_BY_DEPOSIT_BANK_ID)
	R<ReceivingBankVO> findReceivingBankByDepositBankById(@RequestParam("depositBankById") Long depositBankById,
														  @RequestParam("currency") String currency,
														  @RequestParam("supportType") String supportType);

	@GetMapping(QUERY_PAYMENT_ACCOUNT_BY_WITHDRAWALS_BANK_ID)
	R<ReceivingBankVO> findPaymentBankByWithdrawalsBankById(@RequestParam("withdrawalsBankById") Long withdrawalsBankById,
															@RequestParam("currency") String currency,
															@RequestParam("supportType") String supportType);

	@GetMapping(QUERY_DEPOSIT_BANK_BY_DEPOSIT_BANK_BY_ID)
	R<List<DepositBankVO>> getDepositBankByPayeeBankDetailId(@RequestParam("payeeBankDetailId") Long payeeBankDetailId,
															 @RequestParam("supportType") String supportType,
															 @RequestParam("currency") Integer currency);

	@GetMapping(QUERY_DEPOSIT_WAYS)
	R<List<CashDepositWay>> selectDepositWay(@RequestParam("currency") Integer currency,
                                             @RequestParam("bankType") Integer bankType);
}
