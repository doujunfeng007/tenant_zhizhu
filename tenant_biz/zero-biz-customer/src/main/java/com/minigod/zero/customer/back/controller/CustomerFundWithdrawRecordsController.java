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
import com.minigod.zero.customer.entity.CustomerFundWithdrawRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundWithdrawRecordsVO;
import com.minigod.zero.customer.wrapper.CustomerFundWithdrawRecordsWrapper;
import com.minigod.zero.customer.back.service.ICustomerFundWithdrawRecordsService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 客户出金申请信息表 控制器
 *
 * @author ken
 * @since 2024-04-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("fundWithdrawRecords")
@Api(value = "客户出金申请信息表", tags = "客户出金申请信息表接口")
public class CustomerFundWithdrawRecordsController extends ZeroController {

	private final ICustomerFundWithdrawRecordsService fundWithdrawRecordsService;

	/**
	 * 客户出金申请信息表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入fundWithdrawRecords")
	public R<CustomerFundWithdrawRecordsVO> detail(CustomerFundWithdrawRecordsEntity fundWithdrawRecords) {
		CustomerFundWithdrawRecordsEntity detail = fundWithdrawRecordsService.getOne(Condition.getQueryWrapper(fundWithdrawRecords));
		return R.data(CustomerFundWithdrawRecordsWrapper.build().entityVO(detail));
	}
	/**
	 * 客户出金申请信息表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入fundWithdrawRecords")
	public R<IPage<CustomerFundWithdrawRecordsVO>> list(CustomerFundWithdrawRecordsEntity fundWithdrawRecords, Query query) {
		IPage<CustomerFundWithdrawRecordsEntity> pages = fundWithdrawRecordsService.page(Condition.getPage(query), Condition.getQueryWrapper(fundWithdrawRecords));
		return R.data(CustomerFundWithdrawRecordsWrapper.build().pageVO(pages));
	}

	/**
	 * 客户出金申请信息表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入fundWithdrawRecords")
	public R<IPage<CustomerFundWithdrawRecordsVO>> page(CustomerFundWithdrawRecordsVO fundWithdrawRecords, Query query) {
		IPage<CustomerFundWithdrawRecordsVO> pages = fundWithdrawRecordsService.selectCustomerFundWithdrawRecordsPage(Condition.getPage(query), fundWithdrawRecords);
		return R.data(pages);
	}

	/**
	 * 客户出金申请信息表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入fundWithdrawRecords")
	public R save(@Valid @RequestBody CustomerFundWithdrawRecordsEntity fundWithdrawRecords) {
		return R.status(fundWithdrawRecordsService.save(fundWithdrawRecords));
	}

	/**
	 * 客户出金申请信息表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入fundWithdrawRecords")
	public R update(@Valid @RequestBody CustomerFundWithdrawRecordsEntity fundWithdrawRecords) {
		return R.status(fundWithdrawRecordsService.updateById(fundWithdrawRecords));
	}

	/**
	 * 客户出金申请信息表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入fundWithdrawRecords")
	public R submit(@Valid @RequestBody CustomerFundWithdrawRecordsEntity fundWithdrawRecords) {
		return R.status(fundWithdrawRecordsService.saveOrUpdate(fundWithdrawRecords));
	}

	/**
	 * 客户出金申请信息表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(fundWithdrawRecordsService.deleteLogic(Func.toLongList(ids)));
	}


}
