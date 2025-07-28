package com.minigod.zero.biz.common.enums;

public enum DataSyncType {
	QUOT_PERMINSSION(1, "行情权限"),
	QUOT_LOGIN(2, "行情登录信息"),
	FUND_ADDITIONINFO_LIST(3, "基金列表附加信息");
	private Integer code;
	private String desc;
	private DataSyncType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
