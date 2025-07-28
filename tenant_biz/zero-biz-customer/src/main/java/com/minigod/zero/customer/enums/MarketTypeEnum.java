package com.minigod.zero.customer.enums;

/**
 * @ClassName: com.minigod.zero.customer.enums.MarketTypeEnum
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/29 15:29
 * @Version: 1.0
 */
public enum MarketTypeEnum {
	HLD(1,"活力得"),
	BOND(2,"债券易"),
	STOCK(3,"股票");
	private Integer type;
	private String market;

	MarketTypeEnum(Integer type, String market) {
		this.type = type;
		this.market = market;
	}

	public Integer getType() {
		return type;
	}

	public String getMarket() {
		return market;
	}


}
