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

import com.minigod.zero.bpmn.module.account.entity.AccountTaxationInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountTaxationInfoVO;
import com.minigod.zero.bpmn.module.account.service.IAccountTaxationInfoService;

import java.util.List;


/**
 *  控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX+"/account/accountTaxationInfo")
@Api(value = "", tags = "")
public class AccountTaxationInfoController extends ZeroController {

	private final IAccountTaxationInfoService account_taxation_infoService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入account_taxation_info")
	public R<AccountTaxationInfoEntity> detail(AccountTaxationInfoEntity account_taxation_info) {
		AccountTaxationInfoEntity detail = account_taxation_infoService.getOne(Condition.getQueryWrapper(account_taxation_info));
		return R.data(detail);
	}

	/**
	 * 分页 代码自定义代号
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入account_taxation_info")
	public R<IPage<AccountTaxationInfoEntity>> list(AccountTaxationInfoEntity account_taxation_info, Query query) {
		IPage<AccountTaxationInfoEntity> pages = account_taxation_infoService.page(Condition.getPage(query), Condition.getQueryWrapper(account_taxation_info));
		return R.data(pages);
	}

	/**
	 * 新增 代码自定义代号
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入account_taxation_info")
	public R save(@Valid @RequestBody AccountTaxationInfoEntity account_taxation_info) {
		return R.status(account_taxation_infoService.save(account_taxation_info));
	}

	/**
	 * 修改 代码自定义代号
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入account_taxation_info")
	public R update(@Valid @RequestBody AccountTaxationInfoEntity account_taxation_info) {
		return R.status(account_taxation_infoService.updateById(account_taxation_info));
	}

	/**
	 * 新增或修改 代码自定义代号
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入account_taxation_info")
	public R submit(@Valid @RequestBody AccountTaxationInfoEntity account_taxation_info) {
		return R.status(account_taxation_infoService.saveOrUpdate(account_taxation_info));
	}


	/**
	 * 删除 代码自定义代号
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(account_taxation_infoService.deleteLogic(Func.toLongList(ids)));
	}
	@ApiOperation("查询税务信息列表")
	@GetMapping("/list/{applicationId}")
	public R<List<AccountTaxationInfoVO>> list(@ApiParam("流水号")
												   @NotBlank(message = "流水号不能未空")
												   @PathVariable("applicationId")String applicationId) {
		return R.data(account_taxation_infoService.queryListByApplicationId(applicationId));
	}
}
