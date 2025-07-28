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
import com.minigod.zero.customer.entity.CustomerRealnameVerifyEntity;
import com.minigod.zero.customer.vo.CustomerRealnameVerifyVO;
import com.minigod.zero.customer.wrapper.CustomerRealnameVerifyWrapper;
import com.minigod.zero.customer.back.service.ICustomerRealnameVerifyService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 客户实名认证表 控制器
 *
 * @author ken
 * @since 2024-04-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("realnameVerify")
@Api(value = "客户实名认证表", tags = "客户实名认证表接口")
public class CustomerRealnameVerifyController extends ZeroController {

	private final ICustomerRealnameVerifyService realnameVerifyService;

	/**
	 * 客户实名认证表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入realnameVerify")
	public R<CustomerRealnameVerifyVO> detail(CustomerRealnameVerifyEntity realnameVerify) {
		CustomerRealnameVerifyEntity detail = realnameVerifyService.getOne(Condition.getQueryWrapper(realnameVerify));
		return R.data(CustomerRealnameVerifyWrapper.build().entityVO(detail));
	}
	/**
	 * 客户实名认证表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入realnameVerify")
	public R<IPage<CustomerRealnameVerifyVO>> list(CustomerRealnameVerifyEntity realnameVerify, Query query) {
		IPage<CustomerRealnameVerifyEntity> pages = realnameVerifyService.page(Condition.getPage(query), Condition.getQueryWrapper(realnameVerify));
		return R.data(CustomerRealnameVerifyWrapper.build().pageVO(pages));
	}

	/**
	 * 客户实名认证表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入realnameVerify")
	public R<IPage<CustomerRealnameVerifyVO>> page(CustomerRealnameVerifyVO realnameVerify, Query query) {
		IPage<CustomerRealnameVerifyVO> pages = realnameVerifyService.selectCustomerRealnameVerifyPage(Condition.getPage(query), realnameVerify);
		return R.data(pages);
	}

	/**
	 * 客户实名认证表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入realnameVerify")
	public R save(@Valid @RequestBody CustomerRealnameVerifyEntity realnameVerify) {
		return R.status(realnameVerifyService.save(realnameVerify));
	}

	/**
	 * 客户实名认证表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入realnameVerify")
	public R update(@Valid @RequestBody CustomerRealnameVerifyEntity realnameVerify) {
		return R.status(realnameVerifyService.updateById(realnameVerify));
	}

	/**
	 * 客户实名认证表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入realnameVerify")
	public R submit(@Valid @RequestBody CustomerRealnameVerifyEntity realnameVerify) {
		return R.status(realnameVerifyService.saveOrUpdate(realnameVerify));
	}

	/**
	 * 客户实名认证表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(realnameVerifyService.deleteLogic(Func.toLongList(ids)));
	}


}
