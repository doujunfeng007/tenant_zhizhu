package com.minigod.zero.manage.controller;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.entity.CashDepositBankCurrencyGuideEntity;
import com.minigod.zero.manage.service.ICashDepositBankCurrencyGuideService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 入金银行入金指引信息 控制器
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/cashDepositBankCurrencyGuide")
@Api(value = "入金银行入金指引信息", tags = "入金银行入金指引信息")
public class CashDepositBankCurrencyGuideController extends ZeroController {

	private final ICashDepositBankCurrencyGuideService cashDepositBankCurrencyGuideService;

	/**
	 * 入金银行卡配置编辑页面-获取入金指引（原/add_deposit_currency_guide_ui）
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "入金银行卡配置编辑页面-获取入金指引")
	public R<CashDepositBankCurrencyGuideEntity> detail(Long depositId, Integer currency, String supportType) {
		CashDepositBankCurrencyGuideEntity cashDepositBankCurrencyGuideEntity = new LambdaQueryChainWrapper<>(cashDepositBankCurrencyGuideService.getBaseMapper())
			.eq(CashDepositBankCurrencyGuideEntity::getDepositId, depositId)
			.eq(CashDepositBankCurrencyGuideEntity::getCurrency, currency)
			.eq(CashDepositBankCurrencyGuideEntity::getSupportType, supportType)
			.last("limit 1")
			.one();
		return R.data(cashDepositBankCurrencyGuideEntity);
	}

	/**
	 * 选择收款方式-入金指引-立即提交
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "立即提交")
	public R<CashDepositBankCurrencyGuideEntity> submit(CashDepositBankCurrencyGuideEntity cashDepositBankCurrencyGuide) {
		if (cashDepositBankCurrencyGuide.getId() == null) {
			cashDepositBankCurrencyGuideService.getBaseMapper().insert(cashDepositBankCurrencyGuide);
		} else {
			cashDepositBankCurrencyGuideService.getBaseMapper().updateById(cashDepositBankCurrencyGuide);
		}
		return R.data(new LambdaQueryChainWrapper<>(cashDepositBankCurrencyGuideService.getBaseMapper())
			.eq(CashDepositBankCurrencyGuideEntity::getDepositId, cashDepositBankCurrencyGuide.getDepositId())
			.eq(CashDepositBankCurrencyGuideEntity::getCurrency, cashDepositBankCurrencyGuide.getCurrency())
			.eq(CashDepositBankCurrencyGuideEntity::getSupportType, cashDepositBankCurrencyGuide.getSupportType())
			.last("limit 1")
			.one());
	}
}
