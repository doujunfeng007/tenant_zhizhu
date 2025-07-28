package com.minigod.zero.flow.workflow.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.excel.util.ExcelUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.flow.workflow.domain.bo.WfCategoryBo;
import com.minigod.zero.flow.workflow.domain.vo.WfCategoryVo;
import com.minigod.zero.flow.workflow.service.IWfCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 流程分类Controller
 *
 * @author zsdp
 * @createTime 2022/3/10 00:12
 */
@Api(value = "流程分类控制器", tags = {"流程分类管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/workflow/category")
public class WfCategoryController {

	private final IWfCategoryService flowCategoryService;

	/**
	 * 查询流程分类列表
	 */
	@ApiOperation("查询流程分类列表")
	@GetMapping("/list")
	public R<IPage<WfCategoryVo>> list(WfCategoryBo bo, Query pageQuery) {
		return R.data(flowCategoryService.queryPageList(bo, Condition.getPage(pageQuery)));
	}

	/**
	 * 查询全部的流程分类列表
	 */
	@ApiOperation("查询全部流程分类列表")
	@GetMapping("/listAll")
	public R<List<WfCategoryVo>> listAll(WfCategoryBo bo) {
		return R.data(flowCategoryService.queryList(bo));
	}

	/**
	 * 导出流程分类列表
	 */
	@ApiOperation("导出流程分类列表")
	@PostMapping("/export")
	public void export(@Validated WfCategoryBo bo, HttpServletResponse response) {
		List<WfCategoryVo> list = flowCategoryService.queryList(bo);
		ExcelUtil.export(response, "流程分类", "分类列表", list, WfCategoryVo.class);
	}

	/**
	 * 获取流程分类详细信息
	 */
	@ApiOperation("获取流程分类详细信息")
	@GetMapping("/{categoryId}")
	public R<WfCategoryVo> getInfo(@ApiParam("主键")
								   @NotNull(message = "主键不能为空")
								   @PathVariable("categoryId") Long categoryId) {
		return R.data(flowCategoryService.queryVoById(categoryId));
	}

	/**
	 * 新增流程分类
	 */
	@ApiOperation("新增流程分类")
	@PostMapping()
	public R<Void> add(@RequestBody WfCategoryBo bo) {
		return R.status(flowCategoryService.insert(bo));
	}

	/**
	 * 修改流程分类
	 */
	@ApiOperation("修改流程分类")
	@PutMapping()
	public R edit(@RequestBody WfCategoryBo bo) {
		return R.status(flowCategoryService.update(bo));
	}

	@ApiOperation("删除流程分类")
	@DeleteMapping("/delete/{id}")
	public R delete(@PathVariable("id") Long id) {
		return R.status(flowCategoryService.deleteById(id));
	}

	/**
	 * 删除流程分类
	 */
	@ApiOperation("删除流程分类")
	@DeleteMapping("/{categoryIds}")
	public R remove(@ApiParam("主键串") @NotEmpty(message = "主键不能为空") @PathVariable Long[] categoryIds) {
		return R.status(flowCategoryService.deleteWithValidByIds(Arrays.asList(categoryIds), true));
	}
}
