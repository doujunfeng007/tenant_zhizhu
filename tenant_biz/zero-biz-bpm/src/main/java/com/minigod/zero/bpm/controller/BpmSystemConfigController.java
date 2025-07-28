package com.minigod.zero.bpm.controller;

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
import com.minigod.zero.bpm.entity.BpmSystemConfigEntity;
import com.minigod.zero.bpm.vo.BpmSystemConfigVO;
import com.minigod.zero.bpm.service.IBpmSystemConfigService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 系统配置信息表 控制器
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/bpmSystemConfig")
@Api(value = "系统配置信息表", tags = "系统配置信息表接口")
public class BpmSystemConfigController extends ZeroController {

	private final IBpmSystemConfigService bpmSystemConfigService;

	/**
	 * 系统配置信息表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入bpmSystemConfig")
	public R<BpmSystemConfigEntity> detail(BpmSystemConfigEntity bpmSystemConfig) {
		BpmSystemConfigEntity detail = bpmSystemConfigService.getOne(Condition.getQueryWrapper(bpmSystemConfig));
		return R.data(detail);
	}
	/**
	 * 系统配置信息表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入bpmSystemConfig")
	public R<IPage<BpmSystemConfigEntity>> list(BpmSystemConfigEntity bpmSystemConfig, Query query) {
		IPage<BpmSystemConfigEntity> pages = bpmSystemConfigService.page(Condition.getPage(query), Condition.getQueryWrapper(bpmSystemConfig));
		return R.data(pages);
	}

	/**
	 * 系统配置信息表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入bpmSystemConfig")
	public R<IPage<BpmSystemConfigVO>> page(BpmSystemConfigVO bpmSystemConfig, Query query) {
		IPage<BpmSystemConfigVO> pages = bpmSystemConfigService.selectBpmSystemConfigPage(Condition.getPage(query), bpmSystemConfig);
		return R.data(pages);
	}

	/**
	 * 系统配置信息表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入bpmSystemConfig")
	public R<Object> save(@Valid @RequestBody BpmSystemConfigEntity bpmSystemConfig) {
		return R.status(bpmSystemConfigService.save(bpmSystemConfig));
	}

	/**
	 * 系统配置信息表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入bpmSystemConfig")
	public R<Object> update(@Valid @RequestBody BpmSystemConfigEntity bpmSystemConfig) {
		return R.status(bpmSystemConfigService.updateById(bpmSystemConfig));
	}

	/**
	 * 系统配置信息表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入bpmSystemConfig")
	public R<Object> submit(@Valid @RequestBody BpmSystemConfigEntity bpmSystemConfig) {
		return R.status(bpmSystemConfigService.saveOrUpdate(bpmSystemConfig));
	}

	/**
	 * 系统配置信息表 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(bpmSystemConfigService.removeByIds(Func.toLongList(ids)));
	}


}
