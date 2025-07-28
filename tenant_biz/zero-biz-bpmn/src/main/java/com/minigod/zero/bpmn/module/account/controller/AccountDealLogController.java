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

import com.minigod.zero.bpmn.module.account.entity.AccountDealLogEntity;
import com.minigod.zero.bpmn.module.account.service.IAccountDealLogService;


/**
 *  控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX+"/account/accountDealLog")
@Api(value = "", tags = "")
public class AccountDealLogController extends ZeroController {

	private final IAccountDealLogService account_deal_logService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入account_deal_log")
	public R<AccountDealLogEntity> detail(AccountDealLogEntity account_deal_log) {
		AccountDealLogEntity detail = account_deal_logService.getOne(Condition.getQueryWrapper(account_deal_log));
		return R.data(detail);
	}

	/**
	 * 分页 代码自定义代号
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入account_deal_log")
	public R<IPage<AccountDealLogEntity>> list(AccountDealLogEntity account_deal_log, Query query) {
		IPage<AccountDealLogEntity> pages = account_deal_logService.page(Condition.getPage(query), Condition.getQueryWrapper(account_deal_log));
		return R.data(pages);
	}

	/**
	 * 新增 代码自定义代号
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入account_deal_log")
	public R save(@Valid @RequestBody AccountDealLogEntity account_deal_log) {
		return R.status(account_deal_logService.save(account_deal_log));
	}

	/**
	 * 修改 代码自定义代号
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入account_deal_log")
	public R update(@Valid @RequestBody AccountDealLogEntity account_deal_log) {
		return R.status(account_deal_logService.updateById(account_deal_log));
	}

	/**
	 * 新增或修改 代码自定义代号
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入account_deal_log")
	public R submit(@Valid @RequestBody AccountDealLogEntity account_deal_log) {
		return R.status(account_deal_logService.saveOrUpdate(account_deal_log));
	}


	/**
	 * 删除 代码自定义代号
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(account_deal_logService.deleteLogic(Func.toLongList(ids)));
	}

}
