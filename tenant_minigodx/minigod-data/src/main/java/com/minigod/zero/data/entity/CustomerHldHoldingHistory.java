package com.minigod.zero.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 活利得持仓流水历史表(CustomerHldHoldingHistory)实体类
 *
 * @author makejava
 * @since 2024-05-23 22:29:09
 */
@Data
public class CustomerHldHoldingHistory implements Serializable{
    /**
    * 记录标志
    */
    private Integer id;
    /**
    * 基金账号
    */
	@TableField(value = "subAccountId")
    private String subAccountId;
    /**
    * 基金/现金id
    */
	@TableField(value = "fundCode")
    private String fundCode;
    /**
    * 持仓类型，1：fund；2：cash；3：icp
    */
	@TableField(value = "holdingType")
    private Integer holdingType;
	@TableField(value = "totalQuantity")
    private BigDecimal totalQuantity;
	@TableField(value = "availableQuantity")
    private BigDecimal availableQuantity;
	@TableField(value = "frozenQuantity")
    private BigDecimal frozenQuantity;
	@TableField(value = "transitedQuantity")
    private BigDecimal transitedQuantity;
	@TableField(value = "withdrawQuantity")
    private BigDecimal withdrawQuantity;
	@TableField(value = "averageCost")
    private BigDecimal averageCost;
	@TableField(value = "dilutedCost")
    private BigDecimal dilutedCost;
    /**
    * 货币类型
    */
	@TableField(value = "currency")
    private String currency;
	@TableField(value = "accumulatedBuyAmount")
    private BigDecimal accumulatedBuyAmount;
	@TableField(value = "accumulatedCashDividends")
    private BigDecimal accumulatedCashDividends;
	@TableField(value = "realizedGainLoss")
    private BigDecimal realizedGainLoss;
    /**
    * 活利得累计总收益
    */
	@TableField(value = "totalGainLoss")
    private BigDecimal totalGainLoss;
	@TableField(value = "totalFee")
    private BigDecimal totalFee;
    /**
    * 对应flow记录的主键，如果主键有多个字段，需要扩充
    */
	@TableField(value = "flowId")
    private Integer flowId;
	@TableField(value = "transactionTime")
    private Date transactionTime;
    /**
    * 创建时间
    */
	@TableField(value = "createTime")
    private Date createTime;
    /**
    * 修改时间
    */
	@TableField(value = "updateTime")
    private Date updateTime;
}
