package com.minigod.zero.bpmn.module.edda.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.dbs.DbsEddaFundBusinessService;
import com.minigod.zero.bpmn.module.edda.bo.ClientEddaFundListBO;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaFundApplicationEntity;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaInfoApplicationEntity;
import com.minigod.zero.bpmn.module.edda.service.ClientEddaFundApplicationService;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

@Api(value = "客户资金edda", tags = "客户资金edda")
@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/edda/fund")
public class BackEddaFundApplicationController extends ZeroController {

	private final ClientEddaFundApplicationService clientEddaFundApplicationService;

	@GetMapping("/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "入金申请列表")
	public R<IPage<ClientEddaFundApplicationEntity>> list(ClientEddaFundListBO bo, Query query) {
		IPage<ClientEddaFundApplicationEntity> pages = clientEddaFundApplicationService.queryPage(bo, query);
		return R.data(pages);
	}

	@PostMapping("/approval")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "后台审批入金申请")
	public R approval(@RequestParam String applicationId) {
		return clientEddaFundApplicationService.approval(applicationId);
	}

	@GetMapping("/{applicationId}")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "eddafund信息")
	public R<ClientEddaFundApplicationEntity> eddaCustInfo(@NotNull(message = "预约号不能为空") @PathVariable("applicationId") String applicationId) {
		ClientEddaFundApplicationEntity entity = new ClientEddaFundApplicationEntity();
		entity.setApplicationId(applicationId);
		ClientEddaFundApplicationEntity detail = clientEddaFundApplicationService.getOne(Condition.getQueryWrapper(entity));
		return R.data(detail);
	}

}
