package com.minigod.zero.platform.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/13 19:51
 * @description：
 */
public enum LanguageType {
	ZH_HANS("zh-hans","简体"),
	ZH_HANT("zh-hant","繁体"),
	EN("en","英文");

	private String code;
	private String desc;

	LanguageType(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}
}
