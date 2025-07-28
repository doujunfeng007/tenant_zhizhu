package com.minigod.zero.cust.enums;

public enum EUserType {

	GUEST("游客用户", 0),
	GENERAL("普通用户", 1),
	ADVISER("自媒体用户", 2),
	REALTIME("串流行情用户", 3),
	EYEWITNESS("见证人" , 4),
	//TRD("交易用户", 5);
	ROBOT("机器人用户" , 5);

	private String typeName;
	private Integer typeValue;

	private EUserType(String typeName, Integer typeValue) {
		this.typeName = typeName;
		this.typeValue = typeValue;
	}

	public Integer getTypeValue() {
		return this.typeValue;
	}

	public static Integer getTypeValue(EUserType index) {
		return index.getTypeValue();
	}

	public static String getName(EUserType index) {
		for (EUserType c : EUserType.values()) {
			if (c.getTypeValue().equals(index.getTypeValue())) {
				return c.typeName;
			}
		}
		return null;
	}

	public static String getName(Integer index) {
		for (EUserType c : EUserType.values()) {
			if (c.getTypeValue().equals(index)) {
				return c.typeName;
			}
		}
		return null;
	}

	/**
	 * 判断是否为投顾
	 *
	 * @param userType
	 * @return
	 */
	public static boolean isAdviser(Integer userType) {
		if (userType == null) {
			return false;
		}
		return userType.intValue() == EUserType.ADVISER.getTypeValue();
	}


	/**
	 * 判断是否为普通客户
	 *
	 * @param userType
	 * @return
	 */
	public static boolean isGeneral(Integer userType) {
		if (userType == null) {
			return false;
		}
		return userType.intValue() == EUserType.GENERAL.getTypeValue();
	}
}
