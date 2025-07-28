package com.minigod.zero.trade.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * IPO个股基本信息
 * @author sunline
 *
 */

@Data
public class IPOInfo implements Serializable{
    /**
     * 资产ID
     */
    private String assetId;
    /**
     * char[40] 证券名称
     */
    private String stkName;
    /**
     * int 扣款日
     */
//	private Date closeDate;
    /**
     * int 返款日
     */
//	private Date depositDate;
    /**
     * char 币种代码
     */
    private String currency;
    /**
     * double 最终申购价
     */
    private double finalPrice;
    /**
     * double 证监会交易征费
     */
    private double tranLevy;
    /**
     * double 联交所交易征费
     */
    private double levy;
    /**
     * double 佣金
     */
    private double commission;
    /**
     * double “默认融资“比例
     */
    private double depositRate;
    /**
     * double IPO利率
     */
    private double interestRate;
    /**
     * double 手续费
     */
    private double handlingCharge;
    /**
     * char[255] 招股书连接
     */
    private String link;
    /**
     * char[255] 手册连接
     */
    private String handbook;
    /**
     * int 交易日
     */
//	private Date tradingDate;
    /**
     * int 开始申购日
     */
    private Date applicationBegins;
    /**
     * char[16] 柜台申购截止日
     */
    private Date cutofftime;
    /**
     * char[16] EIPO现金申购截止日
     */
    private Date internetCutofftime;

    /**
     * double 公司融资额度
     */
    private double compFinancingAmount;
    /**
     * double 公司融资额度净余
     */
    private double compFinancingSurplus;
    /**
     * double 融资申购手续费
     */
    private double applicationfeeSf;
    /**
     * double 最小融资数量
     */
    private double minamountSf;
    /**
     * double 最大融资数量
     */
    private double maxamountSf;
    /**
     * int 融资开始日期
     */
    private Date datebeginsSf;
    /**
     * int 融资结束日期
     */
    private Date dateendsSf;
    /**
     * char[16] EIPO融资申购截止日期
     */
    private Date financingCutofftime;

	/**
	 * 融资天数 (柜台有就直接计算 没有就融资结束时间-开始时间+1)
	 */
	private int interestBearingDays;
}
