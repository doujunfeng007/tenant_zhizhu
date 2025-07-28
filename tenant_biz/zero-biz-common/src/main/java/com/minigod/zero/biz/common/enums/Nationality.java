package com.minigod.zero.biz.common.enums;

import org.apache.commons.lang3.StringUtils;

public enum Nationality {
	_ZG("中国", "中国"),
	_TW("台湾", "台湾"),
	_XG("香港", "香港"),
	_AM("澳门", "澳门");
	private String val;
	private String desc;

	private Nationality(String val, String desc) {
		this.val = val;
		this.desc = desc;
	}

	public String getVal() {
		return val;
	}

	public String getDesc() {
		return desc;
	}

	public static final String getValue(String key) {
		if (StringUtils.isNotBlank(key)){
			for (Nationality nationality : Nationality.values()) {
				if (nationality.getVal().equals(key)) {
					return nationality.getDesc();
				}
			}
		}

		return null;
	}

}
