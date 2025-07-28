package com.minigod.zero.biz.common.enums;

import java.util.ArrayList;

/**
 * 用户类型2
 */
public enum EUserTypeEnum {
	REGISTER("注册用户", 1),
	ACCOUNT("开户用户", 2),
	DEPOSIT("入金用户" , 3),
	TRADE("交易用户", 4);

	private String typeName;
	private Integer typeValue;

	private EUserTypeEnum(String typeName, Integer typeValue) {
		this.typeName = typeName;
		this.typeValue = typeValue;
	}

	public Integer getTypeValue() {
		return this.typeValue;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public static Integer getTypeValue(EUserTypeEnum index) {
		return index.getTypeValue();
	}

	public static String getName(EUserTypeEnum index) {
		for (EUserTypeEnum c : EUserTypeEnum.values()) {
			if (c.getTypeValue().equals(index.getTypeValue())) {
				return c.typeName;
			}
		}
		return null;
	}

	public static String getName(Integer index) {
		for (EUserTypeEnum c : EUserTypeEnum.values()) {
			if (c.getTypeValue().equals(index)) {
				return c.typeName;
			}
		}
		return null;
	}

	public static ArrayList getValues(){
		EUserTypeEnum[] enums = EUserTypeEnum.values();
		ArrayList values = new ArrayList();
		for(int index=0; index<enums.length; index++){
			EUserTypeEnum enumItem = enums[index];
			values.add(enumItem.getTypeValue());
		}
		return values;
	}
}
