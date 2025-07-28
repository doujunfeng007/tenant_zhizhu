package com.minigod.zero.customer.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/22 15:08
 * @description：金额操作类型
 */
public enum AmountOperationType {
	DEPOSIT(1,"存入"),
	WITHDRAWAL(2,"划扣"),
	FREEZE(3,"冻结"),
	UNFREEZE(4,"解冻(扣除冻结)"),
	UNFREEZE_RETURN(5,"解冻（退回可用）");

	private Integer code;
	private String desc;

	AmountOperationType(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode(){
		return this.code;
	}
}
