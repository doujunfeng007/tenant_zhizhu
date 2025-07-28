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

import com.minigod.zero.bpmn.module.account.entity.AccountIdentityConfirmEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountIdentityConfirmVO;
import com.minigod.zero.bpmn.module.account.service.IAccountIdentityConfirmService;


/**
 *  控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX+"/account/accountIdentityConfirm")
@Api(value = "", tags = "")
public class AccountIdentityConfirmController extends ZeroController {

	private final IAccountIdentityConfirmService account_identity_confirmService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入account_identity_confirm")
	public R<AccountIdentityConfirmEntity> detail(AccountIdentityConfirmEntity account_identity_confirm) {
		AccountIdentityConfirmEntity detail = account_identity_confirmService.getOne(Condition.getQueryWrapper(account_identity_confirm));
		return R.data(detail);
	}

	/**
	 * 分页 代码自定义代号
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入account_identity_confirm")
	public R<IPage<AccountIdentityConfirmEntity>> list(AccountIdentityConfirmEntity account_identity_confirm, Query query) {
		IPage<AccountIdentityConfirmEntity> pages = account_identity_confirmService.page(Condition.getPage(query), Condition.getQueryWrapper(account_identity_confirm));
		return R.data(pages);
	}

	/**
	 * 新增 代码自定义代号
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入account_identity_confirm")
	public R save(@Valid @RequestBody AccountIdentityConfirmEntity account_identity_confirm) {
		return R.status(account_identity_confirmService.save(account_identity_confirm));
	}

	/**
	 * 修改 代码自定义代号
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入account_identity_confirm")
	public R update(@Valid @RequestBody AccountIdentityConfirmEntity account_identity_confirm) {
		return R.status(account_identity_confirmService.updateById(account_identity_confirm));
	}

	/**
	 * 新增或修改 代码自定义代号
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入account_identity_confirm")
	public R submit(@Valid @RequestBody AccountIdentityConfirmEntity account_identity_confirm) {
		return R.status(account_identity_confirmService.saveOrUpdate(account_identity_confirm));
	}


	/**
	 * 删除 代码自定义代号
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(account_identity_confirmService.deleteLogic(Func.toLongList(ids)));
	}

	@ApiOperation("获取身份确认信息详细信息")
	@GetMapping("/getInfoByApplicationId/{applicationId}")
	public R<AccountIdentityConfirmVO> getInfo(@ApiParam("主键")
												   @NotBlank(message = "流水号不能为空")
												   @PathVariable("applicationId") String applicationId) {
		return R.data(account_identity_confirmService.queryByApplicationId(applicationId));
	}
}
