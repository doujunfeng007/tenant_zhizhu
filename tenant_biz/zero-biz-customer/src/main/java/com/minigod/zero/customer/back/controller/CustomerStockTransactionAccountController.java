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
import com.minigod.zero.customer.entity.CustomerStockTradingAccountEntity;
import com.minigod.zero.customer.vo.CustomerStockTradingAccountVO;
import com.minigod.zero.customer.wrapper.CustomerStockTransactionAccountWrapper;
import com.minigod.zero.customer.back.service.ICustomerStockTransactionAccountService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 交易账户信息表 控制器
 *
 * @author ken
 * @since 2024-04-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("stockTransactionAccount")
@Api(value = "交易账户信息表", tags = "交易账户信息表接口")
public class CustomerStockTransactionAccountController extends ZeroController {

	private final ICustomerStockTransactionAccountService stockTransactionAccountService;

	/**
	 * 交易账户信息表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入stockTransactionAccount")
	public R<CustomerStockTradingAccountVO> detail(CustomerStockTradingAccountEntity stockTransactionAccount) {
		CustomerStockTradingAccountEntity detail = stockTransactionAccountService.getOne(Condition.getQueryWrapper(stockTransactionAccount));
		return R.data(CustomerStockTransactionAccountWrapper.build().entityVO(detail));
	}
	/**
	 * 交易账户信息表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入stockTransactionAccount")
	public R<IPage<CustomerStockTradingAccountVO>> list(CustomerStockTradingAccountEntity stockTransactionAccount, Query query) {
		IPage<CustomerStockTradingAccountEntity> pages = stockTransactionAccountService.page(Condition.getPage(query), Condition.getQueryWrapper(stockTransactionAccount));
		return R.data(CustomerStockTransactionAccountWrapper.build().pageVO(pages));
	}

	/**
	 * 交易账户信息表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入stockTransactionAccount")
	public R<IPage<CustomerStockTradingAccountVO>> page(CustomerStockTradingAccountVO stockTransactionAccount, Query query) {
		IPage<CustomerStockTradingAccountVO> pages = stockTransactionAccountService.selectCustomerStockTransactionAccountPage(Condition.getPage(query), stockTransactionAccount);
		return R.data(pages);
	}

	/**
	 * 交易账户信息表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入stockTransactionAccount")
	public R save(@Valid @RequestBody CustomerStockTradingAccountEntity stockTransactionAccount) {
		return R.status(stockTransactionAccountService.save(stockTransactionAccount));
	}

	/**
	 * 交易账户信息表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入stockTransactionAccount")
	public R update(@Valid @RequestBody CustomerStockTradingAccountEntity stockTransactionAccount) {
		return R.status(stockTransactionAccountService.updateById(stockTransactionAccount));
	}

	/**
	 * 交易账户信息表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入stockTransactionAccount")
	public R submit(@Valid @RequestBody CustomerStockTradingAccountEntity stockTransactionAccount) {
		return R.status(stockTransactionAccountService.saveOrUpdate(stockTransactionAccount));
	}

	/**
	 * 交易账户信息表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(stockTransactionAccountService.deleteLogic(Func.toLongList(ids)));
	}


}
