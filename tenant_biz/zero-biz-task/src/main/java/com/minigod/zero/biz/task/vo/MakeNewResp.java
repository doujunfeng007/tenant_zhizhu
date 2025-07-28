package com.minigod.zero.biz.task.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @author chen
 * @ClassName MakeNewResp.java
 * @Description TODO
 * @createTime 2022年08月08日 16:51:00
 */
@Data
public class MakeNewResp implements Serializable {

    private static final long serialVersionUID = -3258991797903656659L;

    /**
     * 入场费
     */
    private BigDecimal admissionFee;

    /**
     * 基石投资者名称
     */
    private String cornerstoneInvestor;

    /**
     * 行业代码
     */
    private String industryCode;

    /**
     * 	行业
     */
    private String industryName;

    /**
     * 最高招股价
     */
    private BigDecimal issuePriceC;

    /**
     * 最低招股价
     */
    private BigDecimal issuePriceF;

    /**
     * 上市倒计时
     */
    private String listedCountdown;

    /**
     * 	上市日期
     */
    private String listedDate;

    /**
     * 每手股数
     */
    private Integer oneLotShare;

    /**
     * 招股书
     */
    private String prospectus;

    /**
     * 公布配售结果日期
     */
    private String pubResultDate;

    /**
     * 保荐人名称
     */
    private String sponsor;

    /**
     * 股票名称
     */
    private String stockName;

    /**
     * 	认购倒计时
     */
    private String subCountdown;

    /**
     * 截止认购日期
     */
    private String subEndDate;


    /**
     * 认购倍数
     */
    private BigDecimal subMultiple;

    /**
     * 认购开始日期
     */
    private String subStartDate;

    /**
     * 认购热度
     */
    private String subsHot;

    /**
     * 证券代码
     */
    private String symbol;

    /**
     * 承销商名称
     */
    private String underwriter;

    private double interestRate; //融资利率

    private double multiple = 0d; // 倍数

    private int isFinancingFlag; //是否可融资   1,表示可以 0 表示不可以

    private int applyStatus; //认购状态 1.可撤回

    private String sysDate;

	/**
	 * 定价
	 */
    private String issuePrice;


}
