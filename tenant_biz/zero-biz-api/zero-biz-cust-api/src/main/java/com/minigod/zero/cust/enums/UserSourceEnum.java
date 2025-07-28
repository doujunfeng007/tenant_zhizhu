
package com.minigod.zero.cust.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户来源
 *
 * @author Chill
 */
@Getter
@AllArgsConstructor
public enum UserSourceEnum {

	/**
	 * H5
	 */
	H5("H5", 1),

	/**
	 * ANDROID
	 */
	ANDROID("ANDROID", 2),

	/**
	 * IOS
	 */
	IOS("IOS", 3),

	OTHER("OTHER", 4),
	;

	final String name;
	final int category;

	/**
	 * 匹配枚举值
	 *
	 * @param name 名称
	 * @return UserSourceEnum
	 */
	public static UserSourceEnum of(String name) {
		if (name == null) {
			return null;
		}
		UserSourceEnum[] values = UserSourceEnum.values();
		for (UserSourceEnum smsEnum : values) {
			if (smsEnum.name.equals(name)) {
				return smsEnum;
			}
		}
		return null;
	}

}
