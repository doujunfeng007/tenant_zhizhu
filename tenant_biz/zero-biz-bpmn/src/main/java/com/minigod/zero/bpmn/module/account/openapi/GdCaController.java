package com.minigod.zero.bpmn.module.account.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpmn.module.account.gdca.request.LivingBodyValidateBo;
import com.minigod.zero.bpmn.module.account.gdca.request.UnionPayVerifyBo;
import com.minigod.zero.bpmn.module.account.gdca.response.GdCaVerifyResult;
import com.minigod.zero.bpmn.module.account.service.GdCaService;
import com.minigod.zero.bpmn.module.constant.CAMessageConstant;
import com.minigod.zero.bpmn.module.pi.dto.LivingBodyValidateDTO;
import com.minigod.zero.bpmn.module.pi.dto.UnionPayFourDTO;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/open-api-ca")
@Validated
@Slf4j
@AllArgsConstructor
@Api("GDCA认证接口")
public class GdCaController {
	private final GdCaService gdCaService;

	/**
	 * 提交开户资料之前验证银联四要素
	 *
	 * @param unionPayFourDTOCommonReqVO
	 * @return
	 */
	@ApiLog(value = "提交开户资料之前验证银联四要素")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "提交开户资料之前验证银联四要素", notes = "提交开户资料之前验证银联四要素")
	@PostMapping("/check-union-pay-four")
	public R<String> checkUnionPayFours(@RequestBody CommonReqVO<UnionPayFourDTO> unionPayFourDTOCommonReqVO) {
		try {
			UnionPayVerifyBo unionPayVerifyBo = new UnionPayVerifyBo();
			BeanUtil.copyProperties(unionPayFourDTOCommonReqVO.getParams(), unionPayVerifyBo);
//			GdCaVerifyResult result = gdCaService.unionPayVerify(unionPayVerifyBo);
//			if (!result.isStepIsOk()) {
//				return R.fail(result.getMsg());
//			} else {
//				return R.data(result.getMsg());
//			}
			return R.data("success");
		} catch (Exception e) {
			return R.fail(String.format(I18nUtil.getMessage(CAMessageConstant.CA_FAILED_TO_VERIFY_BANK_INFO_NOTICE), e.getMessage()));
		}
	}

	@ApiLog(value = "活体公安对比")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "活体公安对比", notes = "活体公安对比")
	@PostMapping("/living-body-validate")
	public R<String> livingBodyValidate(@RequestBody CommonReqVO<LivingBodyValidateDTO> livingBodyValidateDTOCommonReqVO) {
		try {
			LivingBodyValidateBo livingBodyValidateBo = new LivingBodyValidateBo();
			BeanUtil.copyProperties(livingBodyValidateDTOCommonReqVO.getParams(), livingBodyValidateBo);
			GdCaVerifyResult result = gdCaService.livingBodyValidate(livingBodyValidateBo);
			if (!result.isStepIsOk()) {
				return R.fail(result.getMsg());
			} else {
				return R.data(result.getMsg());
			}
		} catch (Exception e) {
			return R.fail(String.format(I18nUtil.getMessage(CAMessageConstant.CA_LIVE_DETECTION_FAILED_NOTICE), e.getMessage()));
		}
	}
}
