package com.minigod.zero.customer.enums;

import com.minigod.zero.core.tool.utils.StringUtil;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/20 15:17
 * @description：
 */
public enum OpenAccountAccessWay {
	DEFAULT(-1,"未知"),
	H5(1,"H5开户"),
	APP(2,"APP开户"),
	OFFLINE(3,"线下开户");

	private Integer code;
	private String desc;

	OpenAccountAccessWay(Integer code,String desc){
		this.code=code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc(){
		return desc;
	}

	public static OpenAccountAccessWay getByCode(Integer code) {
		if (code == null){
			return DEFAULT;
		}
		for (OpenAccountAccessWay type : OpenAccountAccessWay.values()) {
			if (code.equals(type.getCode())) {
				return type;
			}
		}
		return null;
	}

	public static OpenAccountAccessWay getByCode(String code) {
		if (StringUtil.isBlank(code)){
			return DEFAULT;
		}
		for (OpenAccountAccessWay type : OpenAccountAccessWay.values()) {
			if (Integer.valueOf(code).equals(type.getCode())) {
				return type;
			}
		}
		return null;
	}
}
