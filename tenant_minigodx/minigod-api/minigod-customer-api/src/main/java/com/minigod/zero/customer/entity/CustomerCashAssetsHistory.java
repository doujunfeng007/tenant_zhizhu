package com.minigod.zero.customer.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户现金资产历史记录
 * @TableName customer_cash_assets_history
 */
@Data
public class CustomerCashAssetsHistory implements Serializable {
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
     * 交易账号账号
     */
    private String tradeAccount;

    /**
     * 子账号账号
     */
    private String subAccount;

    /**
     * 港币总资产
     */
    private BigDecimal hkdAssets = BigDecimal.ZERO;

    /**
     * 人民币总资产
     */
    private BigDecimal cnyAssets = BigDecimal.ZERO;

    /**
     * 美元总资产
     */
    private BigDecimal usdAssets = BigDecimal.ZERO;

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

	private BigDecimal hkdAvailable;
	private BigDecimal hkdFreeze;
	private BigDecimal hkdIntransit;
	private BigDecimal usdAvailable;
	private BigDecimal usdFreeze;
	private BigDecimal usdIntransit;
	private BigDecimal cnyAvailable;
	private BigDecimal cnyFreeze;
	private BigDecimal cnyIntransit;


	/**
     * 换汇后港币总资产
     */
    private BigDecimal targetHkdAssets = BigDecimal.ZERO;

    /**
     * 换汇后人民币总资产
     */
    private BigDecimal targetCnyAssets = BigDecimal.ZERO;

    /**
     * 换会后美元总资产
     */
    private BigDecimal targetUsdAssets = BigDecimal.ZERO;

    private static final long serialVersionUID = 1L;

	public CustomerCashAssetsHistory(Long custId,String accountId,String date){
		this.custId = custId;
		this.accountId = accountId;
		this.statisticalTime = date;
		this.createTime = new Date();
	}

	public CustomerCashAssetsHistory(){

	}
}
