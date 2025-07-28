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
import com.minigod.zero.customer.entity.CustomerStockHoldingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerStockHoldingRecordsVO;
import com.minigod.zero.customer.wrapper.CustomerStockHoldingRecordsWrapper;
import com.minigod.zero.customer.back.service.ICustomerStockHoldingRecordsService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 客户股票持仓表 控制器
 *
 * @author ken
 * @since 2024-04-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("stockHoldingRecords")
@Api(value = "客户股票持仓表", tags = "客户股票持仓表接口")
public class CustomerStockHoldingRecordsController extends ZeroController {

	private final ICustomerStockHoldingRecordsService stockHoldingRecordsService;

	/**
	 * 客户股票持仓表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入stockHoldingRecords")
	public R<CustomerStockHoldingRecordsVO> detail(CustomerStockHoldingRecordsEntity stockHoldingRecords) {
		CustomerStockHoldingRecordsEntity detail = stockHoldingRecordsService.getOne(Condition.getQueryWrapper(stockHoldingRecords));
		return R.data(CustomerStockHoldingRecordsWrapper.build().entityVO(detail));
	}
	/**
	 * 客户股票持仓表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入stockHoldingRecords")
	public R<IPage<CustomerStockHoldingRecordsVO>> list(CustomerStockHoldingRecordsEntity stockHoldingRecords, Query query) {
		IPage<CustomerStockHoldingRecordsEntity> pages = stockHoldingRecordsService.page(Condition.getPage(query), Condition.getQueryWrapper(stockHoldingRecords));
		return R.data(CustomerStockHoldingRecordsWrapper.build().pageVO(pages));
	}

	/**
	 * 客户股票持仓表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入stockHoldingRecords")
	public R<IPage<CustomerStockHoldingRecordsVO>> page(CustomerStockHoldingRecordsVO stockHoldingRecords, Query query) {
		IPage<CustomerStockHoldingRecordsVO> pages = stockHoldingRecordsService.selectCustomerStockHoldingRecordsPage(Condition.getPage(query), stockHoldingRecords);
		return R.data(pages);
	}

	/**
	 * 客户股票持仓表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入stockHoldingRecords")
	public R save(@Valid @RequestBody CustomerStockHoldingRecordsEntity stockHoldingRecords) {
		return R.status(stockHoldingRecordsService.save(stockHoldingRecords));
	}

	/**
	 * 客户股票持仓表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入stockHoldingRecords")
	public R update(@Valid @RequestBody CustomerStockHoldingRecordsEntity stockHoldingRecords) {
		return R.status(stockHoldingRecordsService.updateById(stockHoldingRecords));
	}

	/**
	 * 客户股票持仓表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入stockHoldingRecords")
	public R submit(@Valid @RequestBody CustomerStockHoldingRecordsEntity stockHoldingRecords) {
		return R.status(stockHoldingRecordsService.saveOrUpdate(stockHoldingRecords));
	}

	/**
	 * 客户股票持仓表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(stockHoldingRecordsService.deleteLogic(Func.toLongList(ids)));
	}


}
