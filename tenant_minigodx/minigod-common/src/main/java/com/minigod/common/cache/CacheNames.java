/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.common.cache;

/**
 * 缓存名
 *
 * @author zsdp
 */
public interface CacheNames {

	/**
	 * 返回拼接后的key
	 *
	 * @param cacheKey      缓存key
	 * @param cacheKeyValue 缓存key值
	 * @return tenantKey
	 */
	static String cacheKey(String cacheKey, String cacheKeyValue) {
		return cacheKey.concat(cacheKeyValue);
	}

	/**
	 * 返回租户格式的key
	 *
	 * @param tenantId      租户编号
	 * @param cacheKey      缓存key
	 * @param cacheKeyValue 缓存key值
	 * @return tenantKey
	 */
	static String tenantKey(String tenantId, String cacheKey, String cacheKeyValue) {
		return tenantId.concat(":").concat(cacheKey).concat(cacheKeyValue);
	}

	/**
	 * 验证码key
	 */
	String CAPTCHA_KEY = "minigod:auth::minigod:captcha:";

	/**
	 * 登录失败key
	 */
	String USER_FAIL_KEY = "minigod:user::minigod:fail:";

	/**
	 * 交易解锁2fa手机缓存
	 */
	String TOW_FA_TRADE_UNLOCK_PHONE = "tow:fa:trade:unlock:phone:";

	/**
	 * 其他2fa手机缓存
	 */
	String TOW_FA_OTHER_PHONE = "tow:fa:other::phone:";

	/**
	 * 其他2fa邮件缓存
	 */
	String TOW_FA_OTHER_EMAIL = "tow:fa:other::email:";

	/**
	 * 交易登录token
	 */
	String TRADE_LOGIN_TOKEN = "trade:login:token:";

	/**
	 * 交易设备号
	 */
	String TRADE_LOGIN_DEVICE_CODE = "trade:login:device_code";

}
