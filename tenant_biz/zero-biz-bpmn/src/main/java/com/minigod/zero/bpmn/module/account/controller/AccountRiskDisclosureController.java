package com.minigod.zero.bpmn.module.account.controller;

import com.minigod.zero.core.launch.constant.AppConstant;
import io.swagger.annotations.Api;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;
import com.minigod.zero.core.boot.ctrl.ZeroController;


import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.minigod.zero.bpmn.module.account.entity.AccountRiskDisclosureEntity;
import com.minigod.zero.bpmn.module.account.service.IAccountRiskDisclosureService;


/**
 *  控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX+"/account/account_risk_disclosure")
@Api(value = "", tags = "")
@Deprecated
public class AccountRiskDisclosureController extends ZeroController {

	private final IAccountRiskDisclosureService account_risk_disclosureService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入account_risk_disclosure")
	public R<AccountRiskDisclosureEntity> detail(AccountRiskDisclosureEntity account_risk_disclosure) {
		AccountRiskDisclosureEntity detail = account_risk_disclosureService.getOne(Condition.getQueryWrapper(account_risk_disclosure));
		return R.data(detail);
	}

	/**
	 * 分页 代码自定义代号
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入account_risk_disclosure")
	public R<IPage<AccountRiskDisclosureEntity>> list(AccountRiskDisclosureEntity account_risk_disclosure, Query query) {
		IPage<AccountRiskDisclosureEntity> pages = account_risk_disclosureService.page(Condition.getPage(query), Condition.getQueryWrapper(account_risk_disclosure));
		return R.data(pages);
	}

	/**
	 * 新增 代码自定义代号
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入account_risk_disclosure")
	public R save(@Valid @RequestBody AccountRiskDisclosureEntity account_risk_disclosure) {
		return R.status(account_risk_disclosureService.save(account_risk_disclosure));
	}

	/**
	 * 修改 代码自定义代号
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入account_risk_disclosure")
	public R update(@Valid @RequestBody AccountRiskDisclosureEntity account_risk_disclosure) {
		return R.status(account_risk_disclosureService.updateById(account_risk_disclosure));
	}

	/**
	 * 新增或修改 代码自定义代号
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入account_risk_disclosure")
	public R submit(@Valid @RequestBody AccountRiskDisclosureEntity account_risk_disclosure) {
		return R.status(account_risk_disclosureService.saveOrUpdate(account_risk_disclosure));
	}


	/**
	 * 删除 代码自定义代号
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(account_risk_disclosureService.deleteLogic(Func.toLongList(ids)));
	}

}
