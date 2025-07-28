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
import com.minigod.zero.customer.entity.CustomerCardInfoEntity;
import com.minigod.zero.customer.vo.CustomerCardInfoVO;
import com.minigod.zero.customer.wrapper.CustomerCardInfoWrapper;
import com.minigod.zero.customer.back.service.ICustomerCardInfoService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 客户银行卡记录 控制器
 *
 * @author ken
 * @since 2024-04-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("customercardInfo")
@Api(value = "客户银行卡记录", tags = "客户银行卡记录接口")
public class CustomerCardInfoController extends ZeroController {

	private final ICustomerCardInfoService customercardInfoService;

	/**
	 * 客户银行卡记录 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入customercardInfo")
	public R<CustomerCardInfoVO> detail(CustomerCardInfoEntity customercardInfo) {
		CustomerCardInfoEntity detail = customercardInfoService.getOne(Condition.getQueryWrapper(customercardInfo));
		return R.data(CustomerCardInfoWrapper.build().entityVO(detail));
	}
	/**
	 * 客户银行卡记录 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入customercardInfo")
	public R<IPage<CustomerCardInfoVO>> list(CustomerCardInfoEntity customercardInfo, Query query) {
		IPage<CustomerCardInfoEntity> pages = customercardInfoService.page(Condition.getPage(query), Condition.getQueryWrapper(customercardInfo));
		return R.data(CustomerCardInfoWrapper.build().pageVO(pages));
	}

	/**
	 * 客户银行卡记录 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入customercardInfo")
	public R<IPage<CustomerCardInfoVO>> page(CustomerCardInfoVO customercardInfo, Query query) {
		IPage<CustomerCardInfoVO> pages = customercardInfoService.selectCustomerCardInfoPage(Condition.getPage(query), customercardInfo);
		return R.data(pages);
	}

	/**
	 * 客户银行卡记录 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入customercardInfo")
	public R save(@Valid @RequestBody CustomerCardInfoEntity customercardInfo) {
		return R.status(customercardInfoService.save(customercardInfo));
	}

	/**
	 * 客户银行卡记录 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入customercardInfo")
	public R update(@Valid @RequestBody CustomerCardInfoEntity customercardInfo) {
		return R.status(customercardInfoService.updateById(customercardInfo));
	}

	/**
	 * 客户银行卡记录 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入customercardInfo")
	public R submit(@Valid @RequestBody CustomerCardInfoEntity customercardInfo) {
		return R.status(customercardInfoService.saveOrUpdate(customercardInfo));
	}

	/**
	 * 客户银行卡记录 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(customercardInfoService.deleteLogic(Func.toLongList(ids)));
	}


}
