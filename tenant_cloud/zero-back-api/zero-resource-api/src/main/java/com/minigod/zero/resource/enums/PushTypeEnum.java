package com.minigod.zero.resource.enums;

/**
 * @Title: PushTypeEnum.java
 * @Description: 消息推送类型枚举类
 * @Copyright: © 2014 sunline
 * @Company: sunline
 *
 * @author sunline
 * @date 2014-12-28 下午3:13:54
 * @version v1.0
 */

public enum PushTypeEnum {
	STRONG_MSG("强提醒消息", 0), //
	WEAK_MSG("弱提醒消息", 1); //

	private String typeName;
	private Integer typeValue;

	private PushTypeEnum(String typeName, Integer typeValue) {
		this.typeName = typeName;
		this.typeValue = typeValue;
	}

	public Integer getTypeValue() {
		return this.typeValue;
	}

	public static String getTypeName(Integer index) {
		for (PushTypeEnum pushTypeEnum : PushTypeEnum.values()) {
			if (pushTypeEnum.getTypeValue().equals(index)) {
				return pushTypeEnum.typeName;
			}
		}
		return null;
	}

	public static Integer getTypeValue(Integer index) {
		for (PushTypeEnum pushTypeEnum : PushTypeEnum.values()) {
			if (pushTypeEnum.getTypeValue().equals(index)) {
				return pushTypeEnum.getTypeValue();
			}
		}
		return null;
	}

}
