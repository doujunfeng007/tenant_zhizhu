package com.minigod.zero.manage.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


/**
 * 优惠券管理
 *
 * @author eric
 * @date 2024-12-26 09:20:08
 * @TableName sn_coupon_manage
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sn_coupon_manage")
public class SnCouponManageEntity extends TenantEntity {

	/**
	 * 卡券类型
	 */
	private Integer cardType;

	/**
	 * 卡券名称
	 */
	private String cardName;

	/**
	 * 卡券ID
	 */
	private Long cardId;

	/**
	 * 使用条件 1首次开户 2首次入金 3首次转仓 4入金金额 5首次打新
	 */
	private Integer useType;

	/**
	 * 资产条件
	 */
	private Double amount;

	/**
	 * 过期时间
	 */
	private LocalDateTime expiredTime;

	/**
	 * 渠道
	 */
	private String channelId;

	/**
	 * 创建数量
	 */
	private Integer createNumber;

	/**
	 * 使用有效期(天)
	 */
	private Integer useValidityDay;

	/**
	 * 条件描述
	 */
	private String rewardCondition;

	/**
	 * 兑换上限(次)
	 */
	private Integer exchangeMaxNumber;

	/**
	 * 短信推送 0否 1是
	 */
	private Integer sendMobileMsg;

	/**
	 * 短信通知文案
	 */
	private String sendMobileMsgContent;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建人
	 */
	private String oprName;

	/**
	 * 兑换码
	 */
	private String code;

	/**
	 * 是否使用(0:未使用 1:已使用)
	 */
	private Integer useStatus;
}
