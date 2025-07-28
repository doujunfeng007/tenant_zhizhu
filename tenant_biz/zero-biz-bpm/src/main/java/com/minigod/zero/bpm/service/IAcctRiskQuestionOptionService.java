package com.minigod.zero.bpm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpm.entity.AcctRiskQuestionOptionEntity;
import com.minigod.zero.bpm.vo.AcctRiskQuestionOptionVO;
import com.minigod.zero.bpm.vo.request.RiskEvaluationReqVo;
import com.minigod.zero.bpm.vo.response.FundRiskQuestionOptionVo;
import com.minigod.zero.core.tool.api.R;

/**
 * 风险评测题库-选项 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface IAcctRiskQuestionOptionService extends IService<AcctRiskQuestionOptionEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctRiskQuestionOption
	 * @return
	 */
	IPage<AcctRiskQuestionOptionVO> selectAcctRiskQuestionOptionPage(IPage<AcctRiskQuestionOptionVO> page, AcctRiskQuestionOptionVO acctRiskQuestionOption);



	R<FundRiskQuestionOptionVo> getQuestionOption(RiskEvaluationReqVo vo);

}
