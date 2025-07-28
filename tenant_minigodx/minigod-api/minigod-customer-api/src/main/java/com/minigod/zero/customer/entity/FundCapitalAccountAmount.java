package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 基金资金账号账户金额
 * @TableName fund_capital_account_amount
 */
@Data
public class FundCapitalAccountAmount implements Serializable {
    /**
     *
     */
	@TableId(
		value = "id",
		type = IdType.AUTO
	)
    private Long id;

    /**
     * 资金账号
     */
    private String fundAccount;

    /**
     * 可用金额
     */
    private BigDecimal availableAmount;

    /**
     * 冻结金额
     */
    private BigDecimal freezeAmount;

    /**
     * 在途金额
     */
    private BigDecimal transitedAmount;

    /**
     * 币种
     */
    private String currency;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
