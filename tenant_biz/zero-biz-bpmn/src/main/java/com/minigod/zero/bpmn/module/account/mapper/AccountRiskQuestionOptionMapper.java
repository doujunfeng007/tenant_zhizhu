package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionOptionDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskQuestionOptionEntity;
import com.minigod.zero.bpmn.module.account.vo.FundRiskQuestionOptionVO;

import java.util.List;

/**
 * 风险评测题库选项接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface AccountRiskQuestionOptionMapper extends BaseMapper<AccountRiskQuestionOptionEntity> {

	/**
	 * 风险评测题库选项分页列表
	 *
	 * @param page
	 * @param acctRiskQuestionOption
	 * @return
	 */
	List<AccountRiskQuestionOptionEntity> selectAcctRiskQuestionOptionPage(IPage page, AccountRiskQuestionOptionDTO acctRiskQuestionOption);

	/**
	 * 根据问题ID,查询风险评测题库选项
	 *
	 * @param questionId
	 * @return
	 */
	List<AccountRiskQuestionOptionEntity> selectAcctRiskQuestionOptionList(Long questionId);

	/**
	 * 根据问题ID和选项ID,查询风险评测题库选项
	 *
	 * @param questionId
	 * @param optionId
	 * @param lang
	 * @return
	 */
	FundRiskQuestionOptionVO getQuestionOption(Long questionId, Integer optionId, String lang);
}
