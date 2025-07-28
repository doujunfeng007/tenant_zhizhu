package com.minigod.zero.trade.vo.afe;

/**
 * afe 市场 港股 美股 中华通 新加坡
 */
public enum AfeExchangeTypeEnum {
    /**
     * 市场
     */
    HK("HK", "HK"),
	US("US", "US"),  //纽约交易所
    ML("ML", "CN"),  // 中华通  C1 和C2
    SH("SH", "CN"),  // 上海
    SZ("SZ", "CN"),  // 深圳
	SG("SG", "SG"),  // 新加坡
	JP("JP", "JP"),  // 日本
    BOND("BOND", "BOND");
    private String exchangeType;
    private String counterExchangeType;

    AfeExchangeTypeEnum(String exchangeType, String counterExchangeType) {
        this.exchangeType = exchangeType;
        this.counterExchangeType = counterExchangeType;
    }

	public static String getZsExchangeType(String counterExchangeType) {
		for (AfeExchangeTypeEnum i : AfeExchangeTypeEnum.values()) {
			if (i.getCounterExchangeType().equals(counterExchangeType)) {
				return i.getExchangeType();
			}
		}
		return null;
	}

    public static String getCounterExchangeType(String exchangeType) {
        for (AfeExchangeTypeEnum i : AfeExchangeTypeEnum.values()) {
            if (i.getExchangeType().equals(exchangeType)) {
                return i.getCounterExchangeType();
            }
        }
        return null;
    }



    public  String getExchangeType() {
        return exchangeType;
    }

    public String getCounterExchangeType() {
        return counterExchangeType;
    }

}
