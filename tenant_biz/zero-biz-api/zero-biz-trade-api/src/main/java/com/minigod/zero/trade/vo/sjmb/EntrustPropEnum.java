package com.minigod.zero.trade.vo.sjmb;

/**
 * sunline 下单类型
 */
public enum EntrustPropEnum {
    /**
     * 下单类型
     */
    ALO("ALO", "AUCTION_LIMIT"),   //竞价限价盘
    AO("AO", "AUCTION"), //竞价盘
    ASO("ASO", "ASO"),
    BC("BC", "BC"),
    ELO("ELO", "ENHANCED_LIMIT"),  //增强限价盘
    LO("LO", "LIMIT"),  //限价盘
    M("M", "M"),
    MO("MO", "MARKET"),  //市价
    O("O", "O"),  //碎股盘
    OBC("OBC", "OBC"),
    ODD("ODD", "ODD"),
    P("P", "P"),
    SLO("SLO", "SLO"), //SLO
    SODD("SODD", "SODD"),
	S("S", "SPECIAL_LIMIT"), // 特别竞价单
    V("V", "V");


    private String entrustProp;
    private String counterEntrustProp;

    EntrustPropEnum(String entrustProp, String counterEntrustProp) {
        this.entrustProp = entrustProp;
        this.counterEntrustProp = counterEntrustProp;
    }

    public static String getCounterEntrustProp(String entrustProp) {
        for (EntrustPropEnum i : EntrustPropEnum.values()) {
            if (i.getEntrustProp().equals(entrustProp)) {
                return i.getCounterEntrustProp();
            }
        }
        return null;
    }

    public static String getZsEntrustProp(String hsEntrustProp) {
        for (EntrustPropEnum i : EntrustPropEnum.values()) {
            if (i.getCounterEntrustProp().equals(hsEntrustProp)) {
                return i.getEntrustProp();
            }
        }
        return null;
    }



    public  String getEntrustProp() {
        return entrustProp;
    }

    public String getCounterEntrustProp() {
        return counterEntrustProp;
    }

}
