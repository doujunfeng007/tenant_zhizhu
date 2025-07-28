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
import com.minigod.zero.customer.entity.CustomerFundAssetRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundAssetRecordsVO;
import com.minigod.zero.customer.wrapper.CustomerFundAssetRecordsWrapper;
import com.minigod.zero.customer.back.service.ICustomerFundAssetRecordsService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 客户资产流水汇总表 控制器
 *
 * @author ken
 * @since 2024-04-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("fundAssetRecords")
@Api(value = "客户资产流水汇总表", tags = "客户资产流水汇总表接口")
public class CustomerFundAssetRecordsController extends ZeroController {

	private final ICustomerFundAssetRecordsService fundAssetRecordsService;

	/**
	 * 客户资产流水汇总表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入fundAssetRecords")
	public R<CustomerFundAssetRecordsVO> detail(CustomerFundAssetRecordsEntity fundAssetRecords) {
		CustomerFundAssetRecordsEntity detail = fundAssetRecordsService.getOne(Condition.getQueryWrapper(fundAssetRecords));
		return R.data(CustomerFundAssetRecordsWrapper.build().entityVO(detail));
	}
	/**
	 * 客户资产流水汇总表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入fundAssetRecords")
	public R<IPage<CustomerFundAssetRecordsVO>> list(CustomerFundAssetRecordsEntity fundAssetRecords, Query query) {
		IPage<CustomerFundAssetRecordsEntity> pages = fundAssetRecordsService.page(Condition.getPage(query), Condition.getQueryWrapper(fundAssetRecords));
		return R.data(CustomerFundAssetRecordsWrapper.build().pageVO(pages));
	}

	/**
	 * 客户资产流水汇总表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入fundAssetRecords")
	public R<IPage<CustomerFundAssetRecordsVO>> page(CustomerFundAssetRecordsVO fundAssetRecords, Query query) {
		IPage<CustomerFundAssetRecordsVO> pages = fundAssetRecordsService.selectCustomerFundAssetRecordsPage(Condition.getPage(query), fundAssetRecords);
		return R.data(pages);
	}

	/**
	 * 客户资产流水汇总表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入fundAssetRecords")
	public R save(@Valid @RequestBody CustomerFundAssetRecordsEntity fundAssetRecords) {
		return R.status(fundAssetRecordsService.save(fundAssetRecords));
	}

	/**
	 * 客户资产流水汇总表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入fundAssetRecords")
	public R update(@Valid @RequestBody CustomerFundAssetRecordsEntity fundAssetRecords) {
		return R.status(fundAssetRecordsService.updateById(fundAssetRecords));
	}

	/**
	 * 客户资产流水汇总表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入fundAssetRecords")
	public R submit(@Valid @RequestBody CustomerFundAssetRecordsEntity fundAssetRecords) {
		return R.status(fundAssetRecordsService.saveOrUpdate(fundAssetRecords));
	}

	/**
	 * 客户资产流水汇总表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(fundAssetRecordsService.deleteLogic(Func.toLongList(ids)));
	}


}
