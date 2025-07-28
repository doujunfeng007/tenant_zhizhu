package com.minigod.zero.resource.enums;

/**
 * @Title: OsTypeEnum.java
 * @Description: 设备操作系统类型
 * @Copyright: © 2014 sunline
 * @Company: sunline
 *
 * @author sunline
 * @date 2014-12-29 下午4:27:52
 * @version v1.0
 */

public enum OsTypeEnum {
	OS_ALL("全部", 0),
	OS_ANDROID("安卓系统", 1),
	OS_IOS("IOS系统", 2);

	private String typeName;
	private Integer typeValue;

	private OsTypeEnum(String typeName, Integer typeValue) {
		this.typeName = typeName;
		this.typeValue = typeValue;
	}

	public Integer getTypeValue() {
		return this.typeValue;
	}

	public static String getTypeName(Integer index) {
		for (OsTypeEnum osTypeEnum : OsTypeEnum.values()) {
			if (osTypeEnum.getTypeValue().equals(index)) {
				return osTypeEnum.typeName;
			}
		}
		return null;
	}

	public static Integer getTypeValue(Integer index) {
		for (OsTypeEnum osTypeEnum : OsTypeEnum.values()) {
			if (osTypeEnum.getTypeValue().equals(index)) {
				return osTypeEnum.getTypeValue();
			}
		}
		return null;
	}

	public static boolean containOsType(Integer osType) {
		if (osType == null) {
			return false;
		}
		for (OsTypeEnum osTypeEnum : OsTypeEnum.values()) {
			if (osTypeEnum.getTypeValue().equals(osType)) {
				return true;
			}
		}
		return false;
	}

}
