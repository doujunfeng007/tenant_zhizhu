package com.minigod.zero.manage.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 兑换码匹配表
 *
 * @author eric
 * @date 2024-12-23 17:10:08
 * @TableName sn_coupon_manage_match
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sn_coupon_manage_match")
public class SnCouponManageMatchEntity extends TenantEntity {

	/**
	 * 序列号
	 */
	private String serialNum;

	/**
	 * 激活码
	 */
	private String code;

	/**
	 * 犇犇号
	 */
	private Integer userId;

	/**
	 * 渠道id
	 */
	private String channelId;

	/**
	 * 激活时间
	 */
	private LocalDateTime recordDate;

	/**
	 * 使用时间
	 */
	private LocalDateTime useDate;

	/**
	 * 开户时间
	 */
	private LocalDateTime openDate;

	/**
	 * 入金时间
	 */
	private LocalDateTime depositDate;

	/**
	 * 入金金额
	 */
	private BigDecimal depositMoney;
}
