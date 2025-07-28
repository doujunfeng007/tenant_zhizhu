package com.minigod.zero.biz.common.enums;

/**
 * 功能页类型
 */

public enum FunctionPageEnum {

	DISCOVER("发现页", 1),
	MYSTOCK("自选页", 2),
	STOCK_SELECTION_POLICY("选股策略页", 3),
	FOCUS("焦点功能区",4),
	TRADE("交易页",5);

	private String typeName;
	private Integer typeValue;

	private FunctionPageEnum(String typeName, Integer typeValue) {
		this.typeName = typeName;
		this.typeValue = typeValue;
	}

	public Integer getTypeValue() {
		return this.typeValue;
	}

	public static Integer getTypeValue(FunctionPageEnum index) {
		return index.getTypeValue();
	}

	public static String getName(FunctionPageEnum index) {
		for (FunctionPageEnum c : FunctionPageEnum.values()) {
			if (c.getTypeValue().equals(index.getTypeValue())) {
				return c.typeName;
			}
		}
		return null;
	}

	public static String getName(Integer index) {
		for (FunctionPageEnum c : FunctionPageEnum.values()) {
			if (c.getTypeValue().equals(index)) {
				return c.typeName;
			}
		}
		return null;
	}
}
