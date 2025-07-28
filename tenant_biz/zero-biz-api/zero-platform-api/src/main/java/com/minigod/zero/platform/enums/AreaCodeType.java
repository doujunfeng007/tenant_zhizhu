package com.minigod.zero.platform.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/13 20:18
 * @description：常用区号类型
 */
public enum AreaCodeType {
	CHN("86","中国大陆"),
	CHN_HK("852","中国香港"),
	OTHER("","其他");

	private String code;
	private String desc;

	AreaCodeType(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}
}
