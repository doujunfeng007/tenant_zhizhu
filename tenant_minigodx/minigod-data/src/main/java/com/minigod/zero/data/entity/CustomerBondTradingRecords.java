package com.minigod.zero.data.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户债券交易流水汇总表
 * @TableName customer_bond_trading_records
 */
@Data
public class CustomerBondTradingRecords implements Serializable {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 子账户id
     */
    private String subAccountId;

    /**
     * 交易日期(提交日期)
     */
    private Date submitDate;

    /**
     * 清算日期
     */
    private Date settledDate;

    /**
     * 确认日期
     */
    private Date confirmedDate;

    /**
     * 拒绝日期
     */
    private Date rejectedDate;

    /**
     * 取消日期
     */
    private Date canceledDate;

    /**
     * 基金代码
     */
    private String bondCode;

    /**
     * 基金名称
     */
    private String bondName;

    /**
     * 类型,1:买;2:卖;3:交换买;4:交换卖;
     */
    private Integer type;

    /**
     * 方式,1:金额;2:数量;3:权重比例;
     */
    private Integer mode;

    /**
     * 币种
     */
    private String currency;

    /**
     * 申请金额
     */
    private BigDecimal amount;

    /**
     * 成交数量
     */
    private BigDecimal businessAmount;

    /**
     * 成交金额
     */
    private BigDecimal businessBalance;

    /**
     * 成交价格
     */
    private BigDecimal businessPrice;

    /**
     * 佣金
     */
    private BigDecimal feeCount;

    /**
     * 交易流水号
     */
    private Integer sequenceNo;

    /**
     * 状态 ,100:PENDING;200:SUBMITTED;211:AUTHORIZED;221:POOLED;230:PLACED;270:CONFIRMED;300:SETTLED;400:REJECTED;500:CANCELED;600:FAILED;700:SUCCESS;
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusDesc;

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


	@ApiModelProperty(value="transaction_gain_loss活利得、债券易卖出应收利息，债券易买入应付利息")
	private BigDecimal transactionGainLoss;
}
