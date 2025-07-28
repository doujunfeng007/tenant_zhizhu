package com.minigod.zero.manage.enums;

/**
 * @author 掌上智珠
 * @since 2023/7/17 18:51
 */
public enum SensitiveWordScopeEnum {
	ALL(0, "所有范围"),

	ZHIZHU(1, "智珠");


	private SensitiveWordScopeEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	private Integer code;
	private String message;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
