package com.minigod.zero.manage.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 奖品发放详情
 *
 * @author eric
 * @since 2024-12-23 18:38:18
 */
@Data
public class RewardGiveOutVO implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 用户id
	 */
	private Integer userId = 0;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 邀请人
	 */
	private Integer inviter = 0;
	/**
	 * 活动类别(1 开户 2 入金 3 转仓 4双旦活动 )
	 */
	private Integer activeType = 0;
	/**
	 * 活动id
	 */
	private Long snActiveConfigId = 0l;
	/**
	 * 活动名称
	 */
	private String activeName = "";
	/**
	 * 活动奖励明细id
	 */
	private Long snActiveConfigItemId = 0l;
	/**
	 * 活动配置项名称
	 */
	private String activeItemName;
	/**
	 * 开户Id
	 */
	private Long openAccountId = 0l;
	/**
	 * 开户交易号
	 */
	private String clientId = "0";
	/**
	 * 开户时间
	 */
	private Date openAccountDatetime;
	/**
	 * 入金Id
	 */
	private Long depositId = 0l;
	/**
	 * 入金金额
	 */
	private Integer depositMoney = 0;
	/**
	 * 入金时间
	 */
	private Date depositDatetime;
	/**
	 * 转仓id
	 */
	private Long transferStockId = 0l;
	/**
	 * 转仓金额
	 */
	private Integer transferMoney = 0;
	/**
	 * 转仓时间
	 */
	private Date transferStockDateTime;
	/**
	 * 奖励金额
	 */
	private Integer rewardMoney = 0;
	/**
	 * 免佣类型
	 */
	private Integer commissionType = 0;
	/**
	 * 奖励免用天数
	 */
	private Integer commissionDay = 0;
	/**
	 * 奖励类型（1.免佣 2.现金 3.行情 4.增股 5.积分）
	 */
	private Integer rewardType = 0;
	/**
	 * 奖励状态（-1已过期 0 未领取 1已领取 2 申请中 3 退回 4 使用中 5已完成 6免佣渠道拒绝 ）
	 */
	private Integer rewardStatus = 0;
	/**
	 * 奖励领取截至时间
	 */
	private Date confirmEndDatetime;
	/**
	 * 领取奖励时间
	 */
	private Date confirmDatetime;
	/**
	 * 提现Id
	 */
	private Long cashDrawId = 0l;
	/**
	 * 奖励使用时间
	 */
	private Date useDatetime;
	/**
	 * 记录状态 0有效 1失效
	 */
	private Integer STATUS = 0;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 奖励过期时间
	 */
	private Date expireDatetime;
	/**
	 * 来源类型 1:活动 2:运营发放
	 */
	private Integer sourceType = 1;
	/**
	 * 行情类型产品ID
	 */
	private Long packageId;
	/**
	 * 操作人员ID
	 */
	private Integer oprId;
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
	private Boolean invalidRemind = false;
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
	 * 卡券类型 0=普通卡券，1=商务渠道活动入金奖励卡券,2=金豆增股卡券,3=金豆最大现金红包
	 */
	private Integer cardType = 0;
	/**
	 * 卡券状态 0=未激活，1=已激活，2=已失效
	 */
	private Integer cardStatus = 1;
	/**
	 * 交易状态 0=未交易，1=已交易
	 */
	private Integer tradeStatus = 0;
	/**
	 * 交易时间
	 */
	private Date tradeTime;
	/**
	 * 免佣开始时间
	 */
	private Date commissionStartTime;
	/**
	 * 免佣结束时间
	 */
	private Date commissionEndTime;
	/**
	 * 实际免佣开始时间
	 */
	private String actualCommissionStartTime;
	/**
	 * 实际免佣结束时间
	 */
	private String actualCommissionEndTime;
	/**
	 * 抵扣券实际抵扣金额
	 */
	private String actualFreeAmount;
	/**
	 * 渠道
	 */
	private String channel;
	/**
	 * 奖品有效期
	 */
	private Integer rewardValidDay;
	/**
	 * 赠股留存天数
	 */
	private Integer stocksOpenDay;
}
