package com.minigod.zero.manage.enums;

/**
 * 活动类型
 *
 * @author eric
 * @since 2024-12-24 14:11:08
 */
public enum ActiveTypeEnum {
	REGISTER("注册", 0),
	OPEN_ACC("开户", 1),
	GOLDEN("入金", 2),
	TRANSFER_STOCK("转仓", 3),
	SHUANG_DAN("活动", 4),
	GROUP_POINTS("积分", 5),
	WEBSTATIC_INVITE_SHARE("官网邀请分享", 6),
	GOLDRED("金豆", 7),
	DEPOSIT_TOTAL("入金金额", 8),
	FIRST_IPO_APPLY("首次打新", 9),
	FIRST_IPOREC_SUB("首次订阅", 10),
	SHUANG_12("双12活动", 11),
	VIP("有鱼VIP", 12),
	ZERO_PRINCIPAL("零本金", 13),
	ASSETS_REACH_STANDARD("资产达标活动奖励", 14);
	private String name;
	private int vaule;

	ActiveTypeEnum(String name, int value) {
		this.name = name;
		this.vaule = value;
	}

	public String getName() {
		return name;
	}

	public int getVaule() {
		return vaule;
	}
}
