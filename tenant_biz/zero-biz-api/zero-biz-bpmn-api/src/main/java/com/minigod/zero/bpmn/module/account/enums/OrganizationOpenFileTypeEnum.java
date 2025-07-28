package com.minigod.zero.bpmn.module.account.enums;

/**
 * 机构开户资料上传文件类型
 *
 * @author eric
 * @since 2024-05-31 15:45:25
 */
public enum OrganizationOpenFileTypeEnum {
	COMPANY_REGIST_CERT(1, "公司注册证书"),
	BUSINESS_REGIST_CERT(2, "商业登记证书");
	private final int type;
	private final String description;

	OrganizationOpenFileTypeEnum(int type, String description) {
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
	public static OrganizationOpenFileTypeEnum fromType(int type) {
		for (OrganizationOpenFileTypeEnum typeEnum : OrganizationOpenFileTypeEnum.values()) {
			if (typeEnum.getType() == type) {
				return typeEnum;
			}
		}
		return null;
	}
}
