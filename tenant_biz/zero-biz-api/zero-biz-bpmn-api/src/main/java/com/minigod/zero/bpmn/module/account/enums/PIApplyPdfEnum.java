package com.minigod.zero.bpmn.module.account.enums;

import lombok.Getter;

/**
 * PI 申请文件枚举
 *
 * @author eric
 * @since 2024-11-21 10:11:08
 */
@Getter
public enum PIApplyPdfEnum {
	NOTICE_REGARDED_AS_PROFESSIONAL_INVESTORS(1, "关于被视为专业投资者的通知"),
	CONFIRMATION_IDENTITY_AS_PROFESSIONAL_INVESTOR(2, "被视为专业投资者身份的确认书");

	/**
	 * 对应的文件枚举值
	 */
	private final int value;
	/**
	 * 对应的文件枚举名称
	 */
	private final String name;

	PIApplyPdfEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static PIApplyPdfEnum find(int value) {
		for (PIApplyPdfEnum pdfEnum : values()) {
			if (pdfEnum.getValue() == (value)) {
				return pdfEnum;
			}
		}
		return null;
	}
}
