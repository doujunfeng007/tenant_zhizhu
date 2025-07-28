package com.minigod.zero.bpmn.module.pi.enums;

/**
 * 凭证类型枚举
 */
public enum VoucherImageTypeEnum {
	ASSETS_CERTIFICATE(0, "资产凭证"),
	SUPPLEMENTARY_PROOF(1, "补充证明"),
	SIGNATURE_IMAGE(2, "签名图片");

	private final int type;
	private final String description;

	VoucherImageTypeEnum(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public int getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * 根据键获取枚举实例
	 *
	 * @param type
	 * @return
	 */
	public static VoucherImageTypeEnum fromType(int type) {
		for (VoucherImageTypeEnum typeEnum : VoucherImageTypeEnum.values()) {
			if (typeEnum.getType() == type) {
				return typeEnum;
			}
		}
		return null;
	}
}
