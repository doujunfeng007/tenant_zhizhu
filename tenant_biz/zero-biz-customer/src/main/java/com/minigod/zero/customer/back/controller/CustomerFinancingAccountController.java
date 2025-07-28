/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.customer.entity.CustomerFinancingAccountEntity;
import com.minigod.zero.customer.vo.CustomerFinancingAccountVO;
import com.minigod.zero.customer.wrapper.CustomerFinancingAccountWrapper;
import com.minigod.zero.customer.back.service.ICustomerFinancingAccountService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 客户理财账户表 控制器
 *
 * @author ken
 * @since 2024-04-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("financingAccount")
@Api(value = "客户理财账户表", tags = "客户理财账户表接口")
public class CustomerFinancingAccountController extends ZeroController {

	private final ICustomerFinancingAccountService financingAccountService;

	/**
	 * 客户理财账户表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入financingAccount")
	public R<CustomerFinancingAccountVO> detail(CustomerFinancingAccountEntity financingAccount) {
		CustomerFinancingAccountEntity detail = financingAccountService.getOne(Condition.getQueryWrapper(financingAccount));
		return R.data(CustomerFinancingAccountWrapper.build().entityVO(detail));
	}
	/**
	 * 客户理财账户表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入financingAccount")
	public R<IPage<CustomerFinancingAccountVO>> list(CustomerFinancingAccountEntity financingAccount, Query query) {
		IPage<CustomerFinancingAccountEntity> pages = financingAccountService.page(Condition.getPage(query), Condition.getQueryWrapper(financingAccount));
		return R.data(CustomerFinancingAccountWrapper.build().pageVO(pages));
	}

	/**
	 * 客户理财账户表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入financingAccount")
	public R<IPage<CustomerFinancingAccountVO>> page(CustomerFinancingAccountVO financingAccount, Query query) {
		IPage<CustomerFinancingAccountVO> pages = financingAccountService.selectCustomerFinancingAccountPage(Condition.getPage(query), financingAccount);
		return R.data(pages);
	}

	/**
	 * 客户理财账户表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入financingAccount")
	public R save(@Valid @RequestBody CustomerFinancingAccountEntity financingAccount) {
		return R.status(financingAccountService.save(financingAccount));
	}

	/**
	 * 客户理财账户表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入financingAccount")
	public R update(@Valid @RequestBody CustomerFinancingAccountEntity financingAccount) {
		return R.status(financingAccountService.updateById(financingAccount));
	}

	/**
	 * 客户理财账户表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入financingAccount")
	public R submit(@Valid @RequestBody CustomerFinancingAccountEntity financingAccount) {
		return R.status(financingAccountService.saveOrUpdate(financingAccount));
	}

	/**
	 * 客户理财账户表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(financingAccountService.deleteLogic(Func.toLongList(ids)));
	}


}
