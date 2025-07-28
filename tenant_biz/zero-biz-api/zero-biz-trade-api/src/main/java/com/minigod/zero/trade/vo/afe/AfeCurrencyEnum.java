package com.minigod.zero.trade.vo.afe;

/**
 * 币种
 */
public enum AfeCurrencyEnum {
    /**
     * 币种
     */
    USD("USD", "USD"),
	CNY("CNY", "CNY"),
	HKD("HKD", "HKD");
    private String currency;
    private String counterCurrency;

    AfeCurrencyEnum(String currency, String counterCurrency) {
        this.currency = currency;
        this.counterCurrency = counterCurrency;
    }

    public static String getCounterCurrency(String currency) {
        for (AfeCurrencyEnum i : AfeCurrencyEnum.values()) {
            if (i.getCurrency().equals(currency)) {
                return i.getCounterCurrency();
            }
        }
        return null;
    }

    public static String getZsCurrency(String counterCurrency) {
        for (AfeCurrencyEnum i : AfeCurrencyEnum.values()) {
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
