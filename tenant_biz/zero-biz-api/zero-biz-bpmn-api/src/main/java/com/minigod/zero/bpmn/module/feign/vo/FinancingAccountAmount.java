package com.minigod.zero.bpmn.module.feign.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 理财账号账户金额
 * @TableName financing_account_amount
 */
@Data
public class FinancingAccountAmount implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     * 理财账号
     */
    private String accountId;

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


	private BigDecimal totalAmount;

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
