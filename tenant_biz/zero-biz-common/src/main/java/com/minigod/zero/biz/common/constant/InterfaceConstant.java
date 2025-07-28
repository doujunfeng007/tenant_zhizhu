package com.minigod.zero.biz.common.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class InterfaceConstant {

	public static final String INT_INVEST = "get_unread_message_for_invest";
	public static final String INT_REBALANCE = "get_unread_message_for_rebalance";
	public static final String INT_COMMENT = "get_unread_message_for_comment";
	public static final String INT_LIKE = "get_unread_message_for_like";
	public static final String INT_FAV = "get_unread_message_for_fav";
	public static final String INT_SERVICE = "get_unread_message_for_service";
	public static final String INT_ASSISTANT = "get_unread_message_for_assistant";
	public static final String INT_SYSTEM_MESSAGE = "fetch_system_message";
	public static final String INT_UPDATE_STK_LIST = "update_stock_list";
	public static final String INT_UPDATE_CCT_LIST = "update_cct_list";
	public static final String INT_FETCH_FRIENDS = "fetch_friends";
	public static final String INT_FETCH_NEW_FRIENDS = "fetch_new_friends";
	public static final String INT_GET_DEFAULT_CONFIG = "get_default_config";
	public static final String INT_FETCH_IMGROUP_LIST = "fetch_imgroup_list";
	public static final String INT_GET_NEW_VIEWPOINT = "get_unread_new_viewpoint";
	public static final String INT_GET_UNREAD_SQUARE_QUESTIONER = "get_unread_square_questioner";
	public static final String INT_GET_UNREAD_EXCLUSIVE_QUESTIONER = "get_unread_exclusive_questioner";
	public static final String INT_GET_UNREAD_SQUARE_ANSWER = "get_unread_square_answer";
	public static final String INT_GET_UNREAD_EXCLUSIVE_ANSWER = "get_unread_exclusive_answer";
	public static final String INT_GET_AD_PUSH = "get_ad_push";
	public static final String INT_GET_AD_STARTUP = "get_ad_startup";
	public static final String INT_GET_UNREAD_COUPON_MESSAGE = "get_unread_coupon_message";
	public static final String INT_STK_PRICE_REMINDER = "get_unread_message_for_stk_price_reminder";
	public static final String INT_CLEAN_BROWSER_CACHE = "clean_browser_cache"; // APP端是否需要清本地浏览器缓存
	public static final String INT_STK_SINGLE_PUSH = "get_unread_message_for_single_push"; // 个股推送
	public static final String INT_IPO_STK_REMINDER = "get_unread_message_for_ipo_stk_reminder"; // 新股提醒
	public static final String INT_TRADE_REMINDER = "get_unread_message_for_trade_reminder"; // 交易提醒
	public static final String INT_IPO_GENIUS = "get_unread_message_for_ipo_genius"; // 牛人订阅提醒
	public static final String INT_HOT_RECOMMEND = "fetch_hot_recommend"; // 查询热门股票列表
	public static final String INT_IPO_STK_CF_REMINDER = "get_unread_message_for_ipo_stk_cf_reminder"; //今日暗盘提醒

	public static final List<String> VALID_INTERFACES = Arrays.asList(INT_INVEST, INT_REBALANCE, INT_COMMENT, INT_LIKE, INT_FAV, INT_SERVICE, INT_ASSISTANT, INT_SYSTEM_MESSAGE,
		INT_UPDATE_STK_LIST, INT_UPDATE_CCT_LIST, INT_FETCH_FRIENDS, INT_FETCH_NEW_FRIENDS, INT_GET_DEFAULT_CONFIG, INT_FETCH_IMGROUP_LIST, INT_GET_NEW_VIEWPOINT,
		INT_GET_UNREAD_SQUARE_QUESTIONER, INT_GET_UNREAD_EXCLUSIVE_QUESTIONER,
		INT_CLEAN_BROWSER_CACHE,
		INT_GET_UNREAD_SQUARE_ANSWER, INT_GET_UNREAD_EXCLUSIVE_ANSWER, INT_GET_AD_PUSH,INT_GET_AD_STARTUP, INT_GET_UNREAD_COUPON_MESSAGE, INT_STK_PRICE_REMINDER,
		INT_STK_SINGLE_PUSH,INT_IPO_STK_REMINDER,INT_TRADE_REMINDER,INT_IPO_GENIUS,INT_HOT_RECOMMEND,INT_IPO_STK_CF_REMINDER);

	public static final HashSet<String> SET_INTERFACE = new HashSet<String>(VALID_INTERFACES);
	public static HashMap<String, Integer> MAP_GETUNREAD_MESSAGE_PARAM;

	static {
		MAP_GETUNREAD_MESSAGE_PARAM = new HashMap<String, Integer>();
		MAP_GETUNREAD_MESSAGE_PARAM.put(INT_INVEST, 2001);
		MAP_GETUNREAD_MESSAGE_PARAM.put(INT_REBALANCE, 12002);
		MAP_GETUNREAD_MESSAGE_PARAM.put(INT_COMMENT, 12003);
		MAP_GETUNREAD_MESSAGE_PARAM.put(INT_LIKE, 12005);
		MAP_GETUNREAD_MESSAGE_PARAM.put(INT_FAV, 12006);
		MAP_GETUNREAD_MESSAGE_PARAM.put(INT_SERVICE, 12007);
		MAP_GETUNREAD_MESSAGE_PARAM.put(INT_ASSISTANT, 12008);
		MAP_GETUNREAD_MESSAGE_PARAM.put(INT_GET_UNREAD_COUPON_MESSAGE, 12010);
		MAP_GETUNREAD_MESSAGE_PARAM.put(INT_STK_PRICE_REMINDER, 12012);
		MAP_GETUNREAD_MESSAGE_PARAM.put(INT_STK_SINGLE_PUSH, 12013);
		MAP_GETUNREAD_MESSAGE_PARAM.put(INT_IPO_STK_REMINDER, 12014);
		MAP_GETUNREAD_MESSAGE_PARAM.put(INT_TRADE_REMINDER, 12015);
		MAP_GETUNREAD_MESSAGE_PARAM.put(INT_IPO_GENIUS, 12018);
		MAP_GETUNREAD_MESSAGE_PARAM.put(INT_IPO_STK_CF_REMINDER, 12019);
	}
}
