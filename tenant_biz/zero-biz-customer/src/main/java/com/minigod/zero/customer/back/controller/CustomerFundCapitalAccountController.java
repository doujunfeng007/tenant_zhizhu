/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.minigod.zero.customer.back.service.ICustomerFundCapitalAccountService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 基金账户信息表 控制器
 *
 * @author ken
 * @since 2024-04-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("fundCapitalAccount")
@Api(value = "基金账户信息表", tags = "基金账户信息表接口")
public class CustomerFundCapitalAccountController extends ZeroController {

	private final ICustomerFundCapitalAccountService fundCapitalAccountService;

	/**
	 * 基金账户信息表 详情
	 */
//	@GetMapping("/detail")
//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value = "详情", notes = "传入fundCapitalAccount")
//	public R<CustomerFundCapitalAccountVO> detail(CustomerFundCapitalAccountEntity fundCapitalAccount) {
//		CustomerFundCapitalAccountEntity detail = fundCapitalAccountService.getOne(Condition.getQueryWrapper(fundCapitalAccount));
//		return R.data(CustomerFundCapitalAccountWrapper.build().entityVO(detail));
//	}
//	/**
//	 * 基金账户信息表 分页
//	 */
//	@GetMapping("/list")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "分页", notes = "传入fundCapitalAccount")
//	public R<IPage<CustomerFundCapitalAccountVO>> list(CustomerFundCapitalAccountEntity fundCapitalAccount, Query query) {
//		IPage<CustomerFundCapitalAccountEntity> pages = fundCapitalAccountService.page(Condition.getPage(query), Condition.getQueryWrapper(fundCapitalAccount));
//		return R.data(CustomerFundCapitalAccountWrapper.build().pageVO(pages));
//	}
//
//	/**
//	 * 基金账户信息表 自定义分页
//	 */
//	@GetMapping("/page")
//	@ApiOperationSupport(order = 3)
//	@ApiOperation(value = "分页", notes = "传入fundCapitalAccount")
//	public R<IPage<CustomerFundCapitalAccountVO>> page(CustomerFundCapitalAccountVO fundCapitalAccount, Query query) {
//		IPage<CustomerFundCapitalAccountVO> pages = fundCapitalAccountService.selectCustomerFundCapitalAccountPage(Condition.getPage(query), fundCapitalAccount);
//		return R.data(pages);
//	}
//
//	/**
//	 * 基金账户信息表 新增
//	 */
//	@PostMapping("/save")
//	@ApiOperationSupport(order = 4)
//	@ApiOperation(value = "新增", notes = "传入fundCapitalAccount")
//	public R save(@Valid @RequestBody CustomerFundCapitalAccountEntity fundCapitalAccount) {
//		return R.status(fundCapitalAccountService.save(fundCapitalAccount));
//	}
//
//	/**
//	 * 基金账户信息表 修改
//	 */
//	@PostMapping("/update")
//	@ApiOperationSupport(order = 5)
//	@ApiOperation(value = "修改", notes = "传入fundCapitalAccount")
//	public R update(@Valid @RequestBody CustomerFundCapitalAccountEntity fundCapitalAccount) {
//		return R.status(fundCapitalAccountService.updateById(fundCapitalAccount));
//	}
//
//	/**
//	 * 基金账户信息表 新增或修改
//	 */
//	@PostMapping("/submit")
//	@ApiOperationSupport(order = 6)
//	@ApiOperation(value = "新增或修改", notes = "传入fundCapitalAccount")
//	public R submit(@Valid @RequestBody CustomerFundCapitalAccountEntity fundCapitalAccount) {
//		return R.status(fundCapitalAccountService.saveOrUpdate(fundCapitalAccount));
//	}

	/**
	 * 基金账户信息表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(fundCapitalAccountService.deleteLogic(Func.toLongList(ids)));
	}


}
