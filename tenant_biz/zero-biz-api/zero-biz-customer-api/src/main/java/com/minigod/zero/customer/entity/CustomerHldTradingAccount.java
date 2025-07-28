package com.minigod.zero.customer.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 活利得交易账户信息表
 * @TableName customer_hld_trading_account
 */
@Data
public class CustomerHldTradingAccount implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 客户号（个人/授权人）
     */
    private Long custId;

	private String accountId;

    /**
     * 交易账户
     */
    private String tradeAccount;

    /**
     * 账户类型：账户类型：1-个人 2-联名账户 3-机构账户
     */
    private Integer accountType;

    /**
     * 账户权限：0-不可申购和赎回 1-可申购可赎回 2-可申购不可赎回 3-不可申购可赎回
     */
    private Integer operType;

    /**
     * 账户状态：0-正常
     */
    private Integer accountStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     *
     */
    private Integer isDeleted;

    /**
     * 是否当前选中1是，0不是
     */
    private Integer isCurrent;

	private Integer riskLevel;

    private static final long serialVersionUID = 1L;
}
