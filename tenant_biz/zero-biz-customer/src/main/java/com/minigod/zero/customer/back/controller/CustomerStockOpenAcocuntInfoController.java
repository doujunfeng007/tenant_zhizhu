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
import com.minigod.zero.customer.entity.CustomerStockOpenAccountInfoEntity;
import com.minigod.zero.customer.vo.CustomerStockOpenAcocuntInfoVO;
import com.minigod.zero.customer.wrapper.CustomerStockOpenAcocuntInfoWrapper;
import com.minigod.zero.customer.back.service.ICustomerStockOpenAcocuntInfoService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 客户股票开户资料 控制器
 *
 * @author ken
 * @since 2024-04-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("stockOpenAcocuntInfo")
@Api(value = "客户股票开户资料", tags = "客户股票开户资料接口")
public class CustomerStockOpenAcocuntInfoController extends ZeroController {

	private final ICustomerStockOpenAcocuntInfoService stockOpenAcocuntInfoService;

	/**
	 * 客户股票开户资料 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入stockOpenAcocuntInfo")
	public R<CustomerStockOpenAcocuntInfoVO> detail(CustomerStockOpenAccountInfoEntity stockOpenAcocuntInfo) {
		CustomerStockOpenAccountInfoEntity detail = stockOpenAcocuntInfoService.getOne(Condition.getQueryWrapper(stockOpenAcocuntInfo));
		return R.data(CustomerStockOpenAcocuntInfoWrapper.build().entityVO(detail));
	}
	/**
	 * 客户股票开户资料 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入stockOpenAcocuntInfo")
	public R<IPage<CustomerStockOpenAcocuntInfoVO>> list(CustomerStockOpenAccountInfoEntity stockOpenAcocuntInfo, Query query) {
		IPage<CustomerStockOpenAccountInfoEntity> pages = stockOpenAcocuntInfoService.page(Condition.getPage(query), Condition.getQueryWrapper(stockOpenAcocuntInfo));
		return R.data(CustomerStockOpenAcocuntInfoWrapper.build().pageVO(pages));
	}

	/**
	 * 客户股票开户资料 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入stockOpenAcocuntInfo")
	public R<IPage<CustomerStockOpenAcocuntInfoVO>> page(CustomerStockOpenAcocuntInfoVO stockOpenAcocuntInfo, Query query) {
		IPage<CustomerStockOpenAcocuntInfoVO> pages = stockOpenAcocuntInfoService.selectCustomerStockOpenAcocuntInfoPage(Condition.getPage(query), stockOpenAcocuntInfo);
		return R.data(pages);
	}

	/**
	 * 客户股票开户资料 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入stockOpenAcocuntInfo")
	public R save(@Valid @RequestBody CustomerStockOpenAccountInfoEntity stockOpenAcocuntInfo) {
		return R.status(stockOpenAcocuntInfoService.save(stockOpenAcocuntInfo));
	}

	/**
	 * 客户股票开户资料 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入stockOpenAcocuntInfo")
	public R update(@Valid @RequestBody CustomerStockOpenAccountInfoEntity stockOpenAcocuntInfo) {
		return R.status(stockOpenAcocuntInfoService.updateById(stockOpenAcocuntInfo));
	}

	/**
	 * 客户股票开户资料 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入stockOpenAcocuntInfo")
	public R submit(@Valid @RequestBody CustomerStockOpenAccountInfoEntity stockOpenAcocuntInfo) {
		return R.status(stockOpenAcocuntInfoService.saveOrUpdate(stockOpenAcocuntInfo));
	}

	/**
	 * 客户股票开户资料 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(stockOpenAcocuntInfoService.deleteLogic(Func.toLongList(ids)));
	}


}
