package com.minigod.zero.bpmn.module.account.controller;

import com.minigod.zero.bpmn.module.account.bo.query.ApplicationQuery;
import com.minigod.zero.bpmn.module.account.bo.query.BackApplicationQuery;
import com.minigod.zero.bpmn.module.account.bo.query.CABankVerifyQuery;
import com.minigod.zero.bpmn.module.account.vo.AccountBackInfoVO;
import com.minigod.zero.bpmn.module.account.vo.AccountCABankVerifyInfoVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.secure.utils.AuthUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.minigod.zero.core.boot.ctrl.ZeroController;

import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.minigod.zero.bpmn.module.account.vo.AccountOpenApplicationVO;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenApplicationService;

import java.util.Arrays;


/**
 * 客户开户申请接口
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/account/accountOpenApplication")
@Api(value = "客户开户申请接口", tags = "客户开户申请接口")
public class AccountOpenApplicationController extends ZeroController {

	private final IAccountOpenApplicationService accountOpenApplicationService;

	@ApiOperation("查询客户开户申请列表")
	@GetMapping("/list")
	public R<IPage<AccountOpenApplicationVO>> list(ApplicationQuery bo, Query pageQuery) {
		return R.data(accountOpenApplicationService.queryPageList(bo, pageQuery));
	}

	@ApiOperation("查询退回给客户的开户申请记录列表")
	@GetMapping("/back/list")
	public R<IPage<AccountBackInfoVO>> backList(BackApplicationQuery params, Query pageQuery) {
		return R.data(accountOpenApplicationService.queryBackPageList(params, pageQuery));
	}

	@ApiOperation("查询CA认证失败的开户申请记录列表")
	@GetMapping("/ca-failed/list")
	public R<IPage<AccountCABankVerifyInfoVO>> queryCABankVerifyInfoPageList(CABankVerifyQuery params, Query pageQuery) {
		return R.data(accountOpenApplicationService.queryCABankVerifyInfoPageList(params, pageQuery));
	}

	@ApiOperation("批量申领")
	@PostMapping("/batchClaim")
	public R<String> batchClaim(@ApiParam("流程号串")
								@NotEmpty(message = "流程号不能为空")
								@RequestParam String[] applicationIds,
								@ApiParam("流程状态")
								@NotNull(message = "流程状态不能为空")
								@RequestParam("applicationStatus") Integer applicationStatus) {
		return R.data(accountOpenApplicationService.batchClaim(applicationStatus, Arrays.asList(applicationIds)));
	}

	@ApiOperation("批量取消认领")
	@PostMapping("batchUnClaim")
	public R<String> batchUnClaim(@ApiParam("流程号串")
								  @NotEmpty(message = "任务不能为空")
								  @RequestParam String[] applicationIds
	) {
		accountOpenApplicationService.batchUnClaim(Arrays.asList(applicationIds));
		return R.success();
	}

	@ApiOperation("驳回/退回节点")
	@PostMapping(value = "rejectNode")
	public R<String> rejectNode(@ApiParam("流程号串")
								@NotBlank(message = "任务不能为空")
								@RequestParam("applicationId") String applicationId,
								@RequestParam("taskId") String taskId,
								@RequestParam("reason") String reason,
								@RequestParam("status") Integer status
	) {
		accountOpenApplicationService.rejectNode(applicationId, taskId, reason, status);
		return R.success();
	}

	@ApiOperation("驳回至上一节点")
	@PostMapping(value = "rejectPreNode")
	public R<String> rejectPreNode(@ApiParam("流程号串")
								   @NotBlank(message = "任务不能为空")
								   @RequestParam("applicationId") String applicationId,
								   @RequestParam("taskId") String taskId,
								   @RequestParam("reason") String reason
	) {
		accountOpenApplicationService.rejectPreNode(applicationId, taskId, reason);
		return R.success();
	}


	@ApiOperation("通过节点")
	@PostMapping(value = "passNode")
	public R<String> passCustomer(@ApiParam("流程号串")
								  @NotBlank(message = "任务不能为空")
								  @RequestParam("applicationId") String applicationId,
								  @RequestParam("taskId") String taskId,
								  @RequestParam(value = "reason", required = false) String reason
	) {
		accountOpenApplicationService.passNode(applicationId, taskId, reason);
		return R.success();
	}

	@ApiOperation("禁止/允许开户")
	@PostMapping(value = "blacklist")
	public R<Boolean> blackListStatus(@ApiParam("流程号串")
									  @NotBlank(message = "任务不能为空")
									  @RequestParam("applicationId") String applicationId,
									  @RequestParam(value = "blacklist") Integer blacklist,
									  @RequestParam(value = "reason", required = false) String reason
	) {

		return R.data(accountOpenApplicationService.updateBlackListStatus(applicationId, blacklist, reason));
	}

	@ApiOperation("重置aml")
	@GetMapping(value = "resetAml/{applicationId}")
	public R<String> resetAml(@ApiParam("流程号串")
							  @NotBlank(message = "任务不能为空")
							  @PathVariable("applicationId") String applicationId
	) {
		accountOpenApplicationService.resetAml(applicationId);
		return R.success();
	}

}
