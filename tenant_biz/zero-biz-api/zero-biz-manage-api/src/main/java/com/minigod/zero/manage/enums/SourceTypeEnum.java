package com.minigod.zero.manage.enums;

/**
 * 活动类型
 *
 * @author eric
 * @since 2024-12-24 14:12:08
 */
public enum SourceTypeEnum {
	ACTIVITY("活动", 1),
	MANUAL("人工发放", 2),
	COUPON("优惠券", 3),
	;

	private String name;
	private int value;

	SourceTypeEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}
}
