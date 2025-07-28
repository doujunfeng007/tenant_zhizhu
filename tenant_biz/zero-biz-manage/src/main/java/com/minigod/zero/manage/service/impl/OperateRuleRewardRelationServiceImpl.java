package com.minigod.zero.manage.service.impl;

import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.manage.entity.OperateRuleRewardRelationEntity;
import com.minigod.zero.manage.mapper.OperateRuleRewardRelationMapper;
import com.minigod.zero.manage.service.IOperateRuleRewardRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 运营规则奖励关联关系维护服务接口实现
 *
 * @author eric
 * @since 2024-12-25 09:25:08
 */
@Slf4j
@Service
public class OperateRuleRewardRelationServiceImpl extends BaseServiceImpl<OperateRuleRewardRelationMapper, OperateRuleRewardRelationEntity> implements IOperateRuleRewardRelationService {
	private final OperateRuleRewardRelationMapper operateRuleRewardRelationMapper;

	public OperateRuleRewardRelationServiceImpl(OperateRuleRewardRelationMapper operateRuleRewardRelationMapper) {
		this.operateRuleRewardRelationMapper = operateRuleRewardRelationMapper;
	}

	/**
	 * 保存运营规则奖励关联关系
	 *
	 * @param operateRuleRewardRelations
	 */
	@Override
	public void saveOperateRuleRewardRelation(List<OperateRuleRewardRelationEntity> operateRuleRewardRelations) {
		boolean result = this.saveBatch(operateRuleRewardRelations, DEFAULT_BATCH_SIZE);
		if (!result) {
			log.error("保存运营规则奖励关联关系失败!");
		} else {
			log.info("保存运营规则奖励关联关系成功!");
		}
	}

	/**
	 * 删除规则奖励关联关系
	 *
	 * @param ruleIds
	 */
	@Override
	public int deleteOperateRuleRewardRelation(Long[] ruleIds) {
		return operateRuleRewardRelationMapper.deleteOperateRuleRewardRelation(ruleIds);
	}
}
