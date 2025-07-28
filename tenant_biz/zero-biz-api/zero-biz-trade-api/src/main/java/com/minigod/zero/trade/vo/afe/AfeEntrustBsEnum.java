package com.minigod.zero.trade.vo.afe;

/**
 * sunline 委托方向
 */
public enum AfeEntrustBsEnum {
    /**
     * 委托方向
     */
    BUY("B", "B"),
    SELL("S", "S");
    private String entrustBs;
    private String counterEntrustBs;

    AfeEntrustBsEnum(String entrustBs, String counterEntrustBs) {
        this.entrustBs = entrustBs;
        this.counterEntrustBs = counterEntrustBs;
    }

    public static String getCounterEntrustBs(String entrustBs) {
        for (AfeEntrustBsEnum i : AfeEntrustBsEnum.values()) {
            if (i.getEntrustBs().equals(entrustBs)) {
                return i.getCounterEntrustBs();
            }
        }
        return null;
    }

    public static String getZsEntrustBs(String counterEntrustBs) {
        for (AfeEntrustBsEnum i : AfeEntrustBsEnum.values()) {
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
