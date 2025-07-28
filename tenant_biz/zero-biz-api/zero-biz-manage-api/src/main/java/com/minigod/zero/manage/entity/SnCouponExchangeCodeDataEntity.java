package com.minigod.zero.manage.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 优惠券兑换码数据统计
 *
 * @author eric
 * @date 2024-12-26 10:47:08
 * @TableName sn_coupon_exchange_code_data
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sn_coupon_exchange_code_data")
public class SnCouponExchangeCodeDataEntity extends TenantEntity {

	/**
	 * 渠道ID
	 */
	private String channelId;

	/**
	 * 优惠券管理ID
	 */
	private Long manageId;

	/**
	 * 兑换人数
	 */
	private Integer exchangePersonNumber;

	/**
	 * 使用人数
	 */
	private Integer usePersonNumber;

	/**
	 * 卡券类型
	 */
	private Integer cardType;

	/**
	 * 卡券名称
	 */
	private String cardName;

	/**
	 * 首次开户数
	 */
	private Integer firstOpenAccountNumber;

	/**
	 * 首次入金数
	 */
	private Integer firstDepositFundNumber;

	/**
	 * 首次入金金额
	 */
	private BigDecimal firstDepositFundAmount;

	/**
	 * 首次转仓数
	 */
	private Integer firstTransferNumber;

	/**
	 * 首次转仓金额
	 */
	private BigDecimal firstTransferAmount;
}
