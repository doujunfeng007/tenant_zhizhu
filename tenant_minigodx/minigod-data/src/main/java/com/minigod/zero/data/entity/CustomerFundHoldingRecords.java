package com.minigod.zero.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 客户基金持仓表
 * @TableName customer_fund_holding_records
 */
@TableName(value ="customer_fund_holding_records")
@Data
public class CustomerFundHoldingRecords implements Serializable {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
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
    private String fundCode;

    /**
     * 基金名称
     */
    private String fundName;

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

    /**
     * 累计总收益
     */
    private BigDecimal totalGainLoss;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
