package com.minigod.zero.bpmn.module.account.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionOptionDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskQuestionOptionEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionOptionVO;
import com.minigod.zero.bpmn.module.account.vo.FundRiskQuestionOptionVO;
import com.minigod.zero.core.tool.api.R;

import java.util.List;

/**
 * 风险测评问题选项服务接口
 *
 * @author eric
 * @since 2024-08-20 14:36:08
 */
public interface IAccountRiskQuestionOptionService extends IService<AccountRiskQuestionOptionEntity> {

	/**
	 * 风险测评问题选项列表分页查询
	 *
	 * @param page
	 * @param acctRiskQuestionOption
	 * @return
	 */
	IPage<AccountRiskQuestionOptionVO> selectAcctRiskQuestionOptionPage(IPage<AccountRiskQuestionOptionVO> page, AccountRiskQuestionOptionDTO acctRiskQuestionOption);

	/**
	 * 获取指定条件的风险测评问题选项列表
	 *
	 * @param questionId
	 * @return
	 */
	List<AccountRiskQuestionOptionVO> selectAcctRiskQuestionOptionList(Long questionId);

	/**
	 * 获取指定条件的风险测评问题选项
	 *
	 * @param questionId
	 * @param optionId
	 * @return
	 */
	FundRiskQuestionOptionVO getQuestionOption(Long questionId, Integer optionId);

	/**
	 * 添加风险测评问题选项
	 *
	 * @param acctRiskQuestionOption
	 * @return
	 */
	R addQuestionOption(AccountRiskQuestionOptionDTO acctRiskQuestionOption);

	/**
	 * 批量添加风险测评问题选项
	 *
	 * @param acctRiskQuestionOptions
	 * @return
	 */
	R batchAddQuestionOption(List<AccountRiskQuestionOptionDTO> acctRiskQuestionOptions);

	/**
	 * 删除风险测评问题选项
	 *
	 * @param ids
	 * @return
	 */
	R deleteQuestionOption(List<Long> ids);

}
