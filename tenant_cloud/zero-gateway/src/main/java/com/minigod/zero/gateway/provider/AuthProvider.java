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
		DEFAULT_SKIP_URL.add("/back/oauth/token/**");
		DEFAULT_SKIP_URL.add("/back/oauth/captcha/**");
		DEFAULT_SKIP_URL.add("/back/oauth/clear-cache/**");
		DEFAULT_SKIP_URL.add("/back/oauth/user-info");
		DEFAULT_SKIP_URL.add("/back/oauth/render/**");
		DEFAULT_SKIP_URL.add("/back/oauth/callback/**");
		DEFAULT_SKIP_URL.add("/back/oauth/revoke/**");
		DEFAULT_SKIP_URL.add("/back/oauth/refresh/**");
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
		DEFAULT_SKIP_URL.add("/broker-login/**");

		DEFAULT_SKIP_URL.add("/mobile/endpoint/**");
		DEFAULT_SKIP_URL.add("/email/endpoint/**");
		DEFAULT_SKIP_URL.add("/notify/endpoint/**");
		DEFAULT_SKIP_URL.add("/proxy/**");
		DEFAULT_SKIP_URL.add("/back/oauth/checkPwd");
		DEFAULT_SKIP_URL.add("/back/oauth/verification_code");

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
