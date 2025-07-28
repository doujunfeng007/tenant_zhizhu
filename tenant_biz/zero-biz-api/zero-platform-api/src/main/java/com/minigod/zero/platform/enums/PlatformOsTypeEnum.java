package com.minigod.zero.platform.enums;

/**
 * 消息中心设备类型
 * @author Zhe.Xiao
 */
public enum PlatformOsTypeEnum {
	OS_ALL("全部", 0),
	OS_ANDROID("安卓系统", 1),
	OS_IOS("IOS系统", 2);

	private String typeName;
	private int typeValue;

	private PlatformOsTypeEnum(String typeName, int typeValue) {
		this.typeName = typeName;
		this.typeValue = typeValue;
	}

	public String getTypeName() {
		return typeName;
	}

	public int getTypeValue() {
		return typeValue;
	}

	public static String getTypeName(int index) {
		for (PlatformOsTypeEnum platformOsTypeEnum : PlatformOsTypeEnum.values()) {
			if (platformOsTypeEnum.getTypeValue() == index) {
				return platformOsTypeEnum.typeName;
			}
		}
		return null;
	}
}
