package com.minigod.zero.biz.common.vo.mkt;

import com.minigod.zero.biz.common.mkt.cache.ApplyCanVO;
import lombok.Data;

/**
 * 新股日历 新股详情
 */

@Data
public class ApplyStockDetailVO extends ApplyCanVO {

	/**
	 * 每首股数
	 */
	private String lotSize; // 每首股数
	/**
	 * 招股开始日期
	 */
	private String offeringStartDate; // 招股开始日期
	/**
	 * 招股结束日期
	 */
	private String offeringEndDate; // 招股结束日期
	/**
	 * 上市日期
	 */
	private String listingDate; // 上市日期
	/**
	 * 行业
	 */
	private String industry; // 行业
	/**
	 * 保荐人
	 */
	private String sponsor; // 保荐人
	/**
	 * 招股总数
	 */
	private String totalOffering; // 招股总数
	/**
	 * 公开发行
	 */
	private String openIssuePrice; // 公开发行
	/**
	 * 募集资金
	 */
	private String raiseCapital; // 募集资金
	/**
	 * 募集用途
	 */
	private String raiseUse; // 募集用途
	/**
	 * 上市板块
	 */
	private String sector; // 上市板块
	/**
	 * 招股说明书
	 */
	private String link;
	/**
	 * EIPO现金申购截止日
	 */
	private String internetCutofftime; // EIPO现金申购截止日
	/**
	 * 融资开始日期
	 */
	private String datebeginsSf; // 融资开始日期
	/**
	 * 融资结束日期
	 */
	private String dateendsSf; // 融资结束日期
	/**
	 * EIPO融资申购截止日期
	 */
	private String financingCutofftime; // EIPO融资申购截止日期
	/**
	 * 默认 “融资”比例
	 */
	private double depositRate; // 默认 “融资”比例
	/**
	 * 倍数
	 */
	private double multiple; // 倍数
	/**
	 * 是否支持融资 Y-是 N-否
	 */
	private String isFinancing = "N"; // 是否支持融资
	/**
	 * 公布中签日期
	 */
	private String publicationBidDate; // 公布中签日期
	/**
	 * 暗盘交易日期
	 */
	private String greyTradeDate; // 暗盘交易日期
	/**
	 * 暗盘交易开始时间
	 */
	private String greyTradeStartTime; // 暗盘交易开始时间
	/**
	 * 暗盘交易结束时间
	 */
	private String greyTradeEndTime; // 暗盘交易结束时间
	/**
	 * 市盈率
	 */
	private String pe; // 市盈率
	/**
	 * 市值
	 */
	private String marketcap; //市值
	/**
	 * 公司简介
	 */
	private String principalactivities; //公司简介
	/**
	 * 行业板块代码
	 */
	private String industryCode; //行业板块代码
	private String placingResult;

	private String sysTime;// 系统时间
}
