package com.minigod.auth.enums;

/**
 * 登录操作类型
 */
public enum LoginActionEnum {
	IN(1, "登入"),
	OUT(0, "登出"),
	ERROR(-1, "异常");

	private final int code;
	private final String info;

	LoginActionEnum(Integer code, String info) {
		this.code = code;
		this.info = info;
	}

	public Integer getCode() {
		return code;
	}

	public String getInfo() {
		return info;
	}
}
