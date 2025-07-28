package com.minigod.zero.trade.vo.tiger;

/**
 * sunline 委托方向
 */
public enum TigerEntrustBsEnum {
    /**
     * 委托方向
     */
    BUY("B", "BUY"),
    SELL("S", "SELL");
    private String entrustBs;
    private String tigerEntrustBs;

    TigerEntrustBsEnum(String entrustBs, String tigerEntrustBs) {
        this.entrustBs = entrustBs;
        this.tigerEntrustBs = tigerEntrustBs;
    }

    public static String getTigerEntrustBs(String entrustBs) {
        for (TigerEntrustBsEnum i : TigerEntrustBsEnum.values()) {
            if (i.getEntrustBs().equals(entrustBs)) {
                return i.getTigerEntrustBs();
            }
        }
        return null;
    }

    public static String getZsEntrustBs(String tigerEntrustBs) {
        for (TigerEntrustBsEnum i : TigerEntrustBsEnum.values()) {
            if (i.getTigerEntrustBs().equals(tigerEntrustBs)) {
                return i.getEntrustBs();
            }
        }
        return null;
    }



    public  String getEntrustBs() {
        return entrustBs;
    }

    public String getTigerEntrustBs() {
        return tigerEntrustBs;
    }

}
