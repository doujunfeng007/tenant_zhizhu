package com.minigod.zero.biz.common.mkt.cache;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 新股日历 可认购列表信息
 */
@Data
public class ApplyCanVO extends ApplyCommonVO {

	/**
	 * 招股价上限
	 */
    private String priceCeiling; // 招股价上限
	/**
	 * 招股价下限
	 */
    private String priceFloor; // 招股价下限
	/**
	 * 最低入场金额
	 */
    private String entranceMin; // 最低入场金额
	/**
	 * 截止认购日
	 */
    private String endDate; // 截止认购日
	/**
	 * EIPO现金申购截止日
	 */
    private String internetCutofftime; // EIPO现金申购截止日
	/**
	 * 是否可融资   1,表示可以 0 表示不可以
	 */
    private Boolean isFinancingFlag; //是否可融资   1,表示可以 0 表示不可以
	/**
	 * 融资利率
	 */
    private double interestRate; //融资利率
	/**
	 * 剩余时间  时间戳
	 */
	private String remainTime;// 剩余时间  时间戳
	/**
	 * 剩余时间  数值
	 */
	private Integer dayLimit;// 剩余时间  数值
	/**
	 * 每手股数
	 */
    private Integer shares;//每手股数
	/**
	 * 招股说明书
	 */
    private String link; // 招股说明书
    private Integer recLevel;
	/**
	 * 更新时间
	 */
	private String updateTime; //更新时间 yyyy-MM-dd HH:mm:ss 格式
	/**
	 * 认购倍数
	 */
    private String subscribed; //认购倍数
	/**
	 * 认购状态 -1.无 0.去认购 1.已认购
	 */
    private Integer applyStatus; //认购状态 -1.无 0.去认购 1.已认购
	/**
	 * 结果公布日
	 */
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    private Date resultDate;//结果公布日
	/**
	 * 上市日
	 */
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    private Date listedDate;//上市日
	/**
	 * 认购开始日
	 */
    private String startBuyDate;//认购开始日
	/**
	 * 是否开启零本金认购：true-> 开启，false->关闭
	 */
    private boolean enableZeroPrincipal=false; // 是否开启零本金认购：true-> 开启，false->关闭
	/**
	 * 默认 “融资”比例
	 */
	private double depositRate = 0d; // 默认 “融资”比例
	/**
	 * 倍数
	 */
	private double multiple = 0d; // 倍数

	private Boolean isEnd = false;// 是否已截止

	private String quantityApply; // 认购股数

	private Integer type;
	private BigDecimal applyAmount;
}
