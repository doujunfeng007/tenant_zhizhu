package com.minigod.zero.biz.common.enums;

/**
 * 凭证类型
 */

public enum AppCodeEnum {

	// APP编号 1-智投通,2-掌上智珠

	Sunline_ZSZZ("智投通", 1),
	Sunline_Custom("掌上智珠", 2);

	private String typeName;
	private Integer typeValue;

	private AppCodeEnum(String typeName, Integer typeValue) {
		this.typeName = typeName;
		this.typeValue = typeValue;
	}

	public Integer getTypeValue() {
		return this.typeValue;
	}

	public static Integer getTypeValue(AppCodeEnum index) {
		return index.getTypeValue();
	}

	public static String getName(AppCodeEnum index) {
		for (AppCodeEnum c : AppCodeEnum.values()) {
			if (c.getTypeValue().equals(index.getTypeValue())) {
				return c.typeName;
			}
		}
		return null;
	}

	public static String getName(Integer index) {
		for (AppCodeEnum c : AppCodeEnum.values()) {
			if (c.getTypeValue().equals(index)) {
				return c.typeName;
			}
		}
		return null;
	}
}
