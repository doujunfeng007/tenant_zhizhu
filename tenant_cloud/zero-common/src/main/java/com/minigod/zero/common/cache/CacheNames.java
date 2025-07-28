
package com.minigod.zero.common.cache;

/**
 * 缓存名
 *
 * @author Chill
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
	String CAPTCHA_KEY = "zero:auth::zero:captcha:";

	/**
	 * 登录失败key
	 */
	String USER_FAIL_KEY = "zero:user::zero:fail:";

	/**
	 * 用户验证码
	 */
	String USER_VERIFICATION_CODE = "zero:user:code:%s:%s";

	/**
	 * 验证码发送账号
	 */
	String USER_VERIFICATION_CODE_SEND_ACCOUNT = "zero:user:%s:%s";

}
