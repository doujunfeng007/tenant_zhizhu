package com.minigod.zero.manage.enums;

/**
 * 奖品状态
 *
 * @author eric
 * @since 2024-12-24 10:18:08
 */
public enum RewardStatusEnum {
	DISPLAY("上架", 0),
	UNDISPLAY("下架", 1),
	DELETED("删除", 2);

	private String name;
	private int vaule;

	RewardStatusEnum(String name, int value) {
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

