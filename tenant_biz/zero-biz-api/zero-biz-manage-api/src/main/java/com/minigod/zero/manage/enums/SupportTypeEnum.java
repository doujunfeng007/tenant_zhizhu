package com.minigod.zero.manage.enums;

/**
 * 入金汇款方式
 * @author Zhe.Xiao
 */
public enum SupportTypeEnum {
	FPS("1", "FPS"),
	WYZZ("2", "网银转账"),
	ZPZZ("3", "支票转账"),
	KJRJ("4", "快捷入金"),
	YZZZ("5", "银证转账"),
	EDDA("6","edda");

	private String type;
	private String desc;

	SupportTypeEnum(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}
}
