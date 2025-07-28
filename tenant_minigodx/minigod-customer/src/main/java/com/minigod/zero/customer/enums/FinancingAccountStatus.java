package com.minigod.zero.customer.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/10 9:56
 * @description： 理财账户状态枚举
 */
public enum FinancingAccountStatus {
	NORMAL(0,"正常"),
	FREEZE(1,"冻结"),
	REPORTING_LOSS(2,"挂失"),
	ACCOUNT_CANCELLATION(3,"销户"),
	NOT_ACTIVE(4,"未激活"),
	PRE_APPROVED(5,"预批");


	FinancingAccountStatus(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	private String desc;
	private Integer code;

	public Integer getCode() {
		return code;
	}
}
