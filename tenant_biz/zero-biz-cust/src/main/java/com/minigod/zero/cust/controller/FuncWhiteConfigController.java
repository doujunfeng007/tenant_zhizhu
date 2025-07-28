package com.minigod.zero.cust.controller;

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
import com.minigod.zero.cust.entity.FuncWhiteConfigEntity;
import com.minigod.zero.cust.vo.FuncWhiteConfigVO;
import com.minigod.zero.cust.service.IFuncWhiteConfigService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 功能白名单启用配置 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/funcWhiteConfig")
@Api(value = "功能白名单启用配置", tags = "功能白名单启用配置接口")
public class FuncWhiteConfigController extends ZeroController {

	private final IFuncWhiteConfigService funcWhiteConfigService;

	/**
	 * 功能白名单启用配置 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入funcWhiteConfig")
	public R<FuncWhiteConfigEntity> detail(FuncWhiteConfigEntity funcWhiteConfig) {
		FuncWhiteConfigEntity detail = funcWhiteConfigService.getOne(Condition.getQueryWrapper(funcWhiteConfig));
		return R.data(detail);
	}
	/**
	 * 功能白名单启用配置 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入funcWhiteConfig")
	public R<IPage<FuncWhiteConfigEntity>> list(FuncWhiteConfigEntity funcWhiteConfig, Query query) {
		IPage<FuncWhiteConfigEntity> pages = funcWhiteConfigService.page(Condition.getPage(query), Condition.getQueryWrapper(funcWhiteConfig));
		return R.data(pages);
	}

	/**
	 * 功能白名单启用配置 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入funcWhiteConfig")
	public R<IPage<FuncWhiteConfigVO>> page(FuncWhiteConfigVO funcWhiteConfig, Query query) {
		IPage<FuncWhiteConfigVO> pages = funcWhiteConfigService.selectFuncWhiteConfigPage(Condition.getPage(query), funcWhiteConfig);
		return R.data(pages);
	}

	/**
	 * 功能白名单启用配置 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入funcWhiteConfig")
	public R<Object> save(@Valid @RequestBody FuncWhiteConfigEntity funcWhiteConfig) {
		return R.status(funcWhiteConfigService.save(funcWhiteConfig));
	}

	/**
	 * 功能白名单启用配置 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入funcWhiteConfig")
	public R<Object> update(@Valid @RequestBody FuncWhiteConfigEntity funcWhiteConfig) {
		return R.status(funcWhiteConfigService.updateById(funcWhiteConfig));
	}

	/**
	 * 功能白名单启用配置 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入funcWhiteConfig")
	public R<Object> submit(@Valid @RequestBody FuncWhiteConfigEntity funcWhiteConfig) {
		return R.status(funcWhiteConfigService.saveOrUpdate(funcWhiteConfig));
	}

	/**
	 * 功能白名单启用配置 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(funcWhiteConfigService.removeByIds(Func.toLongList(ids)));
	}


}
