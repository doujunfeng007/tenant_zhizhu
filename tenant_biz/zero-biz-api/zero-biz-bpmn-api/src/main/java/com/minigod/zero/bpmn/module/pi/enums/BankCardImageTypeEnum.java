package com.minigod.zero.bpmn.module.pi.enums;

/**
 * 凭证类型[0-银行卡 1-银行凭证]
 */
public enum BankCardImageTypeEnum {
	BANK_CARD(0, "银行卡"),
	BANK_VOUCHER(1, "银行凭证");
	private final int type;
	private final String description;

	BankCardImageTypeEnum(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public int getType() {
		return type;
	}

	public static BankCardImageTypeEnum fromType(int type) {
		for (BankCardImageTypeEnum item : BankCardImageTypeEnum.values()) {
			if (item.getType() == type) {
				return item;
			}
		}
		return null;
	}
}
