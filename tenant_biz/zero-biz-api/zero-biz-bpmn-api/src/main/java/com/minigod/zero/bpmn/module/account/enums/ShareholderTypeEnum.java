package com.minigod.zero.bpmn.module.account.enums;

/**
 * 机构开户股东信息类型枚举
 *
 * @author eric
 * @since 2024-07-16 10:20:11
 */
public enum ShareholderTypeEnum {
	MR(1, "先生"),
	MS(2, "小姐"),
	PHD(3, "博士"),
	COMPANY(4, "公司");

	private Integer code;
	private String message;

	ShareholderTypeEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public static ShareholderTypeEnum getCode(Integer code) {
		for (ShareholderTypeEnum item : ShareholderTypeEnum.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
