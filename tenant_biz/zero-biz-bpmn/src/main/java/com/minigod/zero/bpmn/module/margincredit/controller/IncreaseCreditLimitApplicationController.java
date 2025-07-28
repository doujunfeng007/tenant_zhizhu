package com.minigod.zero.bpmn.module.margincredit.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.margincredit.service.IIncreaseCreditLimitApplicationService;
import com.minigod.zero.bpmn.module.margincredit.vo.IncreaseCreditLimitApplicationVO;
import com.minigod.zero.bpmn.module.margincredit.vo.req.IncreaseCreditQuery;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * @author chen
 * @ClassName IncreaseCreditLimitApplicationController.java
 * @Description TODO
 * @createTime 2024年03月11日 17:26:00
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/marginCreditApplication")
@Api(value = "信用额度申请接口", tags = "信用额度申请接口")
public class IncreaseCreditLimitApplicationController extends ZeroController {

	@Resource
	private IIncreaseCreditLimitApplicationService increaseCreditLimitApplicationService;

	@ApiOperation("查询信用额度申请列表")
	@GetMapping("/list")
	public R<IPage<IncreaseCreditLimitApplicationVO>> list(IncreaseCreditQuery query, Query pageQuery) {
		query.setTenantId(AuthUtil.getTenantId());
		return R.data(increaseCreditLimitApplicationService.queryPageList(query, pageQuery));
	}

	@ApiOperation("查询信用额度申请列表")
	@GetMapping("/checkList")
	@ApiOperationSupport(order = 2)
	public R<IPage<IncreaseCreditLimitApplicationVO>> checkList(IncreaseCreditQuery query, Query pageQuery) {
		query.setIsCheck(1);
		query.setTenantId(AuthUtil.getTenantId());
		return R.data(increaseCreditLimitApplicationService.queryPageList(query, pageQuery));
	}

	@ApiOperation("批量申领")
	@PostMapping("/batchClaim")
	public R<String> batchClaim(@ApiParam("流程号串")
								@NotEmpty(message = "流程号不能为空")
								@RequestParam String[] applicationIds,
								@ApiParam("流程状态")
								@NotNull(message = "流程状态不能为空")
								@RequestParam("applicationStatus") Integer applicationStatus
	) {
		return R.data(increaseCreditLimitApplicationService.batchClaim(applicationStatus, Arrays.asList(applicationIds)));
	}

	@ApiOperation("批量取消认领")
	@PostMapping("batchUnClaim")
	public R<String> batchUnClaim(@ApiParam("流程号串")
								  @NotEmpty(message = "任务不能为空")
								  @RequestParam String[] applicationIds
	) {
		increaseCreditLimitApplicationService.batchUnClaim(Arrays.asList(applicationIds));
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
		increaseCreditLimitApplicationService.rejectNode(applicationId, taskId, reason, status);
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
		increaseCreditLimitApplicationService.rejectPreNode(applicationId, taskId, reason);
		return R.success();
	}

	@ApiOperation("通过节点")
	@PostMapping(value = "passNode")
	public R<String> passCustomer(@ApiParam("流程号串")
								  @NotBlank(message = "任务不能为空")
								  @RequestParam("applicationId") String applicationId,
								  @RequestParam("taskId") String taskId,
								  @RequestParam(value = "reason",required = false) String reason
	) {
		increaseCreditLimitApplicationService.passNode(applicationId,taskId,reason);
		return R.success();
	}

}
