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
import com.minigod.zero.customer.entity.CustomerResetLogEntity;
import com.minigod.zero.customer.vo.CustomerResetLogVO;
import com.minigod.zero.customer.wrapper.CustomerResetLogWrapper;
import com.minigod.zero.customer.back.service.ICustomerResetLogService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 客户密码重置日志 控制器
 *
 * @author ken
 * @since 2024-04-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("resetLog")
@Api(value = "客户密码重置日志", tags = "客户密码重置日志接口")
public class CustomerResetLogController extends ZeroController {

	private final ICustomerResetLogService resetLogService;

	/**
	 * 客户密码重置日志 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入resetLog")
	public R<CustomerResetLogVO> detail(CustomerResetLogEntity resetLog) {
		CustomerResetLogEntity detail = resetLogService.getOne(Condition.getQueryWrapper(resetLog));
		return R.data(CustomerResetLogWrapper.build().entityVO(detail));
	}
	/**
	 * 客户密码重置日志 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入resetLog")
	public R<IPage<CustomerResetLogVO>> list(CustomerResetLogEntity resetLog, Query query) {
		IPage<CustomerResetLogEntity> pages = resetLogService.page(Condition.getPage(query), Condition.getQueryWrapper(resetLog));
		return R.data(CustomerResetLogWrapper.build().pageVO(pages));
	}

	/**
	 * 客户密码重置日志 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入resetLog")
	public R<IPage<CustomerResetLogVO>> page(CustomerResetLogVO resetLog, Query query) {
		IPage<CustomerResetLogVO> pages = resetLogService.selectCustomerResetLogPage(Condition.getPage(query), resetLog);
		return R.data(pages);
	}

	/**
	 * 客户密码重置日志 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入resetLog")
	public R save(@Valid @RequestBody CustomerResetLogEntity resetLog) {
		return R.status(resetLogService.save(resetLog));
	}

	/**
	 * 客户密码重置日志 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入resetLog")
	public R update(@Valid @RequestBody CustomerResetLogEntity resetLog) {
		return R.status(resetLogService.updateById(resetLog));
	}

	/**
	 * 客户密码重置日志 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入resetLog")
	public R submit(@Valid @RequestBody CustomerResetLogEntity resetLog) {
		return R.status(resetLogService.saveOrUpdate(resetLog));
	}

	/**
	 * 客户密码重置日志 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(resetLogService.deleteLogic(Func.toLongList(ids)));
	}


}
