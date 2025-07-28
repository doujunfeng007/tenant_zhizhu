package com.minigod.zero.customer.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/25 9:56
 * @description：
 */
public enum AmountType {

	AVAILABLE_AMOUNT(1,"可用金额"),
	FREEZE_AMOUNT(2,"冻结金额"),
	TRANSITED_AMOUNT(3,"在途金额");

	private Integer code;
	private String desc;

	AmountType(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode(){
		return this.code;
	}
}
