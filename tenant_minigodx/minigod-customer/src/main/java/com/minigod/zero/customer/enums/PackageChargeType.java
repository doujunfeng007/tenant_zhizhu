package com.minigod.zero.customer.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/13 17:47
 * @description：
 */
public enum PackageChargeType {
	EACH_FIXED_TRANSACTION(1,"每笔固定"),
	PROPORTIONAL_CALCULATION(2,"比例计算");

	private Integer code;
	private String desc;

	PackageChargeType(Integer code,String desc){
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
		for (PackageChargeType type: PackageChargeType.values()){
			if (type.getCode().equals(code)){
				return type.getDesc();
			}
		}
		return "";
	}
}
