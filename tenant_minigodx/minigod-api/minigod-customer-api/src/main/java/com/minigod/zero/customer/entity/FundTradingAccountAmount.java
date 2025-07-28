package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 基金交易账号账户金额
 * @TableName fund_trading_account_amount
 */
@Data
public class FundTradingAccountAmount implements Serializable {
    /**
     *
     */
	@TableId(
		value = "id",
		type = IdType.AUTO
	)
    private Long id;

    /**
     * 交易账号
     */
    private String tradingAccount;

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
