package com.minigod.zero.manage.enums;

/**
 * 奖励状态
 *
 * @author: eric
 * @since 2024-12-24 14:14:06
 */
public enum RewardStatusTypeEnum {
	OVERDUE("已过期", -1),
	UNCLAIMED("未领取", 0),
	CLAIMED("已领取", 1),
	APPLYING("申请中", 2),
	REJECT("已驳回", 3),
	USING("使用中", 4),
	COMPLETE("已完成", 5),
	REFUSE("渠道拒绝", 6);
	private String name;
	private int vaule;

	RewardStatusTypeEnum(String name, int value) {
		this.name = name;
		this.vaule = value;
	}

	public static String getNameByKey(int key) {
		for (RewardStatusTypeEnum rewardTypeEnum : RewardStatusTypeEnum.values()) {
			if (key == rewardTypeEnum.getValue()) {
				return rewardTypeEnum.getName();
			}
		}
		return "";
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return vaule;
	}
}
