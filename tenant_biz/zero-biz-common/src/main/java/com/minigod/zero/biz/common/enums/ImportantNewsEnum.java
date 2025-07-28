package com.minigod.zero.biz.common.enums;

/**
 * 智通要闻category类目
 */
public enum ImportantNewsEnum {

	HK_STOCK_TRENDS("港股动向"),
	OVERSEAS_TRENDS("海外动向"),
	INLAND_DEPTH("内地深度"),
	OTHER("其他"),
	COMPREHENSIVE_DEPTH("综合深度"),
	COMPREHENSIVE_TRENDS("综合动向"),
	OVERSEAS_DEPTH("海外深度"),
	HK_AND_MO_DEPTH("港澳深度"),
	MAINLAND_TRENDS("内地动向"),
	;
	private String category;

	ImportantNewsEnum(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}
}
