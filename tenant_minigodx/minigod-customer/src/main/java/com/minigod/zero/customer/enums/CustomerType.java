package com.minigod.zero.customer.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/20 15:02
 * @description：
 */
public enum CustomerType {
	TOURIST(0,"游客"),
	REGULAR_CUSTOMERS(1,"普通个户"),
	INSTITUTIONAL_CUSTOMERS(2,"机构账户");


	private Integer code;
	private String desc;

	CustomerType(Integer code,String desc){
		this.code=code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc(){
		return desc;
	}

	public static CustomerType getByCode(Integer code) {
		for (CustomerType type : CustomerType.values()) {
			if (code.equals(type.getCode())) {
				return type;
			}
		}
		return null;
	}
}
