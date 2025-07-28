package com.minigod.zero.manage.enums;

/**
 * 子奖励类型
 *
 * @author eric
 * @since 2024-12-24 14：08：05
 */
public enum SubRewardTypeEnum {
	TYP_1(1, "融资打新手续费"),
	TYP_2(2, "现金打新手续费"),
	TYP_3(3, "0本金打新券");
	public int value;
	public String description;

	SubRewardTypeEnum(int value, String description) {
		this.value = value;
		this.description = description;
	}
}
