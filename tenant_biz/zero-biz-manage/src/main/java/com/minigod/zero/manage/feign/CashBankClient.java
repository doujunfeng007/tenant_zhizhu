package com.minigod.zero.manage.feign;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.entity.CashDepositWay;
import com.minigod.zero.manage.entity.CashWithdrawalsBankEntity;
import com.minigod.zero.manage.service.ICashPayeeBankDetailService;
import com.minigod.zero.manage.service.ICashWithdrawalsBankService;
import com.minigod.zero.manage.vo.BankSwiftCodeVO;
import com.minigod.zero.manage.vo.DepositBankVO;
import com.minigod.zero.manage.vo.ReceivingBankVO;
import com.minigod.zero.manage.service.ICashDepositBankService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/17 17:16
 * @description：
 */
@RestController
public class CashBankClient implements ICashBankClient {

	@Resource
	private ICashDepositBankService cashDepositBankService;

	@Resource
	private ICashWithdrawalsBankService cashWithdrawalsBankService;

	@Resource
	private ICashPayeeBankDetailService cashPayeeBankDetailService;


	@Override
	@GetMapping(QUERY_WITHDRAW_BANK_LIST)
	public R withdrawBankList(@RequestParam("bankType") Integer bankType) {
		return cashWithdrawalsBankService.withdrawBankList(bankType);
	}

	@Override
	@GetMapping(QUERY_BANK_SWIFT_CODE)
	public R<BankSwiftCodeVO> bankSwiftCode(@RequestParam("bankCode") String bankCode) {
		return cashWithdrawalsBankService.bankSwiftCode(bankCode);
	}

	@Override
	@GetMapping(QUERY_WITHDRAW_BANK_ID)
	public R<Long> withdrawBankId(@RequestParam("bankCode") String bankCode, @RequestParam("bankId") String bankId) {
		CashWithdrawalsBankEntity cashWithdrawalsBank = cashWithdrawalsBankService.selectByBankCodeAndId(bankCode, bankId);
		if (cashWithdrawalsBank == null) {
			return R.data(null);
		}
		return R.data(cashWithdrawalsBank.getId());
	}

	@Override
	@GetMapping(QUERY_DEPOSIT_BANK_LIST)
	public R<List<DepositBankVO>> depositBankList(@RequestParam("bankType") Integer bankType,
												  @RequestParam("currency") Integer currency,
												  @RequestParam("supportType") String supportType,
												  @RequestParam("language") String language) {
		return R.data(cashDepositBankService.depositBankList(bankType, currency, supportType, language));
	}

	@Override
	public R<DepositBankVO> findDepositBankById(@RequestParam("depositId") Long depositId) {
		return R.data(cashDepositBankService.getDepositBankByDepositId(depositId));
	}

	@Override
	@GetMapping(QUERY_RECEIVING_ACCOUNT_NEW)
	public R<ReceivingBankVO> findReceivingBankNew(@RequestParam("currency") Integer currency,
												   @RequestParam("supportType") String supportType) {
		return R.data(cashPayeeBankDetailService.findReceivingBank(currency, supportType));
	}

	@Override
	@GetMapping(QUERY_RECEIVING_ACCOUNT)
	public R<ReceivingBankVO> findReceivingBankById(@RequestParam("payeeBankDetailId") Long payeeBankDetailId) {
		return R.data(cashPayeeBankDetailService.findReceivingBankById(payeeBankDetailId));
	}

	@Override
	@GetMapping(QUERY_RECEIVING_ACCOUNT_BY_DEPOSIT_BANK_ID)
	public R<ReceivingBankVO> findReceivingBankByDepositBankById(@RequestParam("depositBankById") Long depositBankById,
																 @RequestParam("currency") String currency,
																 @RequestParam("supportType") String supportType) {
		return R.data(cashPayeeBankDetailService.findReceivingBankByDepositBankById(depositBankById, currency, supportType));
	}

	@Override
	@GetMapping(QUERY_PAYMENT_ACCOUNT_BY_WITHDRAWALS_BANK_ID)
	public R<ReceivingBankVO> findPaymentBankByWithdrawalsBankById(@RequestParam("withdrawalsBankById") Long withdrawalsBankById,
																   @RequestParam("currency") String currency,
																   @RequestParam("supportType") String supportType) {
		return R.data(cashPayeeBankDetailService.findPaymentBankByWithdrawalsBankById(withdrawalsBankById, currency, supportType));
	}

	@Override
	@GetMapping(QUERY_DEPOSIT_BANK_BY_DEPOSIT_BANK_BY_ID)
	public R<List<DepositBankVO>> getDepositBankByPayeeBankDetailId(@RequestParam("payeeBankDetailId") Long payeeBankDetailId,
																	@RequestParam("supportType") String supportType,
																	@RequestParam("currency") Integer currency) {
		return R.data(cashDepositBankService.getDepositBankByPayeeBankDetailId(payeeBankDetailId, supportType, currency));
	}

	@Override
	@GetMapping(QUERY_DEPOSIT_WAYS)
	public R<List<CashDepositWay>> selectDepositWay(@RequestParam("currency") Integer currency,
													@RequestParam("bankType") Integer bankType) {
		return R.data(cashDepositBankService.selectDepositWay(currency, bankType));
	}

}
