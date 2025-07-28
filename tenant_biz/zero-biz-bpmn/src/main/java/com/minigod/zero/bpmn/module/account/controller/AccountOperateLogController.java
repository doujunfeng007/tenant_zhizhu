package com.minigod.zero.bpmn.module.account.controller;

import com.minigod.zero.core.launch.constant.AppConstant;
import io.swagger.annotations.Api;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;
import com.minigod.zero.core.boot.ctrl.ZeroController;


import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.minigod.zero.bpmn.module.account.entity.AccountOperateLogEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountOperateLogVO;
import com.minigod.zero.bpmn.module.account.service.IAccountOperateLogService;

import java.util.List;


/**
 *  控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX+"/account/accountOperateLog")
@Api(value = "", tags = "")
public class AccountOperateLogController extends ZeroController {

	private final IAccountOperateLogService account_operate_logService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入account_operate_log")
	public R<AccountOperateLogEntity> detail(AccountOperateLogEntity account_operate_log) {
		AccountOperateLogEntity detail = account_operate_logService.getOne(Condition.getQueryWrapper(account_operate_log));
		return R.data(detail);
	}

	/**
	 * 分页 代码自定义代号
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入account_operate_log")
	public R<IPage<AccountOperateLogEntity>> page(AccountOperateLogEntity account_operate_log, Query query) {
		IPage<AccountOperateLogEntity> pages = account_operate_logService.page(Condition.getPage(query), Condition.getQueryWrapper(account_operate_log));
		return R.data(pages);
	}

	@GetMapping("/list/{applicationId}")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查询开户内容修改记录列表", notes = "传入account_operate_log")
	public R<List<AccountOperateLogVO>> list(@PathVariable("applicationId")String applicationId) {
		return R.data(account_operate_logService.queryListByApplicationId(applicationId));
	}

	/**
	 * 新增 代码自定义代号
	 */
	@PostMapping()
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入account_operate_log")
	public R save(@Valid @RequestBody AccountOperateLogEntity account_operate_log) {
		return R.status(account_operate_logService.save(account_operate_log));
	}

	/**
	 * 修改 代码自定义代号
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入account_operate_log")
	public R update(@Valid @RequestBody AccountOperateLogEntity account_operate_log) {
		return R.status(account_operate_logService.updateById(account_operate_log));
	}

	/**
	 * 新增或修改 代码自定义代号
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入account_operate_log")
	public R submit(@Valid @RequestBody AccountOperateLogEntity account_operate_log) {
		return R.status(account_operate_logService.saveOrUpdate(account_operate_log));
	}


	/**
	 * 删除 代码自定义代号
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(account_operate_logService.deleteLogic(Func.toLongList(ids)));
	}

}
