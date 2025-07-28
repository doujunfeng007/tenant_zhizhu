package com.minigod.zero.customer.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @TableName manual_deposit_record
 */
@Data
public class ManualDepositRecord implements Serializable {
    /**
     *
     */
    private Integer id;
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
     * 币种
     */
    private String currency;
    /**
     * 存入时间
     */
    private Date createTime;
    /**
     * 操作人员姓名
     */
    private String createUserName;
    /**
     * 操作人员id
     */
    private Long createUserId;
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
	private Integer bankType;
	private String bankName;


    private static final long serialVersionUID = 1L;
}
