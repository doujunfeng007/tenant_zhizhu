package com.minigod.zero.manage.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 运营活动方案表
 *
 * @author eric
 * @date 2024-12-24 17:00:08
 * @TableName operate_act_plan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("operate_act_plan")
public class OperateActPlanEntity extends TenantEntity {

	/**
	 * 方案名称
	 */
	private String planName;

	/**
	 * 开户领取有效天数
	 */
	private Integer openValidDay;

	/**
	 * 入金领取有效天数
	 */
	private Integer depositValidDay;

	/**
	 * 转仓领取有效天数
	 */
	private Integer transferValidDay;

	/**
	 * 领取奖励有效天数
	 */
	private Integer rewardValidDay;

	/**
	 * 邀请开户奖励有效天数
	 */
	private Integer invOpenValidDay;

	/**
	 * 邀请入金奖励有效天数
	 */
	private Integer invDepositValidDay;

	/**
	 * 邀请转仓奖励有效天数
	 */
	private Integer invTransferValidDay;

	/**
	 * 是否启用商务渠道活动开户入金奖励，0=不启用 1=启用
	 */
	private Integer isBusinessReward;

	/**
	 * 是否启用转仓奖励，0=不启用 1=启用
	 */
	private Integer isTransferReward;
}
