
package com.minigod.zero.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请求源类型枚举
 *
 * @author Chill
 */
@Getter
@AllArgsConstructor
public enum SourceType {

	/**
	 * web后台
	 */
	WEB("web", 1),
	/**
	 * ESOP管理端
	 */
	ESOP_ADMIN("esop-admin", 3),
	/**
	 * ESOP端
	 */
	ESOP("esop", 4)
	;

	final String name;

	final int category;

	/**
	 * 匹配枚举值
	 *
	 * @param name 名称
	 * @return ZeroUserEnum
	 */
	public static SourceType of(String name) {
		if (name == null) {
			return null;
		}
		SourceType[] values = SourceType.values();
		for (SourceType smsEnum : values) {
			if (smsEnum.name.equals(name)) {
				return smsEnum;
			}
		}
		return null;
	}

}
