package com.minigod.zero.bpmn.module.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionnaireQuestionDTO;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionnaireQuestionSortDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskQuestionnaireQuestionEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionnaireAndQuestionVO;

import java.util.List;

/**
 * 风险测评问卷题目关联
 *
 * @author eric
 * @since 2024-08-30 20:31:08
 */
public interface IAccountRiskQuestionnaireQuestionService extends IService<AccountRiskQuestionnaireQuestionEntity> {
	/**
	 * 根据问卷ID查询绑定的问题列表数据
	 *
	 * @param id
	 * @param lang
	 * @return
	 */
	AccountRiskQuestionnaireAndQuestionVO getByQuestionnaireId(Long id, String lang);

	/**
	 * 添加绑定的问题数据
	 *
	 * @param dtos
	 * @return
	 */
	boolean addQuestionnaireQuestion(List<AccountRiskQuestionnaireQuestionDTO> dtos);

	/**
	 * 修改绑定的问题顺序
	 *
	 * @param dtos
	 * @return
	 */
	boolean changeSort(List<AccountRiskQuestionnaireQuestionSortDTO> dtos);

	/**
	 * 根据问卷ID删除绑定的问题数据
	 *
	 * @param questionnaireId
	 * @return
	 */
	boolean deleteByQuestionnaireId(Long questionnaireId);

	/**
	 * 根据问卷ID批量删除绑定的问题数据
	 *
	 * @param questionnaireIds
	 * @return
	 */
	boolean deleteByQuestionnaireIds(List<Long> questionnaireIds);

	/**
	 * 根据主键删除数据
	 *
	 * @param id
	 * @return
	 */
	boolean deleteById(Long id);

	/**
	 * 根据主键批量删除数据
	 *
	 * @param ids
	 * @return
	 */
	boolean deleteByIds(List<Long> ids);
}
