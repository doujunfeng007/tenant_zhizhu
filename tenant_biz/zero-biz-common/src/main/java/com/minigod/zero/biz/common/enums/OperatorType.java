package com.minigod.zero.biz.common.enums;

public enum OperatorType {
	ADD_OR_UPDATE(1, "新增或修改"),
	DEL(2, "删除");
	private Integer code;
	private String desc;
	private OperatorType(Integer code, String desc) {
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
