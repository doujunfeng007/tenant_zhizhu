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

import com.minigod.zero.bpmn.module.account.entity.AccountBankVerityInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountBankVerityInfoVO;
import com.minigod.zero.bpmn.module.account.service.IAccountBankVerityInfoService;


/**
 *  控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX+"/account/accountBankVerityInfo")
@Api(value = "", tags = "")
public class AccountBankVerityInfoController extends ZeroController {

	private final IAccountBankVerityInfoService account_bank_verity_infoService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入account_bank_verity_info")
	public R<AccountBankVerityInfoEntity> detail(AccountBankVerityInfoEntity account_bank_verity_info) {
		AccountBankVerityInfoEntity detail = account_bank_verity_infoService.getOne(Condition.getQueryWrapper(account_bank_verity_info));
		return R.data(detail);
	}

	/**
	 * 分页 代码自定义代号
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入account_bank_verity_info")
	public R<IPage<AccountBankVerityInfoEntity>> list(AccountBankVerityInfoEntity account_bank_verity_info, Query query) {
		IPage<AccountBankVerityInfoEntity> pages = account_bank_verity_infoService.page(Condition.getPage(query), Condition.getQueryWrapper(account_bank_verity_info));
		return R.data(pages);
	}

	/**
	 * 新增 代码自定义代号
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入account_bank_verity_info")
	public R save(@Valid @RequestBody AccountBankVerityInfoEntity account_bank_verity_info) {
		return R.status(account_bank_verity_infoService.save(account_bank_verity_info));
	}

	/**
	 * 修改 代码自定义代号
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入account_bank_verity_info")
	public R update(@Valid @RequestBody AccountBankVerityInfoEntity account_bank_verity_info) {
		return R.status(account_bank_verity_infoService.updateById(account_bank_verity_info));
	}

	/**
	 * 新增或修改 代码自定义代号
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入account_bank_verity_info")
	public R submit(@Valid @RequestBody AccountBankVerityInfoEntity account_bank_verity_info) {
		return R.status(account_bank_verity_infoService.saveOrUpdate(account_bank_verity_info));
	}


	/**
	 * 删除 代码自定义代号
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(account_bank_verity_infoService.deleteLogic(Func.toLongList(ids)));
	}

	@ApiOperation("获取银行卡四要素验证信息详细信息")
	@GetMapping("/queryByApplicationId/{applicationId}")
	public R<AccountBankVerityInfoVO> queryByApplicationId(@ApiParam("流水号")
															   @NotBlank(message = "流水号不能为空")
															   @PathVariable("applicationId") String applicationId) {
		return R.data(account_bank_verity_infoService.queryByApplicationId(applicationId));
	}

}
