package com.minigod.zero.bpmn.module.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionnaireRatingDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskQuestionnaireRatingEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionnaireRatingVO;

import java.util.List;

/**
 * 风险测评问卷风险等级关联服务接口
 *
 * @author eric
 * @since 2024-09-02 09:49:11
 */
public interface IAccountRiskQuestionnaireRatingService extends IService<AccountRiskQuestionnaireRatingEntity> {
	/**
	 * 根据主键删除问卷风险等级
	 *
	 * @param ids
	 * @return
	 */
	boolean deleteByIds(List<Long> ids);

	/**
	 * 根据主键ID查询风险测评问卷风险等级关联信息
	 *
	 * @param id
	 * @return
	 */
	AccountRiskQuestionnaireRatingVO getRiskQuestionnaireById(Long id);

	/**
	 * 根据问卷ID及分值查询风险测评问卷风险等级关联信息
	 */
	AccountRiskQuestionnaireRatingVO getRiskQuestionnaireByQuestionnaireIdAndScore(Long questionnaireId, Float score);

	/**
	 * 持久化风险测评问卷风险等级关联信息
	 *
	 * @param dto
	 * @return
	 */
	boolean save(AccountRiskQuestionnaireRatingDTO dto);

	/**
	 * 批量持久化风险测评问卷风险等级关联信息
	 *
	 * @param dtos
	 * @return
	 */
	boolean batchSave(List<AccountRiskQuestionnaireRatingDTO> dtos);

	/**
	 * 根据风险测评问卷ID删除该问卷的风险等级关联信息
	 *
	 * @param questionnaireId
	 * @return
	 */
	boolean deleteByQuestionnaireId(Long questionnaireId);

	/**
	 * 根据风险测评问卷ID查询该问卷的风险等级关联信息
	 *
	 * @param questionnaireId
	 * @return
	 */
	List<AccountRiskQuestionnaireRatingVO> getByQuestionnaireIds(Long questionnaireId);
}
