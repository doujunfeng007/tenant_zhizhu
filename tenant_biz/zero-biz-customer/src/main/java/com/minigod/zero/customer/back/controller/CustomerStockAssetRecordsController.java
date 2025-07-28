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
import com.minigod.zero.customer.entity.CustomerStockAssetRecordsEntity;
import com.minigod.zero.customer.vo.CustomerStockAssetRecordsVO;
import com.minigod.zero.customer.wrapper.CustomerStockAssetRecordsWrapper;
import com.minigod.zero.customer.back.service.ICustomerStockAssetRecordsService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 客户资产流水汇总表 控制器
 *
 * @author ken
 * @since 2024-04-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("stockAssetRecords")
@Api(value = "客户资产流水汇总表", tags = "客户资产流水汇总表接口")
public class CustomerStockAssetRecordsController extends ZeroController {

	private final ICustomerStockAssetRecordsService stockAssetRecordsService;

	/**
	 * 客户资产流水汇总表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入stockAssetRecords")
	public R<CustomerStockAssetRecordsVO> detail(CustomerStockAssetRecordsEntity stockAssetRecords) {
		CustomerStockAssetRecordsEntity detail = stockAssetRecordsService.getOne(Condition.getQueryWrapper(stockAssetRecords));
		return R.data(CustomerStockAssetRecordsWrapper.build().entityVO(detail));
	}
	/**
	 * 客户资产流水汇总表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入stockAssetRecords")
	public R<IPage<CustomerStockAssetRecordsVO>> list(CustomerStockAssetRecordsEntity stockAssetRecords, Query query) {
		IPage<CustomerStockAssetRecordsEntity> pages = stockAssetRecordsService.page(Condition.getPage(query), Condition.getQueryWrapper(stockAssetRecords));
		return R.data(CustomerStockAssetRecordsWrapper.build().pageVO(pages));
	}

	/**
	 * 客户资产流水汇总表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入stockAssetRecords")
	public R<IPage<CustomerStockAssetRecordsVO>> page(CustomerStockAssetRecordsVO stockAssetRecords, Query query) {
		IPage<CustomerStockAssetRecordsVO> pages = stockAssetRecordsService.selectCustomerStockAssetRecordsPage(Condition.getPage(query), stockAssetRecords);
		return R.data(pages);
	}

	/**
	 * 客户资产流水汇总表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入stockAssetRecords")
	public R save(@Valid @RequestBody CustomerStockAssetRecordsEntity stockAssetRecords) {
		return R.status(stockAssetRecordsService.save(stockAssetRecords));
	}

	/**
	 * 客户资产流水汇总表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入stockAssetRecords")
	public R update(@Valid @RequestBody CustomerStockAssetRecordsEntity stockAssetRecords) {
		return R.status(stockAssetRecordsService.updateById(stockAssetRecords));
	}

	/**
	 * 客户资产流水汇总表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入stockAssetRecords")
	public R submit(@Valid @RequestBody CustomerStockAssetRecordsEntity stockAssetRecords) {
		return R.status(stockAssetRecordsService.saveOrUpdate(stockAssetRecords));
	}

	/**
	 * 客户资产流水汇总表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(stockAssetRecordsService.deleteLogic(Func.toLongList(ids)));
	}


}
