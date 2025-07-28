package com.minigod.zero.bpm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpm.entity.AcctRiskQuestionEntity;
import com.minigod.zero.bpm.vo.AcctRiskQuestionVO;
import com.minigod.zero.bpm.vo.request.RiskEvaluationReqVo;
import com.minigod.zero.bpm.vo.response.FundRiskQuestionVo;
import com.minigod.zero.core.tool.api.R;

import java.util.List;

/**
 * 风险评测题库 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface IAcctRiskQuestionService extends IService<AcctRiskQuestionEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctRiskQuestion
	 * @return
	 */
	IPage<AcctRiskQuestionVO> selectAcctRiskQuestionPage(IPage<AcctRiskQuestionVO> page, AcctRiskQuestionVO acctRiskQuestion);

	/**
	 * 获取问题列表
	 * @param vo
	 * @return
	 */
	R<List<FundRiskQuestionVo>> findQuestion(RiskEvaluationReqVo vo);


}
