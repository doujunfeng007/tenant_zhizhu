package com.minigod.zero.customer.enums;


/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/6 21:37
 * @description：账户类型
 */
public enum TradeAccountType {
	FUND(0,"fund","基金"),
	HLD(64,"hld","活利得"),
	ZQY(65,"bond","债券易"),
	STOCK(3,"stock","股票"),
	DEBENTURE(128,"otc","债券");


	private String desc;
	private String value;
	private Integer code;

	public Integer getCode(){
		return this.code;
	}
	public String getValue(){
		return this.value;
	}

	TradeAccountType(Integer code, String value,String desc){
		this.code = code;
		this.desc = desc;
		this.value = value;
	}
}
