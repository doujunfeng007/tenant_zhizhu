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
import com.minigod.zero.customer.entity.CustomerDeviceInfoEntity;
import com.minigod.zero.customer.vo.CustomerDeviceInfoVO;
import com.minigod.zero.customer.wrapper.CustomerDeviceInfoWrapper;
import com.minigod.zero.customer.back.service.ICustomerDeviceInfoService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 客户设备信息 控制器
 *
 * @author ken
 * @since 2024-04-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("customerdeviceInfo")
@Api(value = "客户设备信息", tags = "客户设备信息接口")
public class CustomerDeviceInfoController extends ZeroController {

	private final ICustomerDeviceInfoService customerdeviceInfoService;

	/**
	 * 客户设备信息 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入customerdeviceInfo")
	public R<CustomerDeviceInfoVO> detail(CustomerDeviceInfoEntity customerdeviceInfo) {
		CustomerDeviceInfoEntity detail = customerdeviceInfoService.getOne(Condition.getQueryWrapper(customerdeviceInfo));
		return R.data(CustomerDeviceInfoWrapper.build().entityVO(detail));
	}
	/**
	 * 客户设备信息 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入customerdeviceInfo")
	public R<IPage<CustomerDeviceInfoVO>> list(CustomerDeviceInfoEntity customerdeviceInfo, Query query) {
		IPage<CustomerDeviceInfoEntity> pages = customerdeviceInfoService.page(Condition.getPage(query), Condition.getQueryWrapper(customerdeviceInfo));
		return R.data(CustomerDeviceInfoWrapper.build().pageVO(pages));
	}

	/**
	 * 客户设备信息 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入customerdeviceInfo")
	public R<IPage<CustomerDeviceInfoVO>> page(CustomerDeviceInfoVO customerdeviceInfo, Query query) {
		IPage<CustomerDeviceInfoVO> pages = customerdeviceInfoService.selectCustomerDeviceInfoPage(Condition.getPage(query), customerdeviceInfo);
		return R.data(pages);
	}

	/**
	 * 客户设备信息 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入customerdeviceInfo")
	public R save(@Valid @RequestBody CustomerDeviceInfoEntity customerdeviceInfo) {
		return R.status(customerdeviceInfoService.save(customerdeviceInfo));
	}

	/**
	 * 客户设备信息 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入customerdeviceInfo")
	public R update(@Valid @RequestBody CustomerDeviceInfoEntity customerdeviceInfo) {
		return R.status(customerdeviceInfoService.updateById(customerdeviceInfo));
	}

	/**
	 * 客户设备信息 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入customerdeviceInfo")
	public R submit(@Valid @RequestBody CustomerDeviceInfoEntity customerdeviceInfo) {
		return R.status(customerdeviceInfoService.saveOrUpdate(customerdeviceInfo));
	}

	/**
	 * 客户设备信息 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(customerdeviceInfoService.deleteLogic(Func.toLongList(ids)));
	}


}
