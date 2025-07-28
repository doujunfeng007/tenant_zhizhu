package com.minigod.zero.bpm.proxy;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpm.service.IAcctRiskEvaluationService;
import com.minigod.zero.bpm.service.IAcctRiskQuestionOptionService;
import com.minigod.zero.bpm.service.IAcctRiskQuestionService;
import com.minigod.zero.bpm.entity.AcctRiskEvaluationEntity;
import com.minigod.zero.bpm.vo.request.RiskEvaluationReqVo;
import com.minigod.zero.bpm.vo.response.FundRiskQuestionOptionVo;
import com.minigod.zero.bpm.vo.response.FundRiskQuestionVo;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(AppConstant.PROXY_API_PREFIX + "/bpm_api")
@Api(value = "BPM后台接口", tags = "BPM后台接口")
@Slf4j
public class BpmApiProxy {

	@Resource
	private IAcctRiskEvaluationService riskEvaluationService;

	@Resource
	private IAcctRiskQuestionService riskQuestionService;

	@Resource
	private IAcctRiskQuestionOptionService riskQuestionOptionService;


	@PostMapping("/get_risk_evaluation")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取基金风险评测记录", notes = "获取基金风险评测记录")
	public R<AcctRiskEvaluationEntity> getRiskEvaluationInfo(@RequestBody RiskEvaluationReqVo vo) {
		if(null == vo ) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return riskEvaluationService.getRiskEvaluationInfo(vo);
	}


	@PostMapping("/reset_risk_evaluation")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "重置风险评测", notes = "重置风险评测")
	public R resetRiskEvaluation(@RequestBody RiskEvaluationReqVo vo) {
		if(null == vo ) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return riskEvaluationService.resetRiskEvaluation(vo);
	}


	@PostMapping("/find_question")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "获取问题列表", notes = "获取问题列表")
	public R<List<FundRiskQuestionVo>> findQuestion(@RequestBody RiskEvaluationReqVo vo) {
		if(null == vo ) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return riskQuestionService.findQuestion(vo);
	}


	@PostMapping("/find_question_option")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "获取问题选项", notes = "获取问题选项")
	public R<FundRiskQuestionOptionVo> getQuestionOption(@RequestBody RiskEvaluationReqVo vo) {
		if(null == vo ) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return riskQuestionOptionService.getQuestionOption(vo);
	}



}
