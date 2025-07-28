
package com.minigod.zero.gateway.provider;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求响应返回
 *
 * @author Chill
 */
public class ResponseProvider {

	/**
	 * 处理成功
	 *
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> success(String message) {
		return response(200, message);
	}

	/**
	 * 处理失败
	 *
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> fail(String message) {
		return response(400, message);
	}

	/**
	 * 登录未授权
	 *
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> unAuth(String message) {
		return response(401, message);
	}

	/**
	 * 验签失败
	 *
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> unSign(String message) {
		return response(1401, message);
	}

	/**
	 * 登录过期
	 *
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> expire(String message) {
		return response(4401, message);
	}

	/**
	 * 登录未授权
	 *
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> unOper(String message) {
		return response(5401, message);
	}

	/**
	 * 交易未授权
	 *
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> unTrade(String message) {
		return response(2401, message);
	}

	/**
	 * 服务器异常
	 *
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> error(String message) {
		return response(500, message);
	}

	/**
	 * 构建返回的JSON数据格式
	 *
	 * @param status  状态码
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> response(int status, String message) {
		Map<String, Object> map = new HashMap<>(16);
		map.put("code", status);
		map.put("msg", message);
		map.put("data", null);
		map.put("success", true);
		if (status != 200) {
			map.put("success", false);
		}
		return map;
	}

}
