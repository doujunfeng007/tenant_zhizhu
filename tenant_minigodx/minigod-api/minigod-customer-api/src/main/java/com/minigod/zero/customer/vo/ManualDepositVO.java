package com.minigod.zero.customer.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @TableName manual_deposit_record
 */
@Data
public class ManualDepositVO implements Serializable {
    /**
     * 客户id
     */
    private Long custId;
    /**
     * 理财账号
     */
    private String accountId;
    /**
     * 客户姓名
     */
    private String custName;
    /**
     * 存入金额
     */
    private BigDecimal depositAmount;
    /**
     * 币种 1港币，2美元，3人民币
     */
    private Integer currency;
    /**
     * 存入时间
     */
    private Date createTime;
	/**
	 *入金密码
	 */
	private String password;
	/**
	 * 入金银行id
	 */
	private Long depositBankId;
	/**
	 * 汇款账号
	 */
	private String remittanceAccount;
	/**
	 * 汇款账号
	 */
	private String remittanceAccountName;
	/**
	 * 汇款银行机构号
	 */
	private String bankId;
	/**
	 * 租户id
	 */
	private String tenantId;
	/**
	 * 操作人
	 */
	private String createUserName;


    private static final long serialVersionUID = 1L;
}
