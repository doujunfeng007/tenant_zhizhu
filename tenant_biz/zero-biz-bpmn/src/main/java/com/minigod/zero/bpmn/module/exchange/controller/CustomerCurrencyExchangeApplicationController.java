package com.minigod.zero.bpmn.module.exchange.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.exchange.feign.ICurrencyExchangeClient;
import com.minigod.zero.bpmn.module.exchange.service.ICustomerCurrencyExchangeApplicationService;
import com.minigod.zero.bpmn.module.exchange.vo.CurrencyExchangeApplicationVO;
import com.minigod.zero.bpmn.module.exchange.vo.req.CurrencyExchangeQuery;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * @author chen
 * @ClassName CustomerCurrencyExchangeApplicationController.java
 * @Description TODO
 * @createTime 2024年03月16日 16:29:00
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/currencyExchangeApplication")
@Api(value = "兑换申请流程", tags = "兑换申请流程")
public class CustomerCurrencyExchangeApplicationController extends ZeroController {

	@Resource
	private ICustomerCurrencyExchangeApplicationService currencyExchangeApplicationService;

	@Resource
	private ICurrencyExchangeClient currencyExchangeClient;

	@ApiLog("查询兑换记录列表")
	@ApiOperation("查询兑换记录列表")
	@GetMapping("/list")
	@ApiOperationSupport(order = 1)
	public R<IPage<CurrencyExchangeApplicationVO>> list(CurrencyExchangeQuery query, Query pageQuery) {
		query.setTenantId(AuthUtil.getTenantId());
		return R.data(currencyExchangeApplicationService.queryPageList(query, pageQuery));
	}

	@ApiLog("导出兑换记录列表")
	@ApiOperation("导出兑换记录列表")
	@GetMapping("/list/export")
	@ApiOperationSupport(order = 2)
	public void exportList(CurrencyExchangeQuery query, Query pageQuery, HttpServletResponse response) {
		query.setTenantId(AuthUtil.getTenantId());
		currencyExchangeApplicationService.exportCheckList(query, pageQuery, response);
	}

	@ApiLog("查询兑换审核记录列表")
	@ApiOperation("查询兑换审核记录列表")
	@GetMapping("/checkList")
	@ApiOperationSupport(order = 2)
	public R<IPage<CurrencyExchangeApplicationVO>> checkList(CurrencyExchangeQuery query, Query pageQuery) {
		query.setIsCheck(1);
		query.setTenantId(AuthUtil.getTenantId());
		query.setAssignDrafter(AuthUtil.getUserId().toString());
		log.info("查询审核记录列表：getTenantId{}", AuthUtil.getTenantId());

		return R.data(currencyExchangeApplicationService.queryPageList(query, pageQuery));
	}

	@ApiLog("导出兑换审核记录列表")
	@ApiOperation("导出兑换审核记录列表")
	@GetMapping("/check-list/export")
	@ApiOperationSupport(order = 2)
	public void exportCheckList(CurrencyExchangeQuery query, Query pageQuery, HttpServletResponse response) {
		query.setIsCheck(1);
		query.setTenantId(AuthUtil.getTenantId());
		currencyExchangeApplicationService.exportCheckList(query, pageQuery, response);
	}

	@ApiLog("批量申领兑换申请流程审批任务")
	@ApiOperation("批量申领兑换申请流程审批任务")
	@PostMapping("/batchClaim")
	public R<String> batchClaim(@ApiParam("流程号串")
								@NotEmpty(message = "流程号不能为空")
								@RequestParam String[] applicationIds,
								@ApiParam("流程状态")
								@NotNull(message = "流程状态不能为空")
								@RequestParam("applicationStatus") Integer applicationStatus) {
		return R.data(currencyExchangeApplicationService.batchClaim(applicationStatus, Arrays.asList(applicationIds)));
	}

	@ApiLog("批量取消申领兑换申请流程审批任务")
	@ApiOperation("批量取消认领")
	@PostMapping("batchUnClaim")
	public R<String> batchUnClaim(@ApiParam("流程号串")
								  @NotEmpty(message = "任务不能为空")
								  @RequestParam String[] applicationIds) {
		currencyExchangeApplicationService.batchUnClaim(Arrays.asList(applicationIds));
		return R.success();
	}

	@ApiLog("驳回/退回兑换节点流程审批任务")
	@ApiOperation("驳回/退回节点")
	@PostMapping(value = "rejectNode")
	public R<String> rejectNode(@ApiParam("流程号串")
								@NotBlank(message = "任务不能为空")
								@RequestParam("applicationId") String applicationId,
								@RequestParam("taskId") String taskId,
								@RequestParam("reason") String reason,
								@RequestParam("status") Integer status) {
		currencyExchangeApplicationService.rejectNode(applicationId, taskId, reason, status);
		return R.success();
	}

	@ApiLog("驳回至上一兑换节点任务")
	@ApiOperation("驳回至上一节点")
	@PostMapping(value = "rejectPreNode")
	public R<String> rejectPreNode(@ApiParam("流程号串")
								   @NotBlank(message = "任务不能为空")
								   @RequestParam("applicationId") String applicationId,
								   @RequestParam("taskId") String taskId,
								   @RequestParam("reason") String reason) {
		currencyExchangeApplicationService.rejectPreNode(applicationId, taskId, reason);
		return R.success();
	}

	@ApiLog("通过兑换节点任务")
	@ApiOperation("通过节点")
	@PostMapping(value = "passNode")
	public R<String> passCustomer(@ApiParam("流程号串")
								  @NotBlank(message = "任务不能为空")
								  @RequestParam("applicationId") String applicationId,
								  @RequestParam("taskId") String taskId,
								  @RequestParam(value = "reason", required = false) String reason) {
		currencyExchangeApplicationService.passNode(applicationId, taskId, reason);
		return R.success();
	}

	@ApiLog("挂起兑换节点任务")
	@ApiOperation("挂起")
	@PostMapping("hangUp")
	public R<String> hangUp(@ApiParam("流程号串")
							@NotEmpty(message = "预约号不能为空")
							@RequestParam String applicationId) {
		currencyExchangeApplicationService.hangUp(applicationId);
		return R.success();
	}

	@ApiLog("取消挂起兑换节点任务")
	@ApiOperation("取消挂起")
	@PostMapping("unHangUp")
	public R<String> unHangUp(@ApiParam("流程号串")
							  @NotEmpty(message = "预约号不能为空")
							  @RequestParam String applicationId) {
		currencyExchangeApplicationService.unHangUp(applicationId);
		return R.success();
	}

	@ApiLog("手工兑换完成任务")
	@ApiOperation("手工兑换完成")
	@PostMapping(value = "doProcessingComplete")
	public R<String> doProcessingComplete(@ApiParam("流程号串")
										  @NotBlank(message = "任务不能为空")
										  @RequestParam("applicationId") String applicationId,
										  @RequestParam("instanceId") String instanceId) {
		currencyExchangeApplicationService.doProcessingComplete(applicationId, instanceId);
		return R.success();
	}

	@ApiLog("撤销兑换完成任务")
	@ApiOperation("撤销")
	@PostMapping(value = "cancel")
	public R<String> cancel(@ApiParam("流程号串")
							@NotBlank(message = "任务不能为空")
							@RequestParam("applicationId") String applicationId,
							@RequestParam("instanceId") String instanceId) {
		return currencyExchangeClient.cancel(applicationId);
	}
}
