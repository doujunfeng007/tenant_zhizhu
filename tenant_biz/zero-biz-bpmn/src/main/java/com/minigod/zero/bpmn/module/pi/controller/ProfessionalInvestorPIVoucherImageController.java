package com.minigod.zero.bpmn.module.pi.controller;

import com.minigod.zero.bpmn.module.pi.enums.VoucherImageTypeEnum;
import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIVoucherImageService;
import com.minigod.zero.bpmn.module.pi.vo.ProfessionalInvestorPIVoucherImageVO;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 专业投资者PI申请凭证处理 控制器
 *
 * @author eric
 * @since 2024-05-07
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/pi/professional-investor-pi-image")
@Api(value = "专业投资者PI申请凭证处理", tags = "专业投资者PI申请凭证处理接口")
public class ProfessionalInvestorPIVoucherImageController extends ZeroController {

	private final IProfessionalInvestorPIVoucherImageService professionalInvestorPIVoucherImageService;

	@ApiLog("查询专业投资者PI申请凭证上传列表")
	@ApiOperation("查询专业投资者PI申请凭证上传列表")
	@GetMapping("/list/{applicationId}")
	public R<List<ProfessionalInvestorPIVoucherImageVO>> list(@ApiParam("流水号")
															  @NotBlank(message = "流水号不能为空")
															  @PathVariable("applicationId") String applicationId) {
		List<Integer> imageTypes = new ArrayList<>();
		imageTypes.add(VoucherImageTypeEnum.ASSETS_CERTIFICATE.getType());
		imageTypes.add(VoucherImageTypeEnum.SUPPLEMENTARY_PROOF.getType());
		return R.data(professionalInvestorPIVoucherImageService.queryByApplicationId(applicationId, imageTypes));
	}

	@ApiLog("查询专业投资者PI申请签名图片")
	@ApiOperation("查询专业投资者PI申请签名图片")
	@GetMapping("/signature/{applicationId}")
	public R<List<ProfessionalInvestorPIVoucherImageVO>> signature(@ApiParam("流水号")
																   @NotBlank(message = "流水号不能为空")
																   @PathVariable("applicationId") String applicationId) {
		List<Integer> imageTypes = new ArrayList<>();
		imageTypes.add(VoucherImageTypeEnum.SIGNATURE_IMAGE.getType());
		return R.data(professionalInvestorPIVoucherImageService.queryByApplicationId(applicationId, imageTypes));
	}
}
