package com.minigod.zero.bpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpm.entity.AcctRiskQuestionOptionEntity;
import com.minigod.zero.bpm.vo.AcctRiskQuestionOptionVO;
import com.minigod.zero.bpm.vo.request.RiskEvaluationReqVo;
import com.minigod.zero.bpm.vo.response.FundRiskQuestionOptionVo;

import java.util.List;

/**
 * 风险评测题库-选项 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface AcctRiskQuestionOptionMapper extends BaseMapper<AcctRiskQuestionOptionEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctRiskQuestionOption
	 * @return
	 */
	List<AcctRiskQuestionOptionVO> selectAcctRiskQuestionOptionPage(IPage page, AcctRiskQuestionOptionVO acctRiskQuestionOption);


	FundRiskQuestionOptionVo getQuestionOption(RiskEvaluationReqVo vo);
}
