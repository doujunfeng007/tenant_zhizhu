package com.minigod.zero.bpmn.module.account.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionnaireDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskQuestionnaireEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionnaireVO;

import java.util.List;

/**
 * 风险测评问卷
 *
 * @author eric
 * @since 2024-08-30 20:14:08
 */
public interface IAccountRiskQuestionnaireService extends IService<AccountRiskQuestionnaireEntity> {
	/**
	 * 风险测评问卷列表分页
	 *
	 * @param page
	 * @param riskQuestionnaireDTO
	 * @return
	 */
	IPage<AccountRiskQuestionnaireVO> selectAcctRiskQuestionnairePage(IPage<AccountRiskQuestionnaireVO> page, AccountRiskQuestionnaireDTO riskQuestionnaireDTO);

	/**
	 * 根据主键查询数据
	 *
	 * @param id
	 * @return
	 */
	AccountRiskQuestionnaireVO getAcctRiskQuestionnaireById(Long id);

	/**
	 * 新增风险测评问卷
	 *
	 * @param dto
	 * @return
	 */
	boolean save(AccountRiskQuestionnaireDTO dto);

	/**
	 * 根据主键删除风险测评问卷
	 *
	 * @param id
	 * @return
	 */
	boolean deleteById(Long id);

	/**
	 * 根据主键批量删除风险测评问卷
	 *
	 * @param ids
	 * @return
	 */
	boolean deleteByIds(List<Long> ids);

}
