package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.lang.Boolean;
import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;

/**
 * 基金交易账户信息表 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("customer_fund_trading_account")
@ApiModel(value = "CustomerFundTransactionAccount对象", description = "基金交易账户信息表")
@EqualsAndHashCode(callSuper = true)
public class CustomerFundTradingAccountEntity extends BaseEntity {
    /**
     * 客户号（个人/授权人）
     */
    @ApiModelProperty(value = "客户号（个人/授权人）")
    private Long custId;
	/**
	 * 理财账号
	 */
	private String accountId;
    /**
     * 交易账户
     */
    @ApiModelProperty(value = "交易账户")
    private String tradeAccount;
    /**
     * 基金账户类型：0-个人户 1-机构户
     */
    @ApiModelProperty(value = "基金账户类型：1-个人 2-联名账户 3-机构账户")
    private Integer fundAccountType;
    /**
     * 基金账户权限：0-不可申购和赎回 1-可申购可赎回 2-可申购不可赎回 3-不可申购可赎回
     */
    @ApiModelProperty(value = "基金账户权限：0-不可申购和赎回 1-可申购可赎回 2-可申购不可赎回 3-不可申购可赎回")
    private Integer fundOperType;
    /**
     * 可用金额
     */
    @ApiModelProperty(value = "可用金额")
    private BigDecimal availableAmount;
    /**
     * 冻结金额
     */
    @ApiModelProperty(value = "冻结金额")
    private BigDecimal freezeAmount;
    /**
     * 币种
     */
    @ApiModelProperty(value = "币种")
    private String currency;
    /**
     * 基金账户状态：0-正常
     */
    @ApiModelProperty(value = "基金账户状态：0-正常")
    private Integer accountStatus;

	/**
	 * 在途金额
	 */
	private BigDecimal transitedAmount;
	/**
	 * 是否选中 1是，0否
	 */
	private int isCurrent;

	private Integer riskLevel;
}
