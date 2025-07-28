package com.minigod.zero.manage.controller;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.entity.CashDepositBankGuideEntity;
import com.minigod.zero.manage.service.ICashDepositBankGuideService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 入金银行转账方式说明 控制器
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/cashDepositBankGuide")
@Api(value = "入金银行转账方式说明", tags = "入金银行转账方式说明")
public class CashDepositBankGuideController extends ZeroController {

	private final ICashDepositBankGuideService cashDepositBankGuideService;

	/**
	 * 入金银行卡配置编辑页面-获取转账方式说明（原/add_deposit_guide_ui）
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "入金银行卡配置编辑页面-获取转账方式说明")
	public R<CashDepositBankGuideEntity> detail(Long depositId, String supportType) {
		CashDepositBankGuideEntity cashDepositBankGuideEntity = new LambdaQueryChainWrapper<>(cashDepositBankGuideService.getBaseMapper())
			.eq(CashDepositBankGuideEntity::getDepositId, depositId)
			.eq(CashDepositBankGuideEntity::getSupportType, supportType)
			.last("limit 1")
			.one();
		return R.data(cashDepositBankGuideEntity);
	}

	/**
	 * 选择收款方式-转账方式说明-立即提交
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "立即提交")
	public R<CashDepositBankGuideEntity> submit(CashDepositBankGuideEntity cashDepositBankGuide) {
		cashDepositBankGuide.setNotes(cashDepositBankGuide.getNotes() != null ? cashDepositBankGuide.getNotes().trim() : null);
		cashDepositBankGuide.setNotesHant(cashDepositBankGuide.getNotesHant() != null ? cashDepositBankGuide.getNotesHant().trim() : null);
		cashDepositBankGuide.setNotesEn(cashDepositBankGuide.getNotesEn() != null ? cashDepositBankGuide.getNotesEn().trim() : null);
		cashDepositBankGuide.setOperation(cashDepositBankGuide.getOperation() != null ? cashDepositBankGuide.getOperation().trim() : null);
		cashDepositBankGuide.setOperationHant(cashDepositBankGuide.getOperationHant() != null ? cashDepositBankGuide.getOperationHant().trim() : null);
		cashDepositBankGuide.setOperationEn(cashDepositBankGuide.getOperationEn() != null ? cashDepositBankGuide.getOperationEn().trim() : null);
		if (cashDepositBankGuide.getId() == null) {
			cashDepositBankGuideService.getBaseMapper().insert(cashDepositBankGuide);
		} else {
			cashDepositBankGuideService.getBaseMapper().updateById(cashDepositBankGuide);
		}
		return R.data(new LambdaQueryChainWrapper<>(cashDepositBankGuideService.getBaseMapper())
			.eq(CashDepositBankGuideEntity::getDepositId, cashDepositBankGuide.getDepositId())
			.last("limit 1")
			.one());
	}

}
