package com.minigod.zero.customer.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/17 14:43
 * @description：
 */
public enum FundRiskLevel {
	DEFAULT(0,"未测评"),
	CONSERVATIVE(1,"保守型-R1"),
	ROBUST(2,"中度保守型-R2"),
	BALANCED(3,"平稳型-R3"),
	GROWTH_ORIENTED(4,"中度进取型-R4"),
	ENTERPRISING(5,"进取型-R5"),
	R6(6,"非常进取型-R6");

	private Integer code;
	private String desc;

	FundRiskLevel(Integer code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc(){
		return this.desc;
	}

	public static FundRiskLevel getByCode(Integer code){
		if (code == null){
			return DEFAULT;
		}
		for (FundRiskLevel type: FundRiskLevel.values()){
			if (type.getCode().equals(code)){
				return type;
			}
		}
		return DEFAULT;
	}


}
