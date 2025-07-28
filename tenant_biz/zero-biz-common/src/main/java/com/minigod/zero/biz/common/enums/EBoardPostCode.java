package com.minigod.zero.biz.common.enums;

public enum EBoardPostCode {

	INDIVIDUAL("自选", 1002),
	STOCK_DETAILS("个股详情", 1003),
	MARKET("交易", 1005),
	NEWS("资讯", 1006),
	ADMINISTER("理财", 1007),
	MY_HOME("我的", 1008),

	;

	private String typeName;
	private Integer typeValue;

	public String getTypeName() {
		return typeName;
	}

	public Integer getTypeValue() {
		return typeValue;
	}

	private EBoardPostCode(String typeName, Integer typeValue) {
		this.typeName = typeName;
		this.typeValue = typeValue;
	}

	public static Integer getTypeValue(Integer index) {
		for (EBoardPostCode eBoardInfoCode : EBoardPostCode.values()) {
			if (eBoardInfoCode.getTypeValue().equals(index)) {
				return eBoardInfoCode.getTypeValue();
			}
		}
		return null;
	}
}
