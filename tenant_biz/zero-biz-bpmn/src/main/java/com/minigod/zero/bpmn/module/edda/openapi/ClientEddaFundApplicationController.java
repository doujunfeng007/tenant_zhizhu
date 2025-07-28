package com.minigod.zero.bpmn.module.edda.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.constant.EDDAMessageConstant;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaFundApplicationEntity;
import com.minigod.zero.bpmn.module.edda.service.ClientEddaFundApplicationService;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.mp.support.Condition;
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

@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/open_api/edda/fund")
@Validated
@Slf4j
@Api(value = "客户EDDA入金", tags = "客户EDDA入金")
@AllArgsConstructor
public class ClientEddaFundApplicationController extends ZeroController {

	private final ClientEddaFundApplicationService clientEddaFundApplicationService;

	@ApiLog("查询EDDAFund入金信息详情")
	@GetMapping("/{applicationId}")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "查询EDDAFund入金信息详情")
	public R eddaCustInfo(@NotNull(message = "预约号不能为空")
						  @PathVariable("applicationId") String applicationId) {
		ClientEddaFundApplicationEntity entity = new ClientEddaFundApplicationEntity();
		entity.setApplicationId(applicationId);
		ClientEddaFundApplicationEntity detail = clientEddaFundApplicationService.getOne(Condition.getQueryWrapper(entity));
		if (detail == null) {
			return R.fail(I18nUtil.getMessage(EDDAMessageConstant.EDDA_FUND_APPLICATION_DETAIL_QUERY_FAILED_NOTICE));
		}
		return R.data(detail);
	}
}
