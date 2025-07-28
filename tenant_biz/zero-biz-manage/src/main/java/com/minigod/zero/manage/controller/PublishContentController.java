package com.minigod.zero.manage.controller;

import com.minigod.zero.manage.entity.PublishContentEntity;
import com.minigod.zero.manage.service.IPublishContentService;
import com.minigod.zero.manage.vo.PublishContentVO;
import com.minigod.zero.manage.vo.request.PublishContentRequest;
import com.minigod.zero.core.launch.constant.AppConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.boot.ctrl.ZeroController;

import java.util.List;

/**
 * 帮助中心内容发布信息 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/publishContent")
@Api(value = "帮助中心内容发布信息", tags = "帮助中心内容发布信息接口")
public class PublishContentController extends ZeroController {

	private final IPublishContentService publishContentService;

	/**
	 * 帮助中心内容发布信息 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入ID")
	public R<PublishContentEntity> detail(Long id) {
		return R.data(publishContentService.findById(id));
	}

	/**
	 * 帮助中心内容发布信息 自定义分页查询
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入publishContent")
	public R<IPage<PublishContentVO>> page(PublishContentVO publishContent, Query query) {
		return R.data(publishContentService.selectPublishContentPage(Condition.getPage(query), publishContent));
	}

	/**
	 * 帮助中心内容发布信息 保存/修改
	 */
	@PostMapping("/saveUpdate")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "保存/修改", notes = "传入publishItemRequest")
	public R<Long> saveUpdate(@Valid @RequestBody PublishContentRequest publishItemRequest) {
		return R.data(publishContentService.saveUpdate(publishItemRequest));
	}

	/**
	 * 帮助中心内容发布信息 更改状态
	 */
	@PostMapping("/updateBean")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "更改状态", notes = "传入publishItemRequest")
	public R updateStatus(@RequestBody PublishContentRequest publishItemRequest) {
		return publishContentService.updateBean(publishItemRequest);
	}

	/**
	 * 帮助中心内容发布信息 逻辑删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestBody List<Long> ids) {
		return R.status(publishContentService.removeBatchByIds2(ids));
	}
}
