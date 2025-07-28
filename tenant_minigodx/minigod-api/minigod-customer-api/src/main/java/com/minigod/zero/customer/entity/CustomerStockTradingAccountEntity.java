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
 * 交易账户信息表 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("customer_stock_trading_account")
@ApiModel(value = "CustomerStockTransactionAccount对象", description = "交易账户信息表")
@EqualsAndHashCode(callSuper = true)
public class CustomerStockTradingAccountEntity extends BaseEntity {

    /**
     * 客户号（个人/授权人）
     */
    @ApiModelProperty(value = "客户号（个人/授权人）")
    private Long custId;
    /**
     * 交易账号
     */
    @ApiModelProperty(value = "交易账号")
    private String tradeAccount;
    /**
     * 账号类型：1-个人 2-联名 3-公司 4-ESOP
     */
    @ApiModelProperty(value = "账号类型：1-个人 2-联名 3-公司 4-ESOP")
    private Integer acctType;
    /**
     * 可取金额
     */
    @ApiModelProperty(value = "可取金额")
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
	 * 在途金额
	 */
	private BigDecimal transitedAmount;

}
