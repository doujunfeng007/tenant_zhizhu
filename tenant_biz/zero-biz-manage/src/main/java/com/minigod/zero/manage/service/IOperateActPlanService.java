package com.minigod.zero.manage.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.manage.entity.OperateActPlanEntity;

import java.util.List;

/**
 * 运营活动方案维护服务接口
 *
 * @author eric
 * @since 2024-12-25 10:12:18
 */
public interface IOperateActPlanService extends BaseService<OperateActPlanEntity> {
	/**
	 * 保存或更新运营活动方案
	 *
	 * @param operateActPlan
	 * @return
	 */
	OperateActPlanEntity saveOrUpdateOperateActPlan(OperateActPlanEntity operateActPlan);

	/**
	 * 根据状态查询运营活动方案列表
	 *
	 * @param status
	 * @return
	 */
	List<OperateActPlanEntity> getPlanLists(Integer status);
}
