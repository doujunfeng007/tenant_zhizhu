package com.minigod.zero.bpmn.module.account.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskEvaluationDTO;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskEvaluationSubmitDTO;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionDTO;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionOptionDTO;
import com.minigod.zero.bpmn.module.account.service.IAccountRiskEvaluationService;
import com.minigod.zero.bpmn.module.account.service.IAccountRiskQuestionOptionService;
import com.minigod.zero.bpmn.module.account.service.IAccountRiskQuestionService;
import com.minigod.zero.bpmn.module.account.vo.*;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/open-api-risk")
@Api(value = "风险测评Open-API服务", tags = "风险测评Open-API服务")
public class AcctRiskOpenController {
	private final IAccountRiskEvaluationService riskEvaluationService;
	private final IAccountRiskQuestionService riskQuestionService;
	private final IAccountRiskQuestionOptionService riskQuestionOptionService;

	/**
	 * 获取当前用户风险评测记录
	 *
	 * @return
	 */
	@ApiLog(value = "获取当前用户风险评测记录")
	@PostMapping("/get_risk_evaluation")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取风险评测记录", notes = "获取风险评测记录")
	public R<AccountRiskEvaluationVO> getRiskEvaluationInfo() {
		return riskEvaluationService.getRiskEvaluationInfo();
	}

	/**
	 * 提交风险评测结果
	 *
	 * @param evaluation
	 * @return
	 */
	@ApiLog(value = "当前用户提交风险评测结果")
	@PostMapping("/save_risk_evaluation")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "提交风险评测结果", notes = "提交风险评测结果")
	public R saveRiskEvaluation(@RequestBody CommonReqVO<AccountRiskEvaluationSubmitDTO> evaluation) {
		if (null == evaluation || null == evaluation.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return riskEvaluationService.saveRiskEvaluation(evaluation.getParams());
	}

	/**
	 * 风险评测未完成结果保留
	 *
	 * @param commonReqVO
	 * @return
	 */
	@ApiLog(value = "当前用户风险评测未完成结果保留")
	@PostMapping("/save_evaluation_temp")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "风险评测未完成结果保留", notes = "风险评测未完成结果保留")
	public R saveEvaluationTemp(@RequestBody CommonReqVO<AccountRiskEvaluationSubmitDTO> commonReqVO) {
		if (null == commonReqVO || null == commonReqVO.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return riskEvaluationService.saveEvaluationTemp(commonReqVO.getParams());
	}

	/**
	 * 风险评测-未完成结果数据获取
	 *
	 * @return
	 */
	@ApiLog(value = "当前用户风险评测-未完成结果数据获取")
	@PostMapping("/evaluation_temp")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "风险评测-未完成结果数据获取", notes = "风险评测-未完成结果数据获取")
	public R<AccountRiskEvaluationTempVO> evaluationTemp() {
		return riskEvaluationService.evaluationTemp();
	}

	/**
	 * 重置风险评测
	 *
	 * @param accRiskEvaluation
	 * @return
	 */
	@ApiLog(value = "当前用户重置风险评测")
	@PostMapping("/reset_risk_evaluation")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "重置风险评测", notes = "重置风险评测")
	public R resetRiskEvaluation(@RequestBody AccountRiskEvaluationDTO accRiskEvaluation) {
		if (null == accRiskEvaluation) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return riskEvaluationService.resetRiskEvaluation(accRiskEvaluation);
	}

	/**
	 * 当前用户获取风险评测题库及题目选择项
	 *
	 * @param commonReqVO
	 * @return
	 */
	@ApiLog(value = "当前用户获取风险评测题库及题目选择项")
	@PostMapping("/risk_question")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "当前用户获取风险评测题库及题目选择项", notes = "当前用户获取风险评测题库及题目选择项")
	public R<List<AccountRiskQuestionAndOptionVO>> riskQuestion(@RequestBody CommonReqVO<AccountRiskQuestionDTO> commonReqVO) {

		if (null == commonReqVO || null == commonReqVO.getParams())
			return R.fail(ResultCode.PARAM_VALID_ERROR);
		return riskQuestionService.riskQuestion(commonReqVO.getParams());
	}

	/**
	 * 获取风险评测题目
	 *
	 * @param acctRiskQuestionDTO
	 * @return
	 */
	@ApiLog(value = "当前用户获取风险评测题目")
	@PostMapping("/find_question")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "获取问题列表", notes = "获取问题列表")
	public R<List<FundRiskQuestionVO>> findQuestion(@RequestBody AccountRiskQuestionDTO acctRiskQuestionDTO) {
		if (null == acctRiskQuestionDTO)
			return R.fail(ResultCode.PARAM_VALID_ERROR);
		return riskQuestionService.findQuestion(acctRiskQuestionDTO);
	}

	/**
	 * 获取问题选项
	 *
	 * @param acctRiskQuestionOptionDTO
	 * @return
	 */
	@ApiLog(value = "当前用户获取问题选项")
	@PostMapping("/find_question_option")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "获取问题选项", notes = "获取问题选项")
	public R<FundRiskQuestionOptionVO> getQuestionOption(@RequestBody AccountRiskQuestionOptionDTO acctRiskQuestionOptionDTO) {
		if (null == acctRiskQuestionOptionDTO || null == acctRiskQuestionOptionDTO.getQuestionId() || null == acctRiskQuestionOptionDTO.getOptionId())
			return R.fail(ResultCode.PARAM_VALID_ERROR);
		return R.data(riskQuestionOptionService.getQuestionOption(acctRiskQuestionOptionDTO.getQuestionId(), acctRiskQuestionOptionDTO.getOptionId()));
	}
}
