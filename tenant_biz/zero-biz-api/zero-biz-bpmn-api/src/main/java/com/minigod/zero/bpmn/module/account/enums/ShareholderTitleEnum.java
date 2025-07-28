package com.minigod.zero.bpmn.module.account.enums;

/**
 * 机构开户董事、股东、授权签署参数对象类型
 */
public enum ShareholderTitleEnum {
	AUTHORIZED_SIGNER(3, "授权签署"),
	DIRECTOR(1, "董事"),
	SHAREHOLDER(2, "股东");

	private Integer code;
	private String message;

	ShareholderTitleEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public static ShareholderTitleEnum getCode(Integer code) {
		for (ShareholderTitleEnum e : ShareholderTitleEnum.values()) {
			if (e.getCode().equals(code)) {
				return e;
			}
		}
		return null;
	}
}
