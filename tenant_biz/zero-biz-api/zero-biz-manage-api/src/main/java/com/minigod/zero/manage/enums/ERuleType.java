package com.minigod.zero.manage.enums;

/**
 * 规则类型
 *
 * @author eric
 * @since 2024-12-24 18:19:08
 */
public enum ERuleType {
	REGISTER(0, "注册"),
	OPEN(1, "开户"),
	DEPOSIT(2, "入金"),
	INVITE_DEPOSIT(3, "邀请入金"),
	INVITE_OPEN(4, "邀请开户"),
	TRANSFER(5, "转仓"),
	INVITE_TRANSFER(6, "邀请转仓"),
	GROUP_POINTS(7, "社区积分");
	private Integer typeCode;
	private String typeValue;

	ERuleType(Integer typeCode, String typeValue) {
		this.typeCode = typeCode;
		this.typeValue = typeValue;
	}

	public Integer getTypeCode() {
		return this.typeCode;
	}

	public static Integer getTypeCode(Integer index) {
		for (ERuleType typeEnum : ERuleType.values()) {
			if (typeEnum.getTypeCode().equals(index)) {
				return typeEnum.typeCode;
			}
		}
		return null;
	}

	public static String getTypeValue(Integer index) {
		for (ERuleType typeEnum : ERuleType.values()) {
			if (typeEnum.getTypeCode().equals(index)) {
				return typeEnum.typeValue;
			}
		}
		return null;
	}
}
