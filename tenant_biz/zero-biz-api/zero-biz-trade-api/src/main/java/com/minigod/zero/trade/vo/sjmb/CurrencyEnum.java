package com.minigod.zero.trade.vo.sjmb;

/**
 * sunline 委托方向
 */
public enum CurrencyEnum {
    /**
     * 委托方向
     */
    USD("USD", "USD"),
	CNY("CNY", "CNH"),
	HKD("HKD", "HKD");
    private String currency;
    private String counterCurrency;

    CurrencyEnum(String currency, String counterCurrency) {
        this.currency = currency;
        this.counterCurrency = counterCurrency;
    }

    public static String getCounterCurrency(String currency) {
        for (CurrencyEnum i : CurrencyEnum.values()) {
            if (i.getCurrency().equals(currency)) {
                return i.getCounterCurrency();
            }
        }
        return null;
    }

    public static String getZsCurrency(String counterCurrency) {
        for (CurrencyEnum i : CurrencyEnum.values()) {
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
