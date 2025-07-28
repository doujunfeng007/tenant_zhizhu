package com.minigod.zero.manage.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 奖励发放表
 *
 * @author eric
 * @date 2024-12-23 15:00:08
 * @TableName sn_active_reward_record
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sn_active_reward_record")
public class SnActiveRewardRecordEntity extends TenantEntity {

	/**
	 * 用户号
	 */
	private Integer userId;

	/**
	 * 用户昵称
	 */
	private String nickname;

	/**
	 * 邀请人
	 */
	private Integer inviter;

	/**
	 * 活动类别(1 开户 2 入金 3 转仓 4双旦活动)
	 */
	private Integer activeType;

	/**
	 * 活动id
	 */
	private Long snActiveConfigId;

	/**
	 * 活动名称
	 */
	private String activeName;

	/**
	 * 活动奖励明细id
	 */
	private Long snActiveConfigItemId;

	/**
	 * 活动配置项名称
	 */
	private String activeItemName;

	/**
	 * 开户Id
	 */
	private Long openAccountId;

	/**
	 * 开户交易号
	 */
	private String clientId;

	/**
	 * 开户时间
	 */
	private LocalDateTime openAccountDatetime;

	/**
	 * 入金Id
	 */
	private Long depositId;

	/**
	 * 入金金额
	 */
	private Integer depositMoney;

	/**
	 * 入金时间
	 */
	private LocalDateTime depositDatetime;

	/**
	 * 转仓id
	 */
	private Long transferStockId;

	/**
	 * 转仓金额
	 */
	private Integer transferMoney;

	/**
	 * 转仓时间
	 */
	private LocalDateTime transferStockDateTime;

	/**
	 * 奖励金额
	 */
	private BigDecimal rewardMoney;

	/**
	 * 免佣类型
	 */
	private Integer commissionType;

	/**
	 * 奖励免用天数
	 */
	private Integer commissionDay;

	/**
	 * 奖励类型（1 免佣 2 现金 3 行情 4增股 5积分）
	 */
	private Integer rewardType;

	/**
	 * 奖励状态（-1已过期 0 未领取 1已领取 2 申请中 3 退回 4 使用中 5已完成 6免佣渠道拒绝）
	 */
	private Integer rewardStatus;

	/**
	 * 奖励领取截至时间
	 */
	private LocalDateTime confirmEndDatetime;

	/**
	 * 领取奖励时间
	 */
	private LocalDateTime confirmDatetime;

	/**
	 * 提现Id
	 */
	private Long cashDrawId;

	/**
	 * 奖励使用时间
	 */
	private LocalDateTime useDatetime;

	/**
	 * 奖励过期时间
	 */
	private LocalDateTime expireDatetime;

	/**
	 * 来源类型 1:活动 2:运营发放
	 */
	private Integer sourceType;

	/**
	 * 行情类型产品ID
	 */
	private Long packageId;

	/**
	 * 操作人员ID
	 */
	private Long oprId;

	/**
	 * 操作人员名称
	 */
	private String oprName;

	/**
	 * 申请发放原因
	 */
	private String oprReason;

	/**
	 * 奖励方案
	 */
	private Integer planId;

	/**
	 * 资产ID
	 */
	private String assetId;

	/**
	 * 股数
	 */
	private Integer quantity;

	/**
	 * 总市值
	 */
	private BigDecimal totalCost;

	/**
	 * 到期提醒
	 */
	private Boolean invalidRemind;

	/**
	 * 奖励条件
	 */
	private String rewardCondition;

	/**
	 * 奖励积分
	 */
	private Integer rewardPoints;

	/**
	 * 优惠券兑换信息ID
	 */
	private Long exchangeCodeId;

	/**
	 * 卡券类型 0=普通卡券，1=商务渠道活动入金奖励卡券, 2=金豆增股卡券
	 */
	private Integer cardType;

	/**
	 * 卡券状态 0=未激活，1=已激活，2=已失效
	 */
	private Integer cardStatus;

	/**
	 * 交易状态 0=未交易，1=已交易
	 */
	private Integer tradeStatus;

	/**
	 * 交易时间
	 */
	private LocalDateTime tradeTime;

	/**
	 * 免佣开始时间
	 */
	private LocalDate commissionStartTime;

	/**
	 * 免佣结束时间
	 */
	private LocalDate commissionEndTime;

	/**
	 * 实际免佣开始时间
	 */
	private String actualCommissionStartTime;

	/**
	 * 实际免佣结束时间
	 */
	private String actualCommissionEndTime;

	/**
	 * 是否金豆最佳 0=否，1=是
	 */
	private Integer isGoldLuckiest;

	/**
	 * 抵扣券实际抵扣金额
	 */
	private String actualFreeAmount;

	/**
	 * 乐观锁版本号
	 */
	private Integer lockVersion;

	/**
	 * 入金,转仓中赠股的留存天数
	 */
	private Integer stocksOpenDay;

	/**
	 * 3天到期是否提醒
	 */
	private Boolean haveRemindForThreeDay;
}
