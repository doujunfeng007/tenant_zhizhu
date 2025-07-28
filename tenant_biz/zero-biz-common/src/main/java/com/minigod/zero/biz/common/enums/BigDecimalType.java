package com.minigod.zero.biz.common.enums;

public enum BigDecimalType {
	ROUND_HALF_UP(4), ROUND_HALF_DOWN(5);

	private final int value;

	BigDecimalType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
