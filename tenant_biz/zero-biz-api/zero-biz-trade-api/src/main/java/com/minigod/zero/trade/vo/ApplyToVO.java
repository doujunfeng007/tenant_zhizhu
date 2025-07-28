package com.minigod.zero.trade.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @description: TODO
 * @author: sunline
 * @date: 2018/4/9 16:34
 * @version: v1.0
 */
@Data
public class ApplyToVO implements Serializable{
    private static final long serialVersionUID = 1L;

    private String enableBalance; // 可用资金
    private String ipoLoanBalance; // 计算垫资后可用资金（可用资金+卖出未交收资金），垫资申购时使用
    private boolean enableFinanceApply = false;// 是否允许融资认购
    private Integer financingCeiling; //融资上限
    private Integer vipFinancingCeiling; //融资上限
    private BigDecimal discount;
    private double enableFinanceMinimumAmount;// 可融资认购的最低综合购买力
    private boolean enableFinancingLot = false;// 是否允许融资认购一手
    private boolean enableZeroPrincipal = false; //是否允许0本金认购
    private String currency; // 币种
    private double oldHandlingCharge;// 原现金申购手续费
    private String handlingCharge; // 手续费
    private double depositRate; // 默认 “融资”比例
    private double interestRate; // IPO 利率
    private double oldApplicationfeeSf;// 原融资申购手续费
    private double applicationfeeSf; // 融资申购手续费
    private double minamountSf; // 最小融资数量
    private double maxamountSf; // 最大融资数量
    private String priceCeiling; // 招股价上限
    private String priceFloor; // 招股价下限
    private double multiple; // 倍数
    private String datebeginsSf; // 融资开始日期
    private String dateendsSf; // 融资结束日期
    private String internetCutofftime; // EIPO现金申购截止日
    private String financingCutofftime; // EIPO融资申购截止日期
    private String sysDate; // 系统时间
    private List<IPOLevel> levels; // 现金认购档位
    private Integer interestBearingDays; // 计息天数
    private boolean ipoQueue = false;
    private int rewardNum = 0; //可用卡券数量
    //ipo融资认购通知
    private String ipoMsg;

	private String issuePrice;//发行价区间
	// 剩余融资额度
	private double financingSurplus;

	/**
	 * double 公司融资额度
	 */
	private double compFinancingAmount;
	/**
	 * double 公司融资额度净余
	 */
	private double compFinancingSurplus;
}
