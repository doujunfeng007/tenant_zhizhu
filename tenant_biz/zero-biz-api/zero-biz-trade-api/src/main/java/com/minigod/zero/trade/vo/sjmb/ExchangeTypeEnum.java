package com.minigod.zero.trade.vo.sjmb;

/**
 * sunline 市场
 */
public enum ExchangeTypeEnum {
    /**
     * 市场
     */
    HK("HK", "SEHK"),
	NYSE("US", "NYSE"),  //纽约交易所
	NASDAQ("US", "NASDAQ"), //纳斯达克交易所
    ML("ML", ""),  // 中华通  C1 和C2
    SH("SH", "SSE"),  // 上海
    SZ("SZ", "SZSE"),  // 深圳
    BOND("BOND", "BOND");
    private String exchangeType;
    private String counterExchangeType;

    ExchangeTypeEnum(String exchangeType, String counterExchangeType) {
        this.exchangeType = exchangeType;
        this.counterExchangeType = counterExchangeType;
    }

	public static String getZsExchangeType(String counterExchangeType) {
		for (ExchangeTypeEnum i : ExchangeTypeEnum.values()) {
			if (i.getCounterExchangeType().equals(counterExchangeType)) {
				return i.getExchangeType();
			}
		}
		return null;
	}

    public static String getCounterExchangeType(String exchangeType) {
        for (ExchangeTypeEnum i : ExchangeTypeEnum.values()) {
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
