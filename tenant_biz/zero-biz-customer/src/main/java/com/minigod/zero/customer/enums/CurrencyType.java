package com.minigod.zero.customer.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/17 15:55
 * @description：
 */
public enum CurrencyType {
	HKD("HKD","港币"),
	USD("USD","美金"),
	CNY("CNY","人民币");
	private String code;
	private String desc;

	CurrencyType(String code,String desc){
		this.code=code;
		this.desc = desc;
	}
	public static CurrencyType getByCode(String code) {
		for (CurrencyType type : CurrencyType.values()) {
			if (code.equals(type.getCode())) {
				return type;
			}
		}
		return null;
	}
	public String getCode(){
		return this.code;
	}

	public String getDesc(){
		return this.desc;
	}
}
