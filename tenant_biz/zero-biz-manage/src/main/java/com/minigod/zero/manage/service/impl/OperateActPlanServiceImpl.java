package com.minigod.zero.manage.service.impl;

import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.manage.entity.OperateActPlanEntity;
import com.minigod.zero.manage.mapper.OperateActPlanMapper;
import com.minigod.zero.manage.service.IOperateActPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 运营活动方案维护服务接口实现
 *
 * @author eric
 * @since 2024-12-25 10:12:18
 */
@Slf4j
@Service
public class OperateActPlanServiceImpl extends BaseServiceImpl<OperateActPlanMapper, OperateActPlanEntity> implements IOperateActPlanService {
	/**
	 * 保存或更新运营活动方案
	 *
	 * @param operateActPlan
	 * @return
	 */
	@Override
	public OperateActPlanEntity saveOrUpdateOperateActPlan(OperateActPlanEntity operateActPlan) {
		if (operateActPlan.getId() == null) {
			this.save(operateActPlan);
		} else {
			this.updateById(operateActPlan);
		}
		return operateActPlan;
	}

	/**
	 * 根据状态查询运营活动方案列表
	 *
	 * @param status
	 * @return
	 */
	@Override
	public List<OperateActPlanEntity> getPlanLists(Integer status) {
		return this.lambdaQuery().eq(OperateActPlanEntity::getStatus, status).list();
	}
}
