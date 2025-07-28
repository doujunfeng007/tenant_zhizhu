package com.minigod.zero.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.manage.service.ICashSupportCurrencyBankService;
import com.minigod.zero.manage.vo.CashSupportCurrencyBankVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 入金银行 付款方式 币种 收款银行关联表 控制器
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/cashSupportCurrencyBank")
@Api(value = "入金银行 付款方式 币种 收款银行关联表", tags = "入金银行 付款方式 币种 收款银行关联表接口")
public class CashSupportCurrencyBankController extends ZeroController {

	private final ICashSupportCurrencyBankService cashSupportCurrencyBankService;

	/**
	 * 分页获取入金银行、收款方式、币种、收款银行关联信息
	 */
	@GetMapping("/page-list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "入金银行、收款方式、币种、收款银行关联分页")
	public R<IPage<CashSupportCurrencyBankVO>> list(CashSupportCurrencyBankVO cashSupportCurrencyBankVO, Query query) {
		return R.data(cashSupportCurrencyBankService.selectCashSupportCurrencyBankPage(query, cashSupportCurrencyBankVO));
	}

	/**
	 * 入金银行卡配置编辑页-选择收款方式-选择币种-勾选/取消勾选 收款银行
	 */
	@PostMapping("/edit")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "勾选/取消勾选 入金银行卡收款方式对应的收款银行")
	public R editSupportCurrencyBank(Long depositId, Integer supportType, Integer currency, Integer accountType, Long payeeBankId, Long payeeBankDetailId) {
		try {
			return cashSupportCurrencyBankService.editSupportCurrencyBank(depositId, supportType, currency, accountType, payeeBankId, payeeBankDetailId);
		} catch (Exception e) {
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
	}

	/**
	 * 入金银行卡配置编辑页-选择收款方式-选择币种-勾选/取消勾选 默认收款银行
	 */
	@PostMapping("/editDefault")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "勾选/取消勾选 入金银行卡收款方式对应的默认收款银行")
	public R editSupportCurrencyBankDefault(Long depositId, Integer supportType, Integer currency, Integer accountType, Long payeeBankId, Long payeeBankDetailId) {
		try {
			return cashSupportCurrencyBankService.editSupportCurrencyBankDefault(depositId, supportType, currency, accountType, payeeBankId, payeeBankDetailId);
		} catch (Exception e) {
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
	}

}
