package com.minigod.zero.manage.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 运营规则奖励关系表
 *
 * @author eric
 * @date 2024-12-24 17:50:08
 * @TableName operate_rule_reward_relation
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("operate_rule_reward_relation")
public class OperateRuleRewardRelationEntity extends TenantEntity {

	/**
	 * 规则ID
	 */
	private Long ruleId;

	/**
	 * 奖品ID
	 */
	private Long rewardId;

	/**
	 * 领取方式 1:领其中一种 2:同时领取多种
	 */
	private Integer claimType;

	/**
	 * 奖品数量
	 */
	private Integer rewardNum;

	/**
	 * 奖品有效期,单位:天 0.代表此配置
	 */
	private Integer validityDay;

	/**
	 * 赠股多少天后可以使用(单位:天 默认:0)
	 */
	private Integer stocksOpenDay;
}
