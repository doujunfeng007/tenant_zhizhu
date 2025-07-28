package com.minigod.zero.biz.common.constant;

public class AppJumpUrlConstant {
	private static final String appJumpUrlPrefix = "yystock://";

	// app首页
	public static final String HOME_PAGE = appJumpUrlPrefix + "homePage";
	// 资讯新闻详情
	public static final String NEWS_DETAIL =appJumpUrlPrefix + "newsDetail?newsId=%s&infotreeid=%s";
	// 7*24快讯首页
	public static final String NEWS_FLASH =appJumpUrlPrefix + "newsDetail?infotreeid=7";
	// 个股详情
	public static final String STOCK_DETAIL =appJumpUrlPrefix + "stockDetail?stockCode=%s&marketCode=%s";
}
