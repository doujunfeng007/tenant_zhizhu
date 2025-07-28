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

import com.minigod.zero.bpmn.module.account.entity.AccountW8benInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountW8benInfoVO;
import com.minigod.zero.bpmn.module.account.service.IAccountW8benInfoService;


/**
 *  控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX+"/account/accountW8benInfo")
@Api(value = "", tags = "")
public class AccountW8benInfoController extends ZeroController {

	private final IAccountW8benInfoService account_w8ben_infoService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入account_w8ben_info")
	public R<AccountW8benInfoEntity> detail(AccountW8benInfoEntity account_w8ben_info) {
		AccountW8benInfoEntity detail = account_w8ben_infoService.getOne(Condition.getQueryWrapper(account_w8ben_info));
		return R.data(detail);
	}

	/**
	 * 分页 代码自定义代号
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入account_w8ben_info")
	public R<IPage<AccountW8benInfoEntity>> list(AccountW8benInfoEntity account_w8ben_info, Query query) {
		IPage<AccountW8benInfoEntity> pages = account_w8ben_infoService.page(Condition.getPage(query), Condition.getQueryWrapper(account_w8ben_info));
		return R.data(pages);
	}

	/**
	 * 新增 代码自定义代号
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入account_w8ben_info")
	public R save(@Valid @RequestBody AccountW8benInfoEntity account_w8ben_info) {
		return R.status(account_w8ben_infoService.save(account_w8ben_info));
	}

	/**
	 * 修改 代码自定义代号
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入account_w8ben_info")
	public R update(@Valid @RequestBody AccountW8benInfoEntity account_w8ben_info) {
		return R.status(account_w8ben_infoService.updateById(account_w8ben_info));
	}

	/**
	 * 新增或修改 代码自定义代号
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入account_w8ben_info")
	public R submit(@Valid @RequestBody AccountW8benInfoEntity account_w8ben_info) {
		return R.status(account_w8ben_infoService.saveOrUpdate(account_w8ben_info));
	}


	/**
	 * 删除 代码自定义代号
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(account_w8ben_infoService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 获取w8信息详细信息
	 */
	@ApiOperation("获取w8信息详细信息")
	@GetMapping("/{applicationId}")
	public R<AccountW8benInfoVO> getInfo(@ApiParam("预约号")
											 @NotNull(message = "预约号不能为空")
											 @PathVariable("applicationId") String applicationId) {
		return R.data(account_w8ben_infoService.queryByApplicationId(applicationId));
	}

}
