package com.minigod.zero.bpmn.module.account.controller;

import com.minigod.zero.core.launch.constant.AppConstant;
import io.swagger.annotations.Api;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.minigod.zero.core.boot.ctrl.ZeroController;


import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.minigod.zero.bpmn.module.account.entity.AccountSupplementCertificateEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountSupplementCertificateVO;
import com.minigod.zero.bpmn.module.account.service.IAccountSupplementCertificateService;


/**
 *  控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX+"/account/accountSupplementCertificate")
@Api(value = "", tags = "")
public class AccountSupplementCertificateController extends ZeroController {

	private final IAccountSupplementCertificateService account_supplement_certificateService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入account_supplement_certificate")
	public R<AccountSupplementCertificateEntity> detail(AccountSupplementCertificateEntity account_supplement_certificate) {
		AccountSupplementCertificateEntity detail = account_supplement_certificateService.getOne(Condition.getQueryWrapper(account_supplement_certificate));
		return R.data(detail);
	}

	/**
	 * 分页 代码自定义代号
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入account_supplement_certificate")
	public R<IPage<AccountSupplementCertificateEntity>> list(AccountSupplementCertificateEntity account_supplement_certificate, Query query) {
		IPage<AccountSupplementCertificateEntity> pages = account_supplement_certificateService.page(Condition.getPage(query), Condition.getQueryWrapper(account_supplement_certificate));
		return R.data(pages);
	}

	/**
	 * 新增 代码自定义代号
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入account_supplement_certificate")
	public R save(@Valid @RequestBody AccountSupplementCertificateEntity account_supplement_certificate) {
		return R.status(account_supplement_certificateService.save(account_supplement_certificate));
	}

	/**
	 * 修改 代码自定义代号
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入account_supplement_certificate")
	public R update(@Valid @RequestBody AccountSupplementCertificateEntity account_supplement_certificate) {
		return R.status(account_supplement_certificateService.updateById(account_supplement_certificate));
	}

	/**
	 * 新增或修改 代码自定义代号
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入account_supplement_certificate")
	public R submit(@Valid @RequestBody AccountSupplementCertificateEntity account_supplement_certificate) {
		return R.status(account_supplement_certificateService.saveOrUpdate(account_supplement_certificate));
	}


	/**
	 * 删除 代码自定义代号
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(account_supplement_certificateService.deleteLogic(Func.toLongList(ids)));
	}
	@ApiOperation("获取开户补充证件详细信息")
	@GetMapping("/queryByApplicationId/{applicationId}")
	public R<AccountSupplementCertificateVO> getInfo(@ApiParam("流水号")
														 @NotNull(message = "流水号不能为空")
														 @PathVariable("applicationId") String applicationId) {
		return R.data(account_supplement_certificateService.queryByApplicationId(applicationId));
	}
}
