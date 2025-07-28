package com.minigod.zero.manage.enums;

/**
 * @author eric
 * @description: 奖励卡券类型
 * @since 2024-12-24 14:09:08
 */
public enum RewardCardTypeEnum {
	TYPE_0(0, "普通卡券"),
	TYPE_1(1, "商务渠道活动入金奖励卡券"),
	TYPE_2(2, "金豆增股奖励"),
	TYPE_3(3, "融资抵扣券"),
	TYPE_4(4, "现金抵扣券"),
	TYPE_5(5, "0本金打新券");
	public int value;
	public String description;

	RewardCardTypeEnum(int value, String description) {
		this.value = value;
		this.description = description;
	}
}
