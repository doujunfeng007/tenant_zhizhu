package com.minigod.zero.manage.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 渠道礼包
 *
 * @author eric
 * @date 2024-12-23 14:52:08
 * @TableName sn_channel_gift
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sn_channel_gift")
public class SnChannelGiftEntity extends TenantEntity {

	/**
	 * 渠道ID
	 */
	private String channelId;

	/**
	 * 礼包名称
	 */
	private String giftName;

	/**
	 * 渠道密码
	 */
	private String channelPwd;

	/**
	 * 库存数量
	 */
	private Integer remainCount;

	/**
	 * 操作人员ID
	 */
	private Integer oprId;

	/**
	 * 操作人员
	 */
	private String oprName;

	/**
	 * 介绍
	 */
	private String introduce;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 礼包奖品方案
	 */
	private Long planId;

	/**
	 * 是否启用入金奖励0:不启用 1:启用
	 */
	private Boolean rewardOpen;

	/**
	 * 是否启用赠股0:不启用 1:启用
	 */
	private Boolean handselStock;

	/**
	 * 邀请奖励是否发放
	 */
	private Boolean inviteReward;
}
