package com.minigod.zero.customer.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/28 19:29
 * @description：
 */
public enum OpenAccountSteps {
	OPEN_FINANCING_ACCOUNT(1,"开通理财账户"),
	UPDATE_RISK_LEVEL(2,"开通基金账户"),
	UPDATE_PI_LEVEL(3,"开通活利得,债券易账户"),
	UPDATE_PI_RISK_LEVEL(4,"更新pi风险等级")
	;

	private Integer code;
	private String desc;

	public Integer getCode(){
		return this.code;
	}

	OpenAccountSteps(Integer code,String desc){
		this.code =code;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public static OpenAccountSteps getByCode(Integer code){
		if (code == null){
			return null;
		}
		for (OpenAccountSteps type: OpenAccountSteps.values()){
			if (type.getCode().equals(code)){
				return type;
			}
		}
		return null;
	}
}
