package com.minigod.zero.manage.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 运营活动奖励方案表
 *
 * @author eric
 * @date 2024-12-23 16:10:08
 * @TableName operate_act_reward_plan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("operate_act_reward_plan")
public class OperateActRewardPlanEntity extends TenantEntity {

	/**
	 * 功能类型 1:开户 2入金 3邀请
	 */
	private Integer funType;

	/**
	 * 方案ID
	 */
	private Long planId;

	/**
	 * 起始金额
	 */
	private BigDecimal startAmount;

	/**
	 * 结束金额
	 */
	private BigDecimal endAmount;

	/**
	 * 现金奖励金额
	 */
	private BigDecimal cashAmount;

	/**
	 * 免费天数
	 */
	private Integer freeDays;

	/**
	 * 奖励条件
	 */
	private String rewardCondition;
}
