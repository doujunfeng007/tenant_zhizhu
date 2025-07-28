package com.minigod.zero.bpmn.module.account.controller;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
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

import com.minigod.zero.bpmn.module.account.entity.AccountAdditionalDetailEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountAdditionalDetailVO;
import com.minigod.zero.bpmn.module.account.service.IAccountAdditionalDetailService;

import java.util.List;


/**
 *  开户补充资料信息备注表
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX+"/account/accountAdditionalDetail")
@Api(value = "开户补充资料信息备注表", tags = "开户补充资料信息接口")
public class AccountAdditionalDetailController extends ZeroController {

	private final IAccountAdditionalDetailService account_additional_detailService;

	/**
	 * 开户补充资料信息备注详情查询
	 */
	@ApiLog("开户补充资料信息备注详情查询")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入account_additional_detail")
	public R<AccountAdditionalDetailEntity> detail(AccountAdditionalDetailEntity account_additional_detail) {
		AccountAdditionalDetailEntity detail = account_additional_detailService.getOne(Condition.getQueryWrapper(account_additional_detail));
		return R.data(detail);
	}

	/**
	 * 开户补充资料信息备注列表查询
	 */
	@ApiLog("开户补充资料信息备注列表查询")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入account_additional_detail")
	public R<IPage<AccountAdditionalDetailEntity>> list(AccountAdditionalDetailEntity account_additional_detail, Query query) {
		IPage<AccountAdditionalDetailEntity> pages = account_additional_detailService.page(Condition.getPage(query), Condition.getQueryWrapper(account_additional_detail));
		return R.data(pages);
	}

	/**
	 * 开户补充资料信息备注持久化
	 */
	@ApiLog("开户补充资料信息备注持久化")
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入account_additional_detail")
	public R save(@Valid @RequestBody AccountAdditionalDetailEntity account_additional_detail) {
		return R.status(account_additional_detailService.save(account_additional_detail));
	}

	/**
	 * 开户补充资料信息备注修改
	 */
	@ApiLog("开户补充资料信息备注修改")
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入account_additional_detail")
	public R update(@Valid @RequestBody AccountAdditionalDetailEntity account_additional_detail) {
		return R.status(account_additional_detailService.updateById(account_additional_detail));
	}

	/**
	 * 开户补充资料信息备注新增或修改
	 */
	@ApiLog("开户补充资料信息备注新增或修改")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入account_additional_detail")
	public R submit(@Valid @RequestBody AccountAdditionalDetailEntity account_additional_detail) {
		return R.status(account_additional_detailService.saveOrUpdate(account_additional_detail));
	}


	/**
	 * 开户补充资料信息备注删除
	 */
	@ApiLog("开户补充资料信息备注删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "开户补充资料信息备注删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(account_additional_detailService.deleteLogic(Func.toLongList(ids)));
	}

	@ApiLog("根据申请ID查询补充资料信息备注")
	@ApiOperation("查询补充资料信息备注列表")
	@GetMapping("/list/{applicationId}")
	public R<List<AccountAdditionalDetailVO>> list(@ApiParam("流水号")
													   @NotBlank(message = "流水号不能为空")
													   @PathVariable("applicationId") String applicationId) {
		return R.data(account_additional_detailService.queryListByApplicationId(applicationId));
	}
}
