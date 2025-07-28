package com.minigod.zero.biz.common.enums;

import org.apache.commons.lang3.StringUtils;

public enum MarketVersion {
	_GNB("1", "国内版"), _HWB("2", "海外版"), _TYB("3", "通用版");
	private String val;
	private String desc;

	public String getVal() {
		return val;
	}

	public String getDesc() {
		return desc;
	}

	MarketVersion(String val, String desc) {
		this.val = val;
		this.desc = desc;
	}

	public static Boolean isExist(String flag) {
		if (StringUtils.isNotBlank(flag)) {
			for (MarketVersion ext : MarketVersion.values()) {
				if (ext.getVal().equals(flag)) {
					return true;
				}
			}
		}
		return false;
	}
}
