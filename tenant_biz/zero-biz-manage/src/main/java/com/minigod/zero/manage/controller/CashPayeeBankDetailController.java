package com.minigod.zero.manage.controller;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.manage.entity.CashPayeeBankDetailEntity;
import com.minigod.zero.manage.enums.SupportTypeEnum;
import com.minigod.zero.manage.service.ICashPayeeBankDetailService;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 收款、付款账户信息 控制器
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/cashPayeeBankDetail")
@Api(value = "收款、付款银行账户信息", tags = "收款、付款银行账户信息接口")
public class CashPayeeBankDetailController extends ZeroController {

	private final ICashPayeeBankDetailService cashPayeeBankDetailService;

	/**
	 * 收款、付款银行账户信息 详情（收款信息编辑页-选择转账方式-选择币种）
	 *
	 * @param id          入金收款银行主表id
	 * @param supportType 转账方式
	 * @param currency    币种
	 * @return
	 */
	@GetMapping(value = "/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情")
	public R detail(Long id, String supportType, Integer currency) {
		List<CashPayeeBankDetailEntity> list = new LambdaQueryChainWrapper<>(cashPayeeBankDetailService.getBaseMapper())
			.eq(CashPayeeBankDetailEntity::getPayeeBankId, id)
			.eq(CashPayeeBankDetailEntity::getSupportType, supportType)
			.eq(CashPayeeBankDetailEntity::getCurrency, currency)
			.list();
		return R.data(list);
	}

	/**
	 * 收款、付款银行账户信息 添加修改（收款信息编辑页-选择转账方式-选择币种-修改按钮、添加收款信息按钮）
	 */
	@PostMapping(value = "/submit")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "添加修改")
	public R update(@RequestBody CashPayeeBankDetailEntity cashPayeeBankDetail) {
		if (SupportTypeEnum.FPS.getType().equals(cashPayeeBankDetail.getSupportType())) {
			if (StringUtils.isBlank(cashPayeeBankDetail.getFpsId())) {
				return R.fail("请填写FPS识别号");
			}
			if (StringUtils.isBlank(cashPayeeBankDetail.getBankAccount())) {
				return R.fail("请填写收款账号");
			}
		}
		if (SupportTypeEnum.WYZZ.getType().equals(cashPayeeBankDetail.getSupportType())) {
			if (cashPayeeBankDetail.getAccountType() == 1) {
				if (StringUtils.isBlank(cashPayeeBankDetail.getBankAccount())) {
					return R.fail("请填写收款账号");
				}
			}
		}
		if (cashPayeeBankDetail.getAccountType() == null) {
			cashPayeeBankDetail.setAccountType(1);
		}
		cashPayeeBankDetail.setUpdateTime(new Date());
		cashPayeeBankDetail.setCreateTime(new Date());
		if (cashPayeeBankDetail.getId() != null) {
			return cashPayeeBankDetailService.updateCashPayeeBankDetail(cashPayeeBankDetail) ? R.success() : R.fail();
		} else {
			return cashPayeeBankDetailService.saveCashPayeeBankDetail(cashPayeeBankDetail) ? R.success() : R.fail();
		}
	}

	/**
	 * 收款、付款银行账户信息 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(cashPayeeBankDetailService.deleteCashPayeeBankDetail(Func.toLongList(ids)));
	}
}
