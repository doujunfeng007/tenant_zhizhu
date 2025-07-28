package com.minigod.zero.manage.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.manage.entity.OperateActRewardPlanEntity;
import com.minigod.zero.manage.vo.RuleRewardVO;

import java.util.List;

/**
 * 运营活动奖励方案维护服务接口
 *
 * @author eric
 * @since 2024-12-25 10:36:08
 */
public interface IOperateActRewardPlanService extends BaseService<OperateActRewardPlanEntity> {

	/**
	 * 查询运营活动奖励方案列表
	 *
	 * @param planId
	 * @param funType
	 * @return
	 */
	List<OperateActRewardPlanEntity> findOperateActRewardPlanList(Long planId, Integer funType);

	/**
	 * 根据方案ID和规则类型获取规则列表
	 * @param planId
	 * @param funType
	 * @return
	 */
	List<RuleRewardVO> getRuleRewardListByPlan(Long planId, Integer funType);

	/**
	 * 保存或更新运营活动奖励方案
	 *
	 * @param operateActRewardPlan
	 * @return
	 */
	OperateActRewardPlanEntity saveOrUpdateOperateActRewardPlan(OperateActRewardPlanEntity operateActRewardPlan);

	/**
	 * 删除运营活动奖励方案
	 *
	 * @param planId
	 * @param funType
	 */
	int deleteOperateActRewardPlan(Long planId, Integer funType);
}
