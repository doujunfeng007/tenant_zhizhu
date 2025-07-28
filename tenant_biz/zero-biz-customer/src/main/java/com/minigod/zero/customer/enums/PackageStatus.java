package com.minigod.zero.customer.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/12 15:13
 * @description：
 */
public enum PackageStatus {
	ENABLE(1,"启用"),
	DISABLE(2,"停用");

	private Integer code;
	private String desc;

	PackageStatus(Integer code,String desc){
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
		for (PackageStatus type: PackageStatus.values()){
			if (type.getCode().equals(code)){
				return type.getDesc();
			}
		}
		return "";
	}
}
