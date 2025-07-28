package com.minigod.zero.customer.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/2 17:42
 * @description：业务类型
 */
public enum BusinessType {
	DEFAULT(-1,"未知"),
	BUY_HLD(1,"购买活利得"),
	BUY_BOND(2,"购买债券易"),
	BUY_STOCK(3,"购买股票"),
	FUND_SUBSCRIPTION_AMOUNT(4,"基金申购"),
	FUND_REDEMPTION_AMOUNT(5,"基金赎回"),
	FUND_CASH_DIVIDENDS(6,"基金现金分红"),
	FUND_LIQUIDATION(7,"基金清算"),
	DEBENTURE_BUY(8,"债券购买"),
	DEBENTURE_SELL(9,"债券卖出"),
	DEBENTURE_DIVIDEND(10,"债券派息"),
	Debenture_LIQUIDATION(11,"债券清算");

	private Integer code;
	private String desc;

	BusinessType(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode(){
		return code;
	}

	public String getDesc() {
		return desc;
	}


	public static BusinessType getByCode(Integer code){
		if (code == null){
			return DEFAULT;
		}
		for (BusinessType type: BusinessType.values()){
			if (type.getCode().equals(code)){
				return type;
			}
		}
		return DEFAULT;
	}
}
