package com.minigod.zero.bpmn.module.account.controller;

import com.minigod.zero.core.launch.constant.AppConstant;
import io.swagger.annotations.Api;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.minigod.zero.core.boot.ctrl.ZeroController;


import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.minigod.zero.bpmn.module.account.entity.AccountPropertyTypeEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountPropertyTypeVO;
import com.minigod.zero.bpmn.module.account.service.IAccountPropertyTypeService;

import java.util.List;


/**
 *  控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX+"/account/accountPropertyType")
@Api(value = "", tags = "")
public class AccountPropertyTypeController extends ZeroController {

	private final IAccountPropertyTypeService account_property_typeService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入account_property_type")
	public R<AccountPropertyTypeEntity> detail(AccountPropertyTypeEntity account_property_type) {
		AccountPropertyTypeEntity detail = account_property_typeService.getOne(Condition.getQueryWrapper(account_property_type));
		return R.data(detail);
	}

	/**
	 * 分页 代码自定义代号
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入account_property_type")
	public R<IPage<AccountPropertyTypeEntity>> list(AccountPropertyTypeEntity account_property_type, Query query) {
		IPage<AccountPropertyTypeEntity> pages = account_property_typeService.page(Condition.getPage(query), Condition.getQueryWrapper(account_property_type));
		return R.data(pages);
	}

	@ApiOperation("查询财产种类列表")
	@GetMapping("/list/{applicationId}")
	public R<List<AccountPropertyTypeVO>> list(@ApiParam("流水号")
												   @NotBlank(message = "流水号不能为空")
												   @PathVariable("applicationId") String applicationId) {
		return R.data(account_property_typeService.queryListByApplicationId(applicationId));
	}

	/**
	 * 新增 代码自定义代号
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入account_property_type")
	public R save(@Valid @RequestBody AccountPropertyTypeEntity account_property_type) {
		return R.status(account_property_typeService.save(account_property_type));
	}

	/**
	 * 修改 代码自定义代号
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入account_property_type")
	public R update(@Valid @RequestBody AccountPropertyTypeEntity account_property_type) {
		return R.status(account_property_typeService.updateById(account_property_type));
	}

	/**
	 * 新增或修改 代码自定义代号
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入account_property_type")
	public R submit(@Valid @RequestBody AccountPropertyTypeEntity account_property_type) {
		return R.status(account_property_typeService.saveOrUpdate(account_property_type));
	}


	/**
	 * 删除 代码自定义代号
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(account_property_typeService.deleteLogic(Func.toLongList(ids)));
	}

}
