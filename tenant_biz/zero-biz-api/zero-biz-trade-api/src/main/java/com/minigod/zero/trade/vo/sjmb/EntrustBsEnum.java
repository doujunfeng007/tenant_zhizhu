package com.minigod.zero.trade.vo.sjmb;

/**
 * sunline 委托方向
 */
public enum EntrustBsEnum {
    /**
     * 委托方向
     */
    BUY("B", "BUY"),
    SELL("S", "SELL");
    private String entrustBs;
    private String counterEntrustBs;

    EntrustBsEnum(String entrustBs, String counterEntrustBs) {
        this.entrustBs = entrustBs;
        this.counterEntrustBs = counterEntrustBs;
    }

    public static String getCounterEntrustBs(String entrustBs) {
        for (EntrustBsEnum i : EntrustBsEnum.values()) {
            if (i.getEntrustBs().equals(entrustBs)) {
                return i.getCounterEntrustBs();
            }
        }
        return null;
    }

    public static String getZsEntrustBs(String counterEntrustBs) {
        for (EntrustBsEnum i : EntrustBsEnum.values()) {
            if (i.getCounterEntrustBs().equals(counterEntrustBs)) {
                return i.getEntrustBs();
            }
        }
        return null;
    }



    public  String getEntrustBs() {
        return entrustBs;
    }

    public String getCounterEntrustBs() {
        return counterEntrustBs;
    }

}
