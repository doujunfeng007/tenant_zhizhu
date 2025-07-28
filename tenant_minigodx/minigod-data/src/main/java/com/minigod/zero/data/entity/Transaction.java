package com.minigod.zero.data.entity;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易实体类
 *
 * @author eric
 * @date 2024-03-19
 */
@Data
public class Transaction {
	/** 标识 */
	private Integer id;

	/** 归集标识 */
	private Integer nomineeTransId;

	/** 订单标识 */
	private Integer orderId;

	/** 子账号 */
	private String subAccountId;

	/** 类型 */
	private Integer type;

	/** 方式,1:金额;2:数量;3:比例 */
	private Integer mode;

	/** 基金代码 */
	private String fundCode;

	/** 币种 */
	private String currency;

	/** 金额 */
	private BigDecimal amount;

	/** 数量 */
	private BigDecimal quantity;

	/** 价格 */
	private BigDecimal price;

	/** 交易日期 */
	private LocalDateTime transactionDate;

	/** 确认日期 */
	private LocalDateTime confirmedDate;

	/** 预计清算日期 */
	private LocalDateTime expectedSettledDate;

	/** 清算日期 */
	private LocalDateTime settledDate;

	/** 有效清算日期 */
	private LocalDateTime effectiveSettledDate;

	/** 清算金额 */
	private BigDecimal settledAmount;

	/** 活利得卖出、债券易买入、债券易卖出利息 */
	private BigDecimal gainLoss;

	/** 费用 */
	private BigDecimal fee;

	/** 交易来源：Client;CorporateAction;ManualAdjustment;Admin */
	private String source;

	/** 位置：Custody;yfund */
	private String location;

	/** 备注 */
	private String remark;

	/** 状态代码,700:CONFIRMED;900:SETTLED */
	private Integer status;

	/** 创建时间 */
	private LocalDateTime createTime;

	/** 更新时间 */
	private LocalDateTime updateTime;
}
