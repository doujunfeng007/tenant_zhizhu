package com.minigod.zero.customer.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户债券易资产历史记录
 * @TableName customer_bond_assets_history
 */
@Data
public class CustomerBondAssetsHistory implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 用户id
     */
    private Long custId;

    /**
     * 理财账号
     */
    private String accountId;

    /**
     * 港币市值
     */
    private BigDecimal hkdMarketValue = BigDecimal.ZERO;

    /**
     * 人民币市值
     */
    private BigDecimal cnyMarketValue = BigDecimal.ZERO;

    /**
     * 美元市值
     */
    private BigDecimal usdMarketValue = BigDecimal.ZERO;

    /**
     * 统计时间
     */
    private String statisticalTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     *
     */
    private Integer isDeleted;

    /**
     * 港币收益
     */
    private BigDecimal hkdIncome = BigDecimal.ZERO;

    /**
     * 人民币收益
     */
    private BigDecimal cnyIncome = BigDecimal.ZERO;

    /**
     * 美元收益
     */
    private BigDecimal usdIncome = BigDecimal.ZERO;

    /**
     * 换汇后港币市值
     */
    private BigDecimal targetHkdMarketValue = BigDecimal.ZERO;

    /**
     * 换汇后人民币市值
     */
    private BigDecimal targetCnyMarketValue = BigDecimal.ZERO;

    /**
     * 换会后美金市值
     */
    private BigDecimal targetUsdMarketValue = BigDecimal.ZERO;

    /**
     * 换会后港币收益
     */
    private BigDecimal targetHkdIncome = BigDecimal.ZERO;

    /**
     * 换汇后人民币收益
     */
    private BigDecimal targetCnyIncome = BigDecimal.ZERO;

    /**
     * 换会后美金收益
     */
    private BigDecimal targetUsdIncome = BigDecimal.ZERO;

    private static final long serialVersionUID = 1L;

    public CustomerBondAssetsHistory(){

	}

	public CustomerBondAssetsHistory(Long custId,String accountId,String date){
		this.createTime = new Date();
		this.custId = custId;
		this.accountId = accountId;
		this.statisticalTime = date;
	}
}
