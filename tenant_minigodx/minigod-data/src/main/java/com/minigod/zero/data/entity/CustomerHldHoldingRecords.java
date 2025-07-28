package com.minigod.zero.data.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户活利得持仓表
 * @TableName customer_hld_holding_records
 */
@Data
public class CustomerHldHoldingRecords implements Serializable {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 持仓标识
     */
    private String holdingId;

    /**
     * 资金账号
     */
    private String subAccountId;

    /**
     * 活利得代码
     */
    private String hldCode;

    /**
     * 活利得名称
     */
    private String hldName;

    /**
     * 总份额
     */
    private BigDecimal totalQuantity;

    /**
     * 可用份额
     */
    private BigDecimal availableQuantity;

    /**
     * 冻结份额
     */
    private BigDecimal frozenQuantity;

    /**
     * 在途份额
     */
    private BigDecimal transitedQuantity;

    /**
     * 可取份额
     */
    private BigDecimal withdrawQuantity;

    /**
     * 平均成本
     */
    private BigDecimal averageCost;

    /**
     * 摊薄平均成本，包含赎回
     */
    private BigDecimal dilutedCost;

    /**
     * 货币类型
     */
    private String currency;

    /**
     * 总费用
     */
    private BigDecimal totalFee;

    /**
     * 以实现盈亏，即卖出获利
     */
    private BigDecimal realizedGainLoss;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     *
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;

	/**
	 * 息率
	 */
	private BigDecimal interestRate;
	/**
	 * 活利得累计总收益
	 */
	private BigDecimal totalGainLoss;
}
