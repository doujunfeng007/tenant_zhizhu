package com.minigod.zero.bpm.constant;

/**
 * 客户级别 包括:1-Common 2-Important 3-VIP 4-PI 5-Capital
 *
 * @author eric
 * @since 2024-05-09 20:35:09
 */
public enum ClientLevelEnum {

	COMMON(1, "Common"),
	IMPORTANT(2, "Important"),
	VIP(3, "VIP"),
	PI(4, "PI"),
	CAPITAL(5, "Capital");
	private final int level;
	private final String description;

	ClientLevelEnum(int level, String description) {
		this.level = level;
		this.description = description;
	}

	public int getLevel() {
		return level;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * 根据键获取枚举实例
	 *
	 * @param level
	 * @return
	 */
	public static ClientLevelEnum fromLevel(int level) {
		for (ClientLevelEnum levelEnum : ClientLevelEnum.values()) {
			if (levelEnum.getLevel() == level) {
				return levelEnum;
			}
		}
		return null;
	}
}
