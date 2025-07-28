package com.minigod.zero.manage.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.manage.entity.OperateRuleRewardRelationEntity;

import java.util.List;

/**
 * 运营规则奖励关联关系维护服务接口
 *
 * @author eric
 * @since 2024-12-25 09:25:08
 */
public interface IOperateRuleRewardRelationService extends BaseService<OperateRuleRewardRelationEntity> {
	/**
	 * 保存规则奖励关联关系
	 *
	 * @param operateRuleRewardRelation
	 */
	void saveOperateRuleRewardRelation(List<OperateRuleRewardRelationEntity> operateRuleRewardRelation);

	/**
	 * 删除规则奖励关联关系
	 *
	 * @param ruleIds
	 */
	int deleteOperateRuleRewardRelation(Long[] ruleIds);
}
