package com.minigod.zero.customer.enums;

/**
 * @ClassName: com.minigod.zero.customer.enums.TradeKindType
 * @Description: 订单类型枚举类
 * @Author: linggr
 * @CreateDate: 2024/9/13 15:40
 * @Version: 1.0
 */
public enum TradeKindType {
	//1:买;2:卖;3:交换买;4:交换卖;11 IPO 买入
	DEFAULT(-1,""),
	BUY_TYPE(1,"买入"),
	SELL_TYPE(2,"卖出"),
	SWAP_PURCHASE(3,"交换买"),
	TRADING_SELL(4,"交换卖"),
	BUY_IPO(11,"IPO");

	private Integer code;
	private String desc;

	TradeKindType(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public Integer getCode(){
		return this.code;
	}

	public static TradeKindType getByCode(Integer code) {
		for (TradeKindType type : TradeKindType.values()) {
			if (code.equals(type.getCode())) {
				return type;
			}
		}
		return DEFAULT;
	}
}
