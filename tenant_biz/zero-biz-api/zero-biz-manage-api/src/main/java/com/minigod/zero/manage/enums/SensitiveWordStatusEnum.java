package com.minigod.zero.manage.enums;

/**
 * @author 掌上智珠
 * @since 2023/7/17 18:54
 */
public enum SensitiveWordStatusEnum {
	/**
	 * 公共敏感词默认生效,下架
	 * 智珠敏感词默认下架,筹备中,生效
	 *
	 */
	DISABLE(0, "下架"),

	ENABLE(1, "生效"),

	REVEEWED(2,"筹备中");
	private SensitiveWordStatusEnum(Integer code, String message) {
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
