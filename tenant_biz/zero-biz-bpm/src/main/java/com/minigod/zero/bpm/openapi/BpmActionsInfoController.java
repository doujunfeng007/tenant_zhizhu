package com.minigod.zero.bpm.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.actionsInfo.client.ICorporateActionsClient;
import com.minigod.zero.bpmn.module.actionsInfo.dto.CorporateActionsInfoDTO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公司行动相关 控制器
 *
 * @author zejie.weng
 * @since 2024-03-15
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/corporate_action_api")
@Api(value = "公司行动相关接口", tags = "公司行动相关接口")
@Slf4j
public class BpmActionsInfoController {

	private final ICorporateActionsClient iCorporateActionsClient;

	/**
	 * 提交公司行动申请
	 */
	@PostMapping("/save_actions")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "提交公司行动申请", notes = "传入corporateActionsInfoDTO")
	public R saveActions(@RequestBody CorporateActionsInfoDTO dto) {
		return iCorporateActionsClient.saveActions(dto);
	}

	/**
	 * 查看提交公司行动申请
	 */
	@PostMapping("/get_actions")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查看提交公司行动申请", notes = "传入corporateActionsInfoDTO")
	public R getActions(@RequestBody CorporateActionsInfoDTO dto) {
		return iCorporateActionsClient.getActions(dto);
	}

}
