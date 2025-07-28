package com.minigod.zero.trade.vo.tiger;

/**
 * 币种
 */
public enum TigerCurrencyEnum {
    /**
     * 币种
     */
    USD("USD", "USD"),
	CNY("CNY", "CNY"),
	HKD("HKD", "HKD");
    private String currency;
    private String counterCurrency;

    TigerCurrencyEnum(String currency, String counterCurrency) {
        this.currency = currency;
        this.counterCurrency = counterCurrency;
    }

    public static String getCounterCurrency(String currency) {
        for (TigerCurrencyEnum i : TigerCurrencyEnum.values()) {
            if (i.getCurrency().equals(currency)) {
                return i.getCounterCurrency();
            }
        }
        return null;
    }

    public static String getZsCurrency(String counterCurrency) {
        for (TigerCurrencyEnum i : TigerCurrencyEnum.values()) {
            if (i.getCounterCurrency().equals(counterCurrency)) {
                return i.getCurrency();
            }
        }
        return null;
    }



    public  String getCurrency() {
        return currency;
    }

    public String getCounterCurrency() {
        return counterCurrency;
    }

}
