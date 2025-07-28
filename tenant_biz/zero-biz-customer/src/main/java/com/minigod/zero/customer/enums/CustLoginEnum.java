package com.minigod.zero.customer.enums;

import io.swagger.annotations.ApiModelProperty;

/**
 * 用户登录枚举类
 */
public class CustLoginEnum {
	/**
	 * 1：登入，0：登出，-1：异常
	 */
	@ApiModelProperty(value = "1：登入，0：登出，-1：异常")
	private Integer action;

	/**
	 * 类型 1-客户登陆，2-交易解锁 3-异地访问
	 */
	@ApiModelProperty(value = "类型 1-客户登陆，2-交易解锁，3-异地访问")
	private Integer type;

	public enum action {
		IN(1, "登入"),
		OUT(0, "登出"),
		ERROR(-1, "异常");

		private final int code;
		private final String info;

		action(Integer code, String info) {
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

	public enum type {
		LOGIN(1, "客户登陆"),
		TRD_UNLOCK(2, "交易解锁"),
		IP_OFFSITE(3, "异地访问");

		private final int code;
		private final String info;

		type(Integer code, String info) {
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

	public enum osType {
		ANDROID(0, "安卓"),
		IOS(1, "苹果"),
		WP(2, "WP"),
		WINDOWS(4, "windows");

		private final int code;
		private final String info;

		osType(Integer code, String info) {
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
}
