package com.minigod.auth.enums;

/**
 * 登录业务类型
 */
public enum LoginTypeEnum {
	LOGIN(1, "客户登陆"),
	TRD_UNLOCK(2, "交易解锁"),
	IP_OFFSITE(3, "异地访问");

	private final int code;
	private final String info;

	LoginTypeEnum(Integer code, String info) {
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
