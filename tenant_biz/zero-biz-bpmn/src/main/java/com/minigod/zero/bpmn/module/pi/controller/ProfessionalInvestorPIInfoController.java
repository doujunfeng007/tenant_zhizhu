package com.minigod.zero.bpmn.module.pi.controller;

import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIInfoService;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 专业投资者PI申请基本信息 控制器
 *
 * @author eric
 * @since 2024-05-07
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/pi/professional-investor-pi-info")
@Api(value = "专业投资者PI申请基本信息", tags = "专业投资者PI申请基本信息接口")
public class ProfessionalInvestorPIInfoController extends ZeroController {

	private final IProfessionalInvestorPIInfoService professionalInvestorPIInfoService;

	/**
	 * 撤销专业投资者身份
	 *
	 * @param applicationId
	 * @param revocationReason
	 */
	@ApiLog("撤销专业投资者身份")
	@PutMapping("/revoke")
	@ApiOperation(value = "撤销专业投资者身份接口")
	public R<String> revokeProfessionalInvestorStatus(@RequestParam(value = "applicationId") String applicationId,
													  @RequestParam(value = "revocationReason") String revocationReason) {
		Boolean result = professionalInvestorPIInfoService.revokeProfessionalInvestorStatus(applicationId, revocationReason);
		return R.success(result ? "撤销成功!" : "撤销失败!");
	}
}
