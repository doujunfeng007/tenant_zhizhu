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

import com.minigod.zero.bpmn.module.account.entity.AccountOtherDisclosureEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountOtherDisclosureVO;
import com.minigod.zero.bpmn.module.account.service.IAccountOtherDisclosureService;

import java.util.List;


/**
 *  控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX+"/account/accountOtherDisclosure")
@Api(value = "", tags = "")
public class AccountOtherDisclosureController extends ZeroController {

	private final IAccountOtherDisclosureService account_other_disclosureService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入account_other_disclosure")
	public R<AccountOtherDisclosureEntity> detail(AccountOtherDisclosureEntity account_other_disclosure) {
		AccountOtherDisclosureEntity detail = account_other_disclosureService.getOne(Condition.getQueryWrapper(account_other_disclosure));
		return R.data(detail);
	}

	/**
	 * 分页 代码自定义代号
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入account_other_disclosure")
	public R<IPage<AccountOtherDisclosureEntity>> list(AccountOtherDisclosureEntity account_other_disclosure, Query query) {
		IPage<AccountOtherDisclosureEntity> pages = account_other_disclosureService.page(Condition.getPage(query), Condition.getQueryWrapper(account_other_disclosure));
		return R.data(pages);
	}

	/**
	 * 新增 代码自定义代号
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入account_other_disclosure")
	public R save(@Valid @RequestBody AccountOtherDisclosureEntity account_other_disclosure) {
		return R.status(account_other_disclosureService.save(account_other_disclosure));
	}

	/**
	 * 修改 代码自定义代号
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入account_other_disclosure")
	public R update(@Valid @RequestBody AccountOtherDisclosureEntity account_other_disclosure) {
		return R.status(account_other_disclosureService.updateById(account_other_disclosure));
	}

	/**
	 * 新增或修改 代码自定义代号
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入account_other_disclosure")
	public R submit(@Valid @RequestBody AccountOtherDisclosureEntity account_other_disclosure) {
		return R.status(account_other_disclosureService.saveOrUpdate(account_other_disclosure));
	}


	/**
	 * 删除 代码自定义代号
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(account_other_disclosureService.deleteLogic(Func.toLongList(ids)));
	}

	@ApiOperation("查询其他信息披露列表")
	@GetMapping("/list/{applicationId}")
	public R<List<AccountOtherDisclosureVO>> list(@ApiParam("流水号")
													  @NotBlank(message = "流水号不能为空")
													  @PathVariable("applicationId") String applicationId) {
		return R.data(account_other_disclosureService.queryListByApplicationId(applicationId));
	}

}
