package com.minigod.zero.customer.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/13 17:48
 * @description：
 */
public enum PackageRoundingType {
	rounding(1,"四舍五入"),
	round_down(2,"向下舍入"),
	round_up(3,"向上舍入");

	private Integer code;
	private String desc;

	PackageRoundingType(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc(){
		return desc;
	}

	public static String getByCode(Integer code){
		if (code == null){
			return "";
		}
		for (PackageRoundingType type: PackageRoundingType.values()){
			if (type.getCode().equals(code)){
				return type.getDesc();
			}
		}
		return "";
	}
}
