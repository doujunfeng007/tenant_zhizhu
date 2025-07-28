package com.minigod.zero.biz.common.enums;

public enum ErrorCodeEnum {

	OK(200, "调用成功"),
	ERROR(400, "处理失败，请稍后重试"),
	NONE_DATA(200, "无满足条件的数据"),
	EXIST_ERROR(300, "有重复值存在"),
	PARAMETER_ERROR(400, "请求参数错误"),
	SOCKET_ERROR(404, "网络异常，请稍后重试"),
	INTERNAL_ERROR(500, "请求异常，请重试"),
	CAPTCHA_OVER_TIME(600, "验证码过期"),
	CAPTCHA_ERROR(601, "验证码不正确"),
	ICBCA_ERROR(602, "调用工银接口错误"),
	TRADE_UN_LOCK_ERROR(603, "解锁失败"),
	ERROR_UNKNOWN(9999, "未知错误"),
	APP_VERSION_ERROR(400, "错误的版本号"), //升级
	;

	private int code;
	private String message;

	ErrorCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public static String getMessage(int code) {
		for (ErrorCodeEnum c : ErrorCodeEnum.values()) {
			if (c.getCode() == code) {
				return c.getMessage();
			}
		}
		return null;
	}

}
