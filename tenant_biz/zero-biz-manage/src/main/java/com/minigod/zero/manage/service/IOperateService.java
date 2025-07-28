package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.OperateActPlanEntity;
import com.minigod.zero.manage.entity.OperateRuleRewardRelationEntity;
import com.minigod.zero.manage.vo.RewardRuleCommonVO;

import java.util.List;

/**
 * 奖励方案管理服务接口
 *
 * @author eric
 * @since 2024-12-24 16:56:51
 */
public interface IOperateService {
	/**
	 * 查询奖励方案列表
	 *
	 * @param page
	 * @param planId
	 * @param planName
	 * @return
	 */
	IPage<OperateActPlanEntity> findOperateActPlanPage(IPage page, Long planId, String planName);

	/**
	 * 查询奖励方案详情
	 *
	 * @param planId
	 * @return
	 */
	OperateActPlanEntity findOperateActPlan(Long planId);

	/**
	 * 根据奖励方案ID和功能类型查询奖励规则
	 *
	 * @param planId
	 * @param funType
	 * @return
	 */
	List<RewardRuleCommonVO> findRewardRule(Long planId, Integer funType);

	/**
	 * 保存存储奖励规则关联关系
	 *
	 * @param operateRuleRewardRelations
	 */
	void saveOperateRuleRewardRelation(List<OperateRuleRewardRelationEntity> operateRuleRewardRelations);

	/**
	 * 保存奖励方案
	 *
	 * @param operateActPlan
	 * @return
	 */
	OperateActPlanEntity saveOrUpdateOperateActPlan(OperateActPlanEntity operateActPlan);

}
