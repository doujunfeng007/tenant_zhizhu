package com.minigod.zero.customer.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户债卷持仓表
 * @TableName customer_bond_holding_records
 */
@Data
public class CustomerBondHoldingRecords implements Serializable {
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
     * 基金代码
     */
    private String bondCode;

    /**
     * 基金名称
     */
    private String bondName;

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

}
