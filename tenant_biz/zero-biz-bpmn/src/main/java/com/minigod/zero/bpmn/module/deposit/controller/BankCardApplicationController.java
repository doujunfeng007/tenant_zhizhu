package com.minigod.zero.bpmn.module.deposit.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.enums.BankAccountType;
import com.minigod.zero.bpmn.module.deposit.bo.BankCardApplicationQuery;
import com.minigod.zero.bpmn.module.deposit.service.BankCardApplicationService;
import com.minigod.zero.bpmn.module.deposit.service.BankCardImageService;
import com.minigod.zero.bpmn.module.deposit.vo.BankCardApplicationVO;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Map;

/**
 * 银行卡审核申请表(client_bank_card_application)表控制层
 *
 * @author chenyu
 */
@Validated
@RestController
@AllArgsConstructor
@Api(value = "银行卡审核申请表", tags = "银行卡审核申请表接口")
@RequestMapping(AppConstant.BACK_API_PREFIX + "/deposit/bankCardApplication")
public class BankCardApplicationController extends ZeroController {
	private final BankCardApplicationService bankCardApplicationService;
	private final BankCardImageService bankCardImageService;

	/**
	 * 获取银行账户类型枚举
	 */
	@GetMapping("/bank-account-type")
	@ApiOperation(value = "获取银行账户类型枚举")
	public R<Map<Integer, String>> bankAccountType() {
		return R.data(BankAccountType.getMap());
	}

	/**
	 * 客户银行卡表 详情
	 */
	@GetMapping("/{applicationId}")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "获取银行卡详细接口")
	public R<BankCardApplicationVO> detail(@PathVariable("applicationId") String applicationId) {
		BankCardApplicationVO detail = bankCardApplicationService.queryByApplicationId(applicationId);
		if (detail != null) {
			detail.setImageInfos(bankCardImageService.queryByApplicationId(applicationId));
		}
		return R.data(detail);
	}

	/**
	 * 客户银行卡表 自定义分页
	 */
	@GetMapping("/applicationList")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "银行卡查询分页接口")
	public R<IPage<BankCardApplicationVO>> page(BankCardApplicationQuery applicationQuery,
												Query query) {
		IPage<BankCardApplicationVO> pages = bankCardApplicationService.selectBankCardApplicationPage(Condition.getPage(query), applicationQuery);
		return R.data(pages);
	}

	@ApiOperation("批量申领")
	@PostMapping(value = "/batchClaim")
	public R<String> batchClaim(@ApiParam(value = "流程号串", required = true)
								@NotEmpty(message = "流程号不能为空")
								@RequestParam String[] applicationIds,
								@ApiParam(value = "流程状态", required = true)
								@NotNull(message = "流程状态不能为空")
								@RequestParam("applicationStatus") Integer applicationStatus) {
		return R.data(bankCardApplicationService.batchClaim(applicationStatus, Arrays.asList(applicationIds)));
	}

	@ApiOperation("批量取消认领")
	@PostMapping(value = "batchUnClaim")
	public R<String> batchUnClaim(@ApiParam(value = "流程号串", required = true)
								  @NotEmpty(message = "任务不能为空")
								  @RequestParam String[] applicationIds) {
		return R.data(bankCardApplicationService.batchUnClaim(Arrays.asList(applicationIds)));
	}

	@ApiOperation("拒绝节点")
	@PostMapping(value = "rejectNode")
	public R<String> rejectNode(@ApiParam(value = "流程号串", required = true)
								@NotBlank(message = "任务不能为空")
								@RequestParam("applicationId") String applicationId,
								@ApiParam(value = "任务实例ID", required = true)
								@RequestParam("instanceId") String instanceId,
								@NotBlank(message = "请填写拒绝原因")
								@ApiParam(value = "原因", required = true)
								@RequestParam("reason") String reason) {
		bankCardApplicationService.rejectNode(applicationId, instanceId, reason);
		return R.success();
	}


	@ApiOperation("通过节点")
	@PostMapping(value = "passNode")
	public R<String> passCustomer(@ApiParam(value = "流程号串", required = true)
								  @NotBlank(message = "任务不能为空")
								  @RequestParam("applicationId") String applicationId,
								  @ApiParam(value = "任务 ID", required = true)
								  @RequestParam("taskId") String taskId,
								  @ApiParam(value = "原因")
								  @RequestParam(value = "reason", required = false) String reason) {
		bankCardApplicationService.passNode(applicationId, taskId, reason);
		return R.success();
	}

}
