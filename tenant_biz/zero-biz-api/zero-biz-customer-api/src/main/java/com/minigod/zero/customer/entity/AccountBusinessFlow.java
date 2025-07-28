package com.minigod.zero.customer.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @TableName account_business_Flow
 */
@Data
public class AccountBusinessFlow implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     * 客户id
     */
    private Long custId;

	private String flowId;

	private Integer operationType;

	private String remark;

    /**
     * 理财账号
     */
    private String accountId;
    /**
     * 调用方业务id
     */
    private String businessId;
    /**
     * 业务金额
     */
    private BigDecimal amount;
    /**
     * 币种
     */
    private String currency;
    /**
     * 业务时间
     */
    private Date createTime;
	/**
	 * {@link com.minigod.zero.customer.enums.PayStatus}
	 */
	private Integer status;
	/**
	 * 付款金额
	 */
	private BigDecimal payAmount;
	/**
	 * 退还金额
	 */
	private BigDecimal refundingAmount;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 跨币种时汇率
	 */
	private String exchangeRate;
	/**
	 * 业务类型
	 * {@link com.minigod.zero.customer.enums.BusinessType}
	 */
	private Integer businessType;

    private static final long serialVersionUID = 1L;

}
