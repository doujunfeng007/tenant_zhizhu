package com.minigod.zero.bpmn.module.account.enums;

public enum OrganizationOpenStatusEnum {
	/**
	 * 待开户
	 */
	PENDING_OPEN(0, "待开户"),
	/**
	 * 开户成功
	 */
	SUCCESS(1, "开户成功"),
	/**
	 * 开户失败
	 */
	FAILED(2, "开户失败");

	private final Integer code;
	private final String message;

	OrganizationOpenStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public static OrganizationOpenStatusEnum getCode(Integer code) {
		for (OrganizationOpenStatusEnum openStatusEnum : OrganizationOpenStatusEnum.values()) {
			if (openStatusEnum.getCode().equals(code)) {
				return openStatusEnum;
			}
		}
		return null;
	}
}
