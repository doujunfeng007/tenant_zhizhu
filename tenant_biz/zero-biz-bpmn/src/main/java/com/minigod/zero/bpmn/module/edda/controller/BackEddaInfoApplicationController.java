package com.minigod.zero.bpmn.module.edda.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.edda.bo.ClientEddaInfoListBO;
import com.minigod.zero.bpmn.module.edda.bo.EddaInfoApprovalBO;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaInfoApplicationEntity;
import com.minigod.zero.bpmn.module.edda.service.ClientEddaInfoApplicationService;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @ClassName: com.minigod.zero.customer.api.controller.edda.FundDepositApplicationController
 * @Description: edda客户入金
 * @Author: linggr
 * @CreateDate: 2024/5/9 17:38
 * @Version: 1.0
 */

@Api(value = "客户授权edda(后台)", tags = "客户授权edda(后台)")
@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/edda/info")
public class BackEddaInfoApplicationController extends ZeroController {

	private final ClientEddaInfoApplicationService clientEddaInfoApplicationService;


	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "授权列表")
	@GetMapping("/list")
	public R<IPage<ClientEddaInfoApplicationEntity>> list(ClientEddaInfoListBO bo, Query query) {
		return R.data(clientEddaInfoApplicationService.pages(bo,query));
	}

	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "后台审批授权申请")
	@PostMapping("/approval")
	public R approval(@RequestBody  EddaInfoApprovalBO eddaInfoApprovalBO) {
		return clientEddaInfoApplicationService.approval(eddaInfoApprovalBO);
	}

	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "edda授权申请 状态刷新")
	@PostMapping("/refreshStatus")
	public R refreshStatus(@RequestParam String applicationId) {
		return clientEddaInfoApplicationService.refreshStatus(applicationId);
	}

	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "eddainfo信息")
	@GetMapping("/{applicationId}")
	public R<ClientEddaInfoApplicationEntity> eddaCustInfo(@NotNull(message = "预约号不能为空")
							  @PathVariable("applicationId") String applicationId) {
		ClientEddaInfoApplicationEntity entity = new ClientEddaInfoApplicationEntity();
		entity.setApplicationId(applicationId);
		ClientEddaInfoApplicationEntity detail = clientEddaInfoApplicationService.getOne(Condition.getQueryWrapper(entity));
		return R.data(detail);
	}

}
