package com.minigod.zero.manage.enums;

/**
 * @author 掌上智珠
 * @since 2023/7/26 14:53
 */
public enum SensitiveWordTimeScopeEnum {
	/**
	 * 公共敏感词默认生效,下架
	 * 智珠敏感词默认下架,筹备中,生效
	 *
	 */
	AFTEREFFECTIVETIME(0, "生效时间后"),

	LASTMONTH(1, "近一月数据"),

	ALL(2,"所有历史"),

	UNKONW(3,"状态未知,请重新编辑");
	private SensitiveWordTimeScopeEnum(Integer code, String message) {
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
