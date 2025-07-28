package com.minigod.zero.gateway.provider;

import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权配置
 *
 * @author Chill
 */
public class AuthProvider {

	private static final List<String> DEFAULT_SKIP_URL = new ArrayList<>();
	private static final List<String> TRADE_FILTER_URL = new ArrayList<>();

	static {
		DEFAULT_SKIP_URL.add("/open/oauth/token/**");
		DEFAULT_SKIP_URL.add("/open/oauth/logout");
		DEFAULT_SKIP_URL.add("/open/oauth/captcha/**");
		DEFAULT_SKIP_URL.add("/open/oauth/clear-cache/**");
		DEFAULT_SKIP_URL.add("/open/oauth/user-info");
		DEFAULT_SKIP_URL.add("/open/oauth/render/**");
		DEFAULT_SKIP_URL.add("/open/oauth/callback/**");
		DEFAULT_SKIP_URL.add("/open/oauth/revoke/**");
		DEFAULT_SKIP_URL.add("/open/oauth/refresh/**");
		DEFAULT_SKIP_URL.add("/open/update_open_email");
		DEFAULT_SKIP_URL.add("/oauth/authorize/**");
		DEFAULT_SKIP_URL.add("/token/**");
		DEFAULT_SKIP_URL.add("/actuator/**");
		DEFAULT_SKIP_URL.add("/v2/api-docs/**");
		DEFAULT_SKIP_URL.add("/auth/**");
		DEFAULT_SKIP_URL.add("/log/**");
		DEFAULT_SKIP_URL.add("/menu/routes");
		DEFAULT_SKIP_URL.add("/menu/auth-routes");
		DEFAULT_SKIP_URL.add("/menu/top-menu");
		DEFAULT_SKIP_URL.add("/tenant/info");
		DEFAULT_SKIP_URL.add("/process/resource-view");
		DEFAULT_SKIP_URL.add("/process/diagram-view");
		DEFAULT_SKIP_URL.add("/manager/check-upload");
		DEFAULT_SKIP_URL.add("/error/**");
		DEFAULT_SKIP_URL.add("/assets/**");

		DEFAULT_SKIP_URL.add("/mobile/endpoint/**");
		DEFAULT_SKIP_URL.add("/email/endpoint/**");
		DEFAULT_SKIP_URL.add("/notify/endpoint/**");

		DEFAULT_SKIP_URL.add("/**/open/wt/**");
		DEFAULT_SKIP_URL.add("/**/open/h5/**");
		DEFAULT_SKIP_URL.add("/**/open/files/**");
		DEFAULT_SKIP_URL.add("/**/open/publish/**");
		DEFAULT_SKIP_URL.add("/zero-platform/open/**");
		DEFAULT_SKIP_URL.add("/zero-biz-cust/open/reset_pwd");
		DEFAULT_SKIP_URL.add("/zero-biz-cust/open/upload_device");
		DEFAULT_SKIP_URL.add("/zero-biz-mkt/open/broker/get_all_brokerInfo");
		DEFAULT_SKIP_URL.add("/zero-search/open/assetInfo/search_stk_cct");
		DEFAULT_SKIP_URL.add("/open/trade/fund_api/get_stock_margin_ratio");
		DEFAULT_SKIP_URL.add("/open/trade/fund_api/get_stock_margin_ratio_single");

		TRADE_FILTER_URL.add("/open/trade/auth_api/place_order");
		TRADE_FILTER_URL.add("/open/trade/auth_api/update_order");
		TRADE_FILTER_URL.add("/open/trade/auth_api/cancel_order");
		TRADE_FILTER_URL.add("/open/trade/auth_api/place_strategy_order");
		TRADE_FILTER_URL.add("/open/trade/auth_api/update_strategy_order");
		TRADE_FILTER_URL.add("/open/trade/auth_api/cancel_strategy_order");
		TRADE_FILTER_URL.add("/open/trade/auth_api/update_common_order");
		TRADE_FILTER_URL.add("/open/ipo/**");

	}

	/**
	 * 默认无需鉴权的API
	 */
	public static List<String> getDefaultSkipUrl() {
		return DEFAULT_SKIP_URL;
	}

	/**
	 * 需要交易解锁的API
	 */
	public static List<String> getTradeFilterUrl() {
		return TRADE_FILTER_URL;
	}

}
