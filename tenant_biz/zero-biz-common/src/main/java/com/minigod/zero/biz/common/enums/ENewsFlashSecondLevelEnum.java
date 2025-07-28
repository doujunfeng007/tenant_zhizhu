package com.minigod.zero.biz.common.enums;

/**
 * @author 掌上智珠
 * @since 2023/8/10 16:39
 */
public enum ENewsFlashSecondLevelEnum {
	All(0,"不分类",null),
	YAOWEN(1, "要闻","yaowen"),
	GANGU(2, "港股","ganggu"),
	MEIGU(3, "美股","mgu"),
	ZQ(4, "债券","zq"),
	GONGGAO(5,"公告","gonggao"),
	YANBAO(6,"研报","yanbao"),
	AGU(7,"A股","agu");

	private  int typeValue;
	private  String desc;
	private String label;

	private ENewsFlashSecondLevelEnum(int typeValue, String desc,String label) {
		this.typeValue = typeValue;
		this.desc = desc;
		this.label=label;
	}

	public int getTypeValue() {
		return typeValue;
	}

	public String getDesc() {
		return desc;
	}

	public String getLabel() {
		return label;
	}

	public static String getLabelByTypeValue(int typeValue) {
		for (ENewsFlashSecondLevelEnum levelEnum : ENewsFlashSecondLevelEnum.values()) {
			if (levelEnum.getTypeValue() == typeValue) {
				return levelEnum.getLabel();
			}
		}
		return null; // 如果找不到匹配的 typeValue，返回 null 或者你认为合适的默认值
	}
}

