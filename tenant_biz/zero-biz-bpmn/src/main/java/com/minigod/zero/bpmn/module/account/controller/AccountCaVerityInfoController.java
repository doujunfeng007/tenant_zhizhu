package com.minigod.zero.bpmn.module.account.controller;

import com.minigod.zero.bpmn.module.account.bo.query.CaQuery;
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

import com.minigod.zero.bpmn.module.account.entity.AccountCaVerityInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountCaVerityInfoVO;
import com.minigod.zero.bpmn.module.account.service.IAccountCaVerityInfoService;


/**
 *  控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX+"/account/accountCaVerityInfo")
@Api(value = "", tags = "")
public class AccountCaVerityInfoController extends ZeroController {

	private final IAccountCaVerityInfoService account_ca_verity_infoService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入account_ca_verity_info")
	public R<AccountCaVerityInfoEntity> detail(AccountCaVerityInfoEntity account_ca_verity_info) {
		AccountCaVerityInfoEntity detail = account_ca_verity_infoService.getOne(Condition.getQueryWrapper(account_ca_verity_info));
		return R.data(detail);
	}

	/**
	 * 分页 代码自定义代号
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查询CA认证信息列表", notes = "传入account_ca_verity_info")
	public R<IPage<AccountCaVerityInfoVO>> list(CaQuery caQuery, Query query) {
		IPage<AccountCaVerityInfoVO> pages = account_ca_verity_infoService.queryPageList(Condition.getPage(query), caQuery);
		return R.data(pages);
	}

	/**
	 * 新增 代码自定义代号
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入account_ca_verity_info")
	public R save(@Valid @RequestBody AccountCaVerityInfoEntity account_ca_verity_info) {
		return R.status(account_ca_verity_infoService.save(account_ca_verity_info));
	}

	/**
	 * 修改 代码自定义代号
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入account_ca_verity_info")
	public R update(@Valid @RequestBody AccountCaVerityInfoEntity account_ca_verity_info) {
		return R.status(account_ca_verity_infoService.updateById(account_ca_verity_info));
	}

	/**
	 * 新增或修改 代码自定义代号
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入account_ca_verity_info")
	public R submit(@Valid @RequestBody AccountCaVerityInfoEntity account_ca_verity_info) {
		return R.status(account_ca_verity_infoService.saveOrUpdate(account_ca_verity_info));
	}


	/**
	 * 删除 代码自定义代号
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(account_ca_verity_infoService.deleteLogic(Func.toLongList(ids)));
	}

	@ApiOperation("获取CA认证信息详细信息")
	@GetMapping("/getInfoByApplicationId/{applicationId}")
	public R<AccountCaVerityInfoVO> queryByApplicationId(@ApiParam("流水号")
														   @NotBlank(message = "流水号不能为空")
														   @PathVariable("applicationId") String applicationId) {
		return R.data(account_ca_verity_infoService.queryByApplicationId(applicationId));
	}
}
