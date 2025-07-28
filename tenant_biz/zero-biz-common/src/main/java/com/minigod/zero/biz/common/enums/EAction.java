package com.minigod.zero.biz.common.enums;

/**
 * 订阅者与发布者的信息的交互
 **/
public enum EAction {
	//ACLEAN, // A股清盘
	//HCLEAN, // H股清盘
	//PTFCLEAN, // 组合清盘
	PTFQUOT, // 组合行情更新
	STKHEAT, // 个股热度
	AQUOTUPDATED, // A股行情更新
	HQUOTUPDATED, // H股行情更新
	SH_TRADETICKER_UPDATED, // 上交所分笔更新
	SZ_TRADETICKER_UPDATED, // 深交所分笔更新
	//HKEXCLEAN,    //港股清盘A
	HKEXQUOTUPDATED, //港股行情更新
	HKEX_DELAY_QUOT_UPDATED,
	HKEX_BROKER_QUEUEU_PDATED, //港股经纪队列更新
	HKEX_TRADETICKER_UPDATED, // 港股分笔更新

	USEXCLEAN, //美股清盘
	USEXQUOTUPDATED, //美股行情更新
	US_BEFORE_EXQUOT_UPDATED, //美股盘前行情更新
	US_AFTER_EXQUOT_UPDATED, //美股盘后行情更新
	US_TRADETICKER_UPDATED, // 美股分笔更新
	US_BEFORE_TRADETICKER_UPDATED, // 美股盘前分笔更新
	US_AFTER_TRADETICKER_UPDATED, // 美股盘后分笔更新

	SH_TIMESHARING_UPDATED,
	SZ_TIMESHARING_UPDATED,
	HK_TIMESHARING_UPDATED,
	US_TIMESHARING_UPDATED,
	US_BEFORE_TIMESHARING_UPDATED,
	US_AFTER_TIMESHARING_UPDATED,

	// 沪港通
	XHK_BALANCE, // 港股通余额
	HGT_TOP, // 沪股通TOP
	HGT_GGT, // 港股通（沪）TOP
	HGT_AH, // AH股（沪）TOP
	// 深港通
	SGT_TOP, // 沪股通TOP
	SGT_GGT, // 港股通（沪）TOP
	SGT_AH, // AH股（沪）TOP

	/**
	 * 1分钟
	 */
	Minute1,
	/**
	 * 5分钟
	 */
	Minute5,
	/**
	 * 15分钟
	 */
	Minute15,
	/**
	 * 30分钟
	 */
	Minute30,
	/**
	 * 60分钟
	 */
	Minute60,

	// 市场首页（港股）
	HK_INDEX_UPDATE, // 港股首页指数更新
	HK_INDU_UPDATED, // 港股行业更新
	HK_CONCEPT_UPDATE,//港股概念板块
	HK_TOP_UPDATED, // 港股龙虎榜更新
	HK_TOP_GEM_UPDATE, // 港股创业板更新
	HK_TOP_MAIN_UPDATE, // 港股主板更新
	HK_ETF_UPDATE,//港股ETF更新
	HK_WARRANT_UPDATE,//港股窝轮更新
	HK_CBBC_UPDATE,//港股牛熊更新
	// 市场首页（美股）
	US_INDEX_UPDATE, // 美股首页指数更新
	US_CONCEPT_UPDATED,//美股概念板块
	TRADE, // 成交推送

	// 暗盘行情 Pillipmart Market
	PM_QUOTUPDATED, // 暗盘行情更新
	PM_BROKER_QUEUEU_PDATED, // 暗盘经纪队列更新
	PM_TRADETICKER_UPDATED, // 暗盘分笔更新
	PM_TIMESHARING_UPDATED, // 暗盘分时
	HK_CONDITION_HKEXQUOT_UPDATED, // 港股条件单行情更新
	US_CONDITION_HKEXQUOT_UPDATED, // 美股条件单行情更新
	A_CONDITION_HKEXQUOT_UPDATED, // A股条件单行情更新

	// ---------- 其他消息类型---------------------
	CLIENT_QUOT_LOG, // 客户行情日志上报
	PRICE_REMINDER, // 股价提醒
}
