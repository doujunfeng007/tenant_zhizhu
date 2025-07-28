package com.minigod.zero.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.manage.service.ICashWithdrawalsSupportCurrencyBankService;
import com.minigod.zero.manage.vo.CashWithdrawalsSupportCurrencyBankVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 出金银行、付款方式、币种、付款银行关联表 控制器
 *
 * @author eric
 * @since 2024-10-18 18:03:05
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/cash-withdrawals-support-currency-bank")
@Api(value = "出金银行、付款方式、币种、付款银行关联表", tags = "出金银行、付款方式、币种、付款银行关联表接口")
public class CashWithdrawalsSupportCurrencyBankController extends ZeroController {
	private final ICashWithdrawalsSupportCurrencyBankService iCashWithdrawalsSupportCurrencyBankService;

	/**
	 * 分页获取出金银行、付款方式、币种、付款银行关联信息
	 */
	@GetMapping("/page-list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "出金银行、付款方式、币种、付款银行关联分页")
	public R<IPage<CashWithdrawalsSupportCurrencyBankVO>> list(CashWithdrawalsSupportCurrencyBankVO cashWithdrawalsSupportCurrencyBankVO, Query query) {
		return R.data(iCashWithdrawalsSupportCurrencyBankService.selectWithdrawalsSupportCurrencyBankPage(query, cashWithdrawalsSupportCurrencyBankVO));
	}

	/**
	 * 出金银行卡配置编辑页-选择付款方式-选择币种-勾选/取消勾选 付款银行
	 */
	@PostMapping("/edit")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "勾选/取消勾选 出金银行卡收款方式对应的付款银行")
	public R editSupportCurrencyBank(Long withdrawalsId, Integer supportType, Integer currency, Integer accountType, Long paymentBankId, Long paymentBankDetailId) {
		try {
			return iCashWithdrawalsSupportCurrencyBankService.editCashWithdrawalsSupportCurrencyBank(withdrawalsId, supportType, currency, accountType, paymentBankId, paymentBankDetailId);
		} catch (Exception e) {
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
	}

	/**
	 * 出金银行卡配置编辑页-选择付款方式-选择币种-勾选/取消勾选 默认付款银行
	 */
	@PostMapping("/edit-default")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "勾选/取消勾选 出金银行卡收款方式对应的默认付款银行")
	public R editSupportCurrencyBankDefault(Long withdrawalsId, Integer supportType, Integer currency, Integer accountType, Long paymentBankId, Long paymentBankDetailId) {
		try {
			return iCashWithdrawalsSupportCurrencyBankService.editCashWithdrawalsSupportCurrencyBankDefault(withdrawalsId, supportType, currency, accountType, paymentBankId, paymentBankDetailId);
		} catch (Exception e) {
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
	}
}
