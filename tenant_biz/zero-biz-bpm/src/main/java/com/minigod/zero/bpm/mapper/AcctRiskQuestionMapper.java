package com.minigod.zero.bpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpm.entity.AcctRiskQuestionEntity;
import com.minigod.zero.bpm.vo.AcctRiskQuestionVO;
import com.minigod.zero.bpm.vo.request.RiskEvaluationReqVo;
import com.minigod.zero.bpm.vo.response.FundRiskQuestionVo;

import java.util.List;

/**
 * 风险评测题库 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface AcctRiskQuestionMapper extends BaseMapper<AcctRiskQuestionEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctRiskQuestion
	 * @return
	 */
	List<AcctRiskQuestionVO> selectAcctRiskQuestionPage(IPage page, AcctRiskQuestionVO acctRiskQuestion);


	List<FundRiskQuestionVo> findQuestion(RiskEvaluationReqVo vo);


}
