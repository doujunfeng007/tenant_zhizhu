package com.minigod.zero.customer.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/13 17:40
 * @description：套餐费率项目
 */
public enum PackageItems {
	SMART_WALLET(1,"活利得"),
	BOND_EASY(2,"债券易"),
	FUND(3,"基金"),
	HONG_KONG_STOCKS(4,"港股"),
	US_STOCKS(5,"美股"),
	A_STOCKS(6,"A股");

	private Integer code;
	private String desc;

	PackageItems(Integer code,String desc){
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
		for (PackageItems type: PackageItems.values()){
			if (type.getCode().equals(code)){
				return type.getDesc();
			}
		}
		return "";
	}
}
