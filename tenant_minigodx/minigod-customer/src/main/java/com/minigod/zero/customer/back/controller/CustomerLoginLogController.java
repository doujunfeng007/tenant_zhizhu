/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.controller;

import com.minigod.zero.customer.dto.CustomerLoginLogDTO;
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
import com.minigod.zero.customer.entity.CustomerLoginLogEntity;
import com.minigod.zero.customer.vo.CustomerLoginLogVO;
import com.minigod.zero.customer.wrapper.CustomerLoginLogWrapper;
import com.minigod.zero.customer.back.service.ICustomerLoginLogService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 登录日志表 控制器
 *
 * @author ken
 * @since 2024-04-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("/loginLog")
@Api(value = "登录日志表", tags = "登录日志表接口")
public class CustomerLoginLogController extends ZeroController {

	private final ICustomerLoginLogService loginLogService;

	/**
	 * 查询登录日志详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "登陆日志详情", notes = "根据参数查询详情")
	public R<CustomerLoginLogVO> detail(CustomerLoginLogDTO loginLog) {
		CustomerLoginLogEntity detail = loginLogService.getOne(Condition.getQueryWrapper(loginLog));
		return R.data(CustomerLoginLogWrapper.build().entityVO(detail));
	}

	/**
	 * 分页查询登陆日志表过虑已经删除的记录
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页查询登陆日志", notes = "根据分页及条件过虑查询登录日志")
	public R<IPage<CustomerLoginLogVO>> list(CustomerLoginLogDTO loginLog, Query query) {
		IPage<CustomerLoginLogEntity> pages = loginLogService.page(Condition.getPage(query), Condition.getQueryWrapper(loginLog));
		return R.data(CustomerLoginLogWrapper.build().pageVO(pages));
	}

	/**
	 * 分页查询登录日志表全部记录
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页查询登陆日志", notes = "根据分页及条件过虑查询登录日志")
	public R<IPage<CustomerLoginLogVO>> allLog(CustomerLoginLogDTO loginLog, Query query) {
		IPage<CustomerLoginLogVO> pages = loginLogService.selectCustomerLoginLogPage(Condition.getPage(query), loginLog);
		return R.data(pages);
	}

	/**
	 * 游客登录日志查询
	 */
	@GetMapping("/visitor-log")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "游客登录日志查询", notes = "根据分页及条件过虑查询游客登录日志")
	public R<IPage<CustomerLoginLogVO>> visitorLog(CustomerLoginLogDTO loginLog, Query query) {
		IPage<CustomerLoginLogVO> pages = loginLogService.selectVisitorLoginLogPage(Condition.getPage(query), loginLog);
		return R.data(pages);
	}

	/**
	 * 开户用户登录日志查询
	 */
	@GetMapping("/account-user-log")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "开户用户登录日志查询", notes = "根据分页及条件过虑查询开户用户登录日志")
	public R<IPage<CustomerLoginLogVO>> accountUserLog(CustomerLoginLogDTO loginLog, Query query) {
		IPage<CustomerLoginLogVO> pages = loginLogService.selectAccountUserLoginLogPage(Condition.getPage(query), loginLog);
		return R.data(pages);
	}

	/**
	 * 新增登录日志
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增登陆日志", notes = "持久化登录日志信息")
	public R save(@Valid @RequestBody CustomerLoginLogDTO loginLog) {
		return R.status(loginLogService.save(loginLog));
	}

	/**
	 * 修改登录日志表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "修改", notes = "更新登录日志信息")
	public R update(@Valid @RequestBody CustomerLoginLogDTO loginLog) {
		return R.status(loginLogService.updateById(loginLog));
	}

	/**
	 * 新增或修改登录日志
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "新增或修改", notes = "保存或更新登录日志信息")
	public R submit(@Valid @RequestBody CustomerLoginLogDTO loginLog) {
		return R.status(loginLogService.saveOrUpdate(loginLog));
	}

	/**
	 * 删除登录日志
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "逻辑删除", notes = "根根据记录Id删除登录日志信息")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(loginLogService.deleteLogic(Func.toLongList(ids)));
	}
}
