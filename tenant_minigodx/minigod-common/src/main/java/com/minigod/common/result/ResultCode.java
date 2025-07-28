package com.minigod.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

/**
 * 业务代码枚举
 */
@Getter
@AllArgsConstructor
public enum ResultCode{
	/**
	 * 操作成功
	 */
	SUCCESS(HttpServletResponse.SC_OK, "操作成功"),
	/**
	 * 操作失败
	 */
	FAIL(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "操作失败"),

	/**
	 * 业务异常
	 */
	FAILURE(HttpServletResponse.SC_BAD_REQUEST, "业务异常"),

	/**
	 * 请求未授权
	 */
	UN_AUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, "请求未授权"),

	/**
	 * 404 没找到请求
	 */
	NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, "404 没找到请求"),

	/**
	 * 消息不能读取
	 */
	MSG_NOT_READABLE(HttpServletResponse.SC_BAD_REQUEST, "消息不能读取"),

	/**
	 * 不支持当前请求方法
	 */
	METHOD_NOT_SUPPORTED(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "不支持当前请求方法"),

	/**
	 * 不支持当前媒体类型
	 */
	MEDIA_TYPE_NOT_SUPPORTED(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "不支持当前媒体类型"),
	/**
	 * 访问太过频繁，请稍后再试!
	 */
	TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS.value(), "访问太过频繁，请稍后再试!"),

	/**
	 * 请求被拒绝
	 */
	REQ_REJECT(HttpServletResponse.SC_FORBIDDEN, "请求被拒绝"),

	/**
	 * 服务器异常
	 */
	INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器异常"),

	/**
	 * 缺少必要的请求参数
	 */
	PARAM_MISS(HttpServletResponse.SC_BAD_REQUEST, "缺少必要的请求参数"),

	/**
	 * 请求参数类型错误
	 */
	PARAM_TYPE_ERROR(HttpServletResponse.SC_BAD_REQUEST, "请求参数类型错误"),

	/**
	 * 请求参数绑定错误
	 */
	PARAM_BIND_ERROR(HttpServletResponse.SC_BAD_REQUEST, "请求参数绑定错误"),

	/**
	 * 参数校验失败
	 */
	PARAM_VALID_ERROR(HttpServletResponse.SC_BAD_REQUEST, "参数校验失败"),

	/**
	 * 系统错误
	 */
	SYSTEM_ERROR(514, "系统错误!"),
	ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器繁忙,请稍后再试!"),
	/**
	 * 服务器繁忙,请稍后再试!
	 */
	GATEWAY_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT.value(), "访问超时，请稍后再试!"),
	/**
	 * 访问超时，请稍后再试!
	 */
	SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE.value(), "服务暂时无法访问!"),

	/**
	 * oauth2返回码
	 */
	INVALID_TOKEN(2000, "无效的访问令牌"),
	INVALID_SCOPE(2001, "无效的作用域"),
	INVALID_REQUEST(2002, "无效的请求"),
	INVALID_CLIENT(2003, "无效的客户端"),
	INVALID_GRANT(2004, "无效的授权"),
	REDIRECT_URI_MISMATCH(2005, "回调url不匹配"),
	UNAUTHORIZED_CLIENT(2006, "无效的客户端"),
	EXPIRED_TOKEN(2007, "token过期"),
	UNSUPPORTED_GRANT_TYPE(2008, "无效的授权模式"),
	UNSUPPORTED_RESPONSE_TYPE(2009, "unsupported_response_type"),
	UNAUTHORIZED(2012, "认证失败,请重新登录!"),
	SIGNATURE_DENIED(2013, "签名异常"),

	ACCESS_DENIED(4030, "权限不足,拒绝访问!"),
	ACCESS_DENIED_AUTHORITY_EXPIRED(4031, "授权已过期,拒绝访问!"),
	ACCESS_DENIED_UPDATING(4032, "正在升级维护中,请稍后再试!"),
	ACCESS_DENIED_DISABLED(4033, "请求地址,禁止访问!"),
	ACCESS_DENIED_NOT_OPEN(4034, "请求地址,拒绝访问!"),
	/**
	 * 账号错误
	 */
	BAD_CREDENTIALS(3000, "账号或密码错误!"),
	ACCOUNT_DISABLED(3001, "账号已被禁用!"),
	ACCOUNT_EXPIRED(3002, "账号已过期!"),
	CREDENTIALS_EXPIRED(3003, "凭证已过期!"),
	ACCOUNT_LOCKED(3004, "账号已被锁定!"),
	USERNAME_NOT_FOUND(3005, "账号或密码错误!"),
	CREDENTIALS_INVALID(3006, "凭证无效!"),
	NON_INTERNAL_ACCOUNT(3007, "非内部账号!"),
	OPTIMISTIC_LOCKING(7001, "数据更新失败");

	/**
	 * code编码
	 */
	final int code;
	/**
	 * 中文信息描述
	 */
	final String message;

}
