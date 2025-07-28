package com.minigod.zero.customer.enums;

import com.minigod.zero.core.tool.utils.StringUtil;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/20 15:19
 * @description：
 */
public enum FundAccountType {
	DEFAULT(-1,"未知"),
	CASH(1,"现金账户"),
	FINANCING(2,"融资账户");

	private Integer code;
	private String desc;

	FundAccountType(Integer code,String desc){
		this.code=code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc(){
		return desc;
	}

	public static FundAccountType getByCode(Integer code) {
		if (code == null){
			return DEFAULT;
		}
		for (FundAccountType type : FundAccountType.values()) {
			if (code.equals(type.getCode())) {
				return type;
			}
		}
		return null;
	}

	public static FundAccountType getByCode(String code) {
		if (StringUtil.isBlank(code)){
			return DEFAULT;
		}
		for (FundAccountType type : FundAccountType.values()) {
			if (Integer.valueOf(code).equals(type.getCode())) {
				return type;
			}
		}
		return null;
	}
}
