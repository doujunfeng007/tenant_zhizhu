package com.minigod.zero.bpm.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpm.dto.BpmAccountRespDto;
import com.minigod.zero.bpm.dto.acct.req.CaCertificationDto;
import com.minigod.zero.bpm.dto.acct.req.RiskEvaluationReqDto;
import com.minigod.zero.bpm.dto.acct.resp.BpmResponseDto;
import com.minigod.zero.bpm.dto.acct.resp.RiskEvaluationRespDto;
import com.minigod.zero.bpm.dto.acct.resp.RiskQuestionRespDto;
import com.minigod.zero.bpm.service.openAccount.IBpmAccountService;
import com.minigod.zero.bpm.vo.UserHkidrVo;
import com.minigod.zero.bpm.vo.request.YfundRiskTempVo;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/bpm_acct")
@Api(value = "bpm账户业务", tags = "bpm账户业务")
public class BpmAccountController {

	@Resource
	private IBpmAccountService bpmAccountService;

	@PostMapping("/account_info")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取客户信息", notes = "获取客户信息")
	public R<BpmAccountRespDto> accountInfo() {
		Long custId = AuthUtil.getCustId();
		return bpmAccountService.getAccountRespDto(custId);
	}

	@PostMapping("/risk_question")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "获取基金风险评测题库", notes = "获取基金风险评测题库")
	public R<List<RiskQuestionRespDto>> riskQuestion(@RequestBody CommonReqVO<RiskEvaluationReqDto> vo) {

		if (null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);

		return bpmAccountService.riskQuestion(vo.getParams());
	}

	@PostMapping("/save_evaluation_temp")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "评测未完成结果保留", notes = "评测未完成结果保留")
	public R saveEvaluationTemp(@RequestBody CommonReqVO<RiskEvaluationReqDto> vo) {
		if (null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return bpmAccountService.saveEvaluationTemp(vo.getParams());
	}

	@PostMapping("/evaluation_temp")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "基金评测-未完成数据获取", notes = "基金评测-未完成数据获取")
	public R<YfundRiskTempVo> evaluationTemp() {
		return bpmAccountService.evaluationTemp();
	}

	@PostMapping("/save_risk_evaluation")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "提交风险评测结果", notes = "提交风险评测结果")
	public R saveRiskEvaluation(@RequestBody CommonReqVO<RiskEvaluationReqDto> vo) {
		if (null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return bpmAccountService.saveRiskEvaluation(vo.getParams());
	}

	@PostMapping("/get_risk_evaluation")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取风险评测结果", notes = "获取风险评测结果")
	public R<RiskEvaluationRespDto> getRiskEvaluationInfo() {
		return bpmAccountService.getRiskEvaluationInfo();
	}

	@PostMapping("/ca_certification")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "CA补充认证", notes = "CA补充认证")
	public R<BpmResponseDto> caCertification(@RequestBody CommonReqVO<CaCertificationDto> vo) {
		if (null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return bpmAccountService.caCertification(vo.getParams());
	}

	@PostMapping("/ca_certification_status")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "查询CA补充认证状态", notes = "查询CA补充认证状态")
	public R<BpmResponseDto> caCertificationStatus(@RequestBody CommonReqVO<CaCertificationDto> vo) {
		return bpmAccountService.caCertificationStatus(null);
	}

	/**
	 * 是否需要授权HKIDR
	 */
	@GetMapping("/need_grant_hkidr")
	@ApiOperation(value = "是否需要授权HKIDR", notes = "无参")
	public R needGrantHkidr() {
		return bpmAccountService.needGrantHkidr(AuthUtil.getCustId());
	}

	/**
	 * HKIDR授权（暂未用到）
	 */
	@PostMapping("/grant_hkidr")
	@ApiOperation(value = "HKIDR授权", notes = "HKIDR授权")
	public R grantHkidr(@RequestBody UserHkidrVo vo) {
		if (null == vo) vo = new UserHkidrVo();
		vo.setUserId(AuthUtil.getCustId().intValue());
		return bpmAccountService.grantHkidr(vo);
	}


	@GetMapping("/apply_open_zht")
	@ApiOperation(value = "申请开通中华通", notes = "申请开通中华通")
	public R applyOpenZht() {
		return bpmAccountService.applyOpenZht();
	}


}
