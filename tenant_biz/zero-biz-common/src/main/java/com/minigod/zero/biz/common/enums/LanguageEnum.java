package com.minigod.zero.biz.common.enums;

/**
 * 语言枚举
 */
public enum LanguageEnum {

	EN("en", "英文"),
	ZH_CN("zh_CN", "简体中文"),
	ZH_CN_LOWER("zh_cn", "简体中文"),
	ZH_HANS("zh-hans", "简体中文"),
	ZH_HANT("zh-hant", "繁体中文"),
	ZH_TW("zh-hant", "繁体中文"),
	ZH_HK("zh_HK", "繁体中文"),
	ZH_HK_LOWER("zh_hk", "繁体中文"),
	EN_US("en_US", "英文"),
	EN_US_LOWER("en_us", "英文");

	private String code;
	private String value;

	LanguageEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return this.code;
	}

	public String getValue() {
		return this.value;
	}

	public static LanguageEnum getLanguageEnumOf(String code) {
		LanguageEnum[] enums = values();
		for (LanguageEnum languageEnum : enums) {
			if (languageEnum.getCode().contentEquals(code)) {
				return languageEnum;
			}
		}
		return null;
	}

}
