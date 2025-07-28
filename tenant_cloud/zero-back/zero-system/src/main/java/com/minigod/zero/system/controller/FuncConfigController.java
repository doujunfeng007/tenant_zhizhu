/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.system.entity.FuncConfig;
import com.minigod.zero.system.service.IFuncConfigService;
import com.minigod.zero.system.vo.FuncConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 功能配置模块 控制器
 *
 * @author ZSDP
 * @since 2024-07-23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/funcConfig")
@Api(value = "功能配置模块", tags = "功能配置模块接口")
public class FuncConfigController extends ZeroController {

	private final IFuncConfigService funcConfigService;

	/**
	 * 功能配置模块 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入funcConfig")
	public R<FuncConfig> detail(FuncConfig funcConfig) {
		FuncConfig detail = funcConfigService.getOne(Condition.getQueryWrapper(funcConfig));
		return R.data(detail);
	}
	/**
	 * 功能配置模块 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入funcConfig")
	public R<IPage<FuncConfig>> list(FuncConfig funcConfig, Query query) {
		IPage<FuncConfig> pages = funcConfigService.page(Condition.getPage(query), Condition.getQueryWrapper(funcConfig));
		return R.data(pages);
	}

	/**
	 * 功能配置模块 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入funcConfig")
	public R<IPage<FuncConfigVO>> page(FuncConfigVO funcConfig, Query query) {
		IPage<FuncConfigVO> pages = funcConfigService.selectFuncConfigPage(Condition.getPage(query), funcConfig);
		return R.data(pages);
	}

	/**
	 * 功能配置模块 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入funcConfig")
	public R save(@Valid @RequestBody FuncConfig funcConfig) {
		return R.status(funcConfigService.save(funcConfig));
	}

	/**
	 * 功能配置模块 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入funcConfig")
	public R update(@Valid @RequestBody FuncConfig funcConfig) {
		return R.status(funcConfigService.updateById(funcConfig));
	}

	/**
	 * 功能配置模块 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入funcConfig")
	public R submit(@Valid @RequestBody FuncConfig funcConfig) {
		return R.status(funcConfigService.saveOrUpdate(funcConfig));
	}

	/**
	 * 功能配置模块 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(funcConfigService.deleteLogic(Func.toLongList(ids)));
	}


}
