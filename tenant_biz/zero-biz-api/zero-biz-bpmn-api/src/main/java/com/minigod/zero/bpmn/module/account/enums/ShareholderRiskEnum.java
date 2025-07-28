package com.minigod.zero.bpmn.module.account.enums;

/**
 * 机构开户董事、股东、授权签署参数对象风险等级
 */
public enum ShareholderRiskEnum {
	HIGH(1, "高风险"),
	MIDDLE(2, "中风险"),
	LOW(3, "低风险");

	private Integer code;
	private String message;

	ShareholderRiskEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public static ShareholderRiskEnum getCode(Integer code) {
		for (ShareholderRiskEnum shareholderRiskEnum : ShareholderRiskEnum.values()) {
			if (shareholderRiskEnum.getCode().equals(code)) {
				return shareholderRiskEnum;
			}
		}
		return null;
	}
}
