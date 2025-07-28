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
import com.minigod.zero.customer.entity.CustomerStockTradingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerStockTradingRecordsVO;
import com.minigod.zero.customer.wrapper.CustomerStockTransactionRecordsWrapper;
import com.minigod.zero.customer.back.service.ICustomerStockTransactionRecordsService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 客户交易流水汇总表 控制器
 *
 * @author ken
 * @since 2024-04-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("stockTransactionRecords")
@Api(value = "客户交易流水汇总表", tags = "客户交易流水汇总表接口")
public class CustomerStockTransactionRecordsController extends ZeroController {

	private final ICustomerStockTransactionRecordsService stockTransactionRecordsService;

	/**
	 * 客户交易流水汇总表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入stockTransactionRecords")
	public R<CustomerStockTradingRecordsVO> detail(CustomerStockTradingRecordsEntity stockTransactionRecords) {
		CustomerStockTradingRecordsEntity detail = stockTransactionRecordsService.getOne(Condition.getQueryWrapper(stockTransactionRecords));
		return R.data(CustomerStockTransactionRecordsWrapper.build().entityVO(detail));
	}
	/**
	 * 客户交易流水汇总表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入stockTransactionRecords")
	public R<IPage<CustomerStockTradingRecordsVO>> list(CustomerStockTradingRecordsEntity stockTransactionRecords, Query query) {
		IPage<CustomerStockTradingRecordsEntity> pages = stockTransactionRecordsService.page(Condition.getPage(query), Condition.getQueryWrapper(stockTransactionRecords));
		return R.data(CustomerStockTransactionRecordsWrapper.build().pageVO(pages));
	}

	/**
	 * 客户交易流水汇总表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入stockTransactionRecords")
	public R<IPage<CustomerStockTradingRecordsVO>> page(CustomerStockTradingRecordsVO stockTransactionRecords, Query query) {

		IPage<CustomerStockTradingRecordsVO> pages = stockTransactionRecordsService.selectCustomerStockTransactionRecordsPage(Condition.getPage(query), stockTransactionRecords);
		return R.data(pages);
	}

	/**
	 * 客户交易流水汇总表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入stockTransactionRecords")
	public R save(@Valid @RequestBody CustomerStockTradingRecordsEntity stockTransactionRecords) {
		return R.status(stockTransactionRecordsService.save(stockTransactionRecords));
	}

	/**
	 * 客户交易流水汇总表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入stockTransactionRecords")
	public R update(@Valid @RequestBody CustomerStockTradingRecordsEntity stockTransactionRecords) {
		return R.status(stockTransactionRecordsService.updateById(stockTransactionRecords));
	}

	/**
	 * 客户交易流水汇总表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入stockTransactionRecords")
	public R submit(@Valid @RequestBody CustomerStockTradingRecordsEntity stockTransactionRecords) {
		return R.status(stockTransactionRecordsService.saveOrUpdate(stockTransactionRecords));
	}

	/**
	 * 客户交易流水汇总表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(stockTransactionRecordsService.deleteLogic(Func.toLongList(ids)));
	}


}
