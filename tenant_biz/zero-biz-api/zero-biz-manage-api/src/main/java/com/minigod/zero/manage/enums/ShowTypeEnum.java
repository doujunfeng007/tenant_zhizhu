package com.minigod.zero.manage.enums;

/**
 * 推送触达用户类型
 *
 * @author Zhe.Xiao
 */
public enum ShowTypeEnum {
	ALL("0", "所有用户"),
	SPECIFIC("1", "特定用户"),
	CHANNEL_WHITE("2", "渠道白名单"),
	CHANNEL_BLACK("3", "渠道黑名单");

	private ShowTypeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	private String code;
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
