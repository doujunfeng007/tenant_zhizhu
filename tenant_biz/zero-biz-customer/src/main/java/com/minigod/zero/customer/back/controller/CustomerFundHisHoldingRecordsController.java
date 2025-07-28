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
import com.minigod.zero.customer.entity.CustomerFundHisHoldingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundHisHoldingRecordsVO;
import com.minigod.zero.customer.wrapper.CustomerFundHisHoldingRecordsWrapper;
import com.minigod.zero.customer.back.service.ICustomerFundHisHoldingRecordsService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 客户基金历史持仓表 控制器
 *
 * @author ken
 * @since 2024-04-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("fundHisHoldingRecords")
@Api(value = "客户基金历史持仓表", tags = "客户基金历史持仓表接口")
public class CustomerFundHisHoldingRecordsController extends ZeroController {

	private final ICustomerFundHisHoldingRecordsService fundHisHoldingRecordsService;

	/**
	 * 客户基金历史持仓表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入fundHisHoldingRecords")
	public R<CustomerFundHisHoldingRecordsVO> detail(CustomerFundHisHoldingRecordsEntity fundHisHoldingRecords) {
		CustomerFundHisHoldingRecordsEntity detail = fundHisHoldingRecordsService.getOne(Condition.getQueryWrapper(fundHisHoldingRecords));
		return R.data(CustomerFundHisHoldingRecordsWrapper.build().entityVO(detail));
	}
	/**
	 * 客户基金历史持仓表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入fundHisHoldingRecords")
	public R<IPage<CustomerFundHisHoldingRecordsVO>> list(CustomerFundHisHoldingRecordsEntity fundHisHoldingRecords, Query query) {
		IPage<CustomerFundHisHoldingRecordsEntity> pages = fundHisHoldingRecordsService.page(Condition.getPage(query), Condition.getQueryWrapper(fundHisHoldingRecords));
		return R.data(CustomerFundHisHoldingRecordsWrapper.build().pageVO(pages));
	}

	/**
	 * 客户基金历史持仓表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入fundHisHoldingRecords")
	public R<IPage<CustomerFundHisHoldingRecordsVO>> page(CustomerFundHisHoldingRecordsVO fundHisHoldingRecords, Query query) {
		IPage<CustomerFundHisHoldingRecordsVO> pages = fundHisHoldingRecordsService.selectCustomerFundHisHoldingRecordsPage(Condition.getPage(query), fundHisHoldingRecords);
		return R.data(pages);
	}

	/**
	 * 客户基金历史持仓表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入fundHisHoldingRecords")
	public R save(@Valid @RequestBody CustomerFundHisHoldingRecordsEntity fundHisHoldingRecords) {
		return R.status(fundHisHoldingRecordsService.save(fundHisHoldingRecords));
	}

	/**
	 * 客户基金历史持仓表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入fundHisHoldingRecords")
	public R update(@Valid @RequestBody CustomerFundHisHoldingRecordsEntity fundHisHoldingRecords) {
		return R.status(fundHisHoldingRecordsService.updateById(fundHisHoldingRecords));
	}

	/**
	 * 客户基金历史持仓表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入fundHisHoldingRecords")
	public R submit(@Valid @RequestBody CustomerFundHisHoldingRecordsEntity fundHisHoldingRecords) {
		return R.status(fundHisHoldingRecordsService.saveOrUpdate(fundHisHoldingRecords));
	}

	/**
	 * 客户基金历史持仓表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(fundHisHoldingRecordsService.deleteLogic(Func.toLongList(ids)));
	}


}
