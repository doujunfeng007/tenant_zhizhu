package com.minigod.zero.trade.vo.sjmb;

/**
 * sunline 国家码
 */
public enum CountryEnum {
    /**
     * 国家码
     */
	AUS("AUS", "AU"),
	CHN("CHN", "CN"),
	CAN("CAN", "CA"),
	GBR("GBR", "GB"),
	HKG("HKG", "HK"),
	IDN("IDN", "ID"),
	JPN("JPN", "JP"),
	MAC("MAC", "MO"),
	SGP("SGP", "SG"),
	TWN("TWN", "TW")
	;
    private String country;
    private String counterCountry;

    CountryEnum(String country, String counterCountry) {
        this.country = country;
        this.counterCountry = counterCountry;
    }

    public static String getCounterCountry(String country) {
        for (CountryEnum i : CountryEnum.values()) {
            if (i.getCountry().equals(country)) {
                return i.getCounterCountry();
            }
        }
        return null;
    }

    public static String getZsCurrency(String counterCountry) {
        for (CountryEnum i : CountryEnum.values()) {
            if (i.getCounterCountry().equals(counterCountry)) {
                return i.getCountry();
            }
        }
        return null;
    }



    public  String getCountry() {
        return country;
    }

    public String getCounterCountry() {
        return counterCountry;
    }

}
