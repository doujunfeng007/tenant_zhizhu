package com.minigod.zero.manage.controller;

import com.minigod.zero.manage.entity.PublishItemEntity;
import com.minigod.zero.manage.service.IPublishItemService;
import com.minigod.zero.manage.vo.request.PublishItemRequest;
import com.minigod.zero.core.launch.constant.AppConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.minigod.zero.core.tool.api.R;
import org.springframework.web.bind.annotation.*;
import com.minigod.zero.core.boot.ctrl.ZeroController;

import java.util.List;

/**
 * 帮助中心目录配置 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/publishItem")
@Api(value = "帮助中心目录配置", tags = "帮助中心目录配置接口")
public class PublishItemController extends ZeroController {

	@Resource
	private IPublishItemService publishItemService;

	/**
	 * 帮助中心目录配置 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入菜单ID")
	public R<PublishItemEntity> detail(Long id) {
		PublishItemEntity detail = publishItemService.findById(id);
		return R.data(detail);
	}

	/**
	 * 查询指定菜单ID下的子菜单列表
	 */
	@GetMapping("/publishTree")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查询指定菜单ID下的子菜单列表", notes = "传入id")
	public R<List<PublishItemEntity>> publishTree(Long id) {
		return R.data(publishItemService.publishTree(id));
	}

	/**
	 * 帮助中心目录配置 保存/修改
	 */
	@PostMapping("/saveUpdate")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "保存/修改", notes = "传入publishItemRequest")
	public R<Long> saveUpdate(@Valid @RequestBody PublishItemRequest publishItemRequest) {
		return R.data(publishItemService.saveUpdate(publishItemRequest));
	}

	/**
	 * 帮助中心目录配置 更改状态
	 */
	@PostMapping("/updateStatus")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "更改状态", notes = "传入publishItemRequest")
	public R updateStatus(@RequestBody PublishItemRequest publishItemRequest) {
		return publishItemService.updateStatus(publishItemRequest);
	}

	/**
	 * 帮助中心目录配置 逻辑删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestBody List<Long> ids) {
		return R.data(publishItemService.deleteByIds(ids));
	}
}
