package com.minigod.zero.customer.enums;

import com.minigod.zero.core.tool.utils.StringUtil;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/20 14:37
 * @description：
 */
public enum Gender {
	DEFAULT(-1,"未知"),
	MAN(0,"男"),
	WMAN(1,"女");

	private Integer code;
	private String desc;

	Gender(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode(){
		return this.code;
	}

	public String getDesc(){
		return this.desc;
	}

	public static Gender getByCode(Integer code){
		if (code == null){
			return DEFAULT;
		}
		for (Gender type: Gender.values()){
			if (type.getCode().equals(code)){
				return type;
			}
		}
		return DEFAULT;
	}

	public static Gender getByCode(String code){
		if (StringUtil.isBlank(code)){
			return DEFAULT;
		}
		for (Gender type: Gender.values()){
			if (type.getCode().equals(Integer.valueOf(code))){
				return type;
			}
		}
		return DEFAULT;
	}
}
