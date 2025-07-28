package com.minigod.zero.customer.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/30 19:52
 * @description：
 */
public enum FinancingAccountType {
	PERSONAL(0,"个人"),
	ORGANIZATION(1,"机构");


	private String desc;
	private Integer code;

	public Integer getCode(){
		return this.code;
	}

	FinancingAccountType(Integer code, String desc){
		this.code = code;
		this.desc = desc;
	}
}
