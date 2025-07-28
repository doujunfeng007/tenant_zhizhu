package com.minigod.zero.manage.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 优惠券兑换码
 *
 * @author eric
 * @date 2024-12-23 16:40:08
 * @TableName sn_coupon_exchange_code
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sn_coupon_exchange_code")
public class SnCouponExchangeCodeEntity extends TenantEntity {

	/**
	 * 优惠券管理ID
	 */
	private Long manageId;

	/**
	 * 激活码
	 */
	private String code;

	/**
	 * 序列号
	 */
	private String serialNum;

	/**
	 * 使用犇犇号
	 */
	private Integer useUid;

	/**
	 * 首次入金
	 */
	private Double firstDepositFund;

	/**
	 * 手机号
	 */
	private String phoneNumber;

	/**
	 * 使用状态 0未使用 1已使用 2已过期 3已作废
	 */
	private Integer useStatus;

	/**
	 * 使用时间
	 */
	private LocalDateTime useDate;

	/**
	 * 兑换时间
	 */
	private LocalDateTime recordDate;

	/**
	 * 兑换状态 0未兑换 1已兑换
	 */
	private Integer recordStatus;

	/**
	 * 新增资产
	 */
	private Double amount;

	/**
	 * 奖励ID
	 */
	private Long rewardId;

	/**
	 * 过期时间
	 */
	private LocalDateTime expiredTime;

	/**
	 * 操作人
	 */
	private String oprName;

	/**
	 * 版本号
	 */
	@Version
	private Integer upVersion;
}
