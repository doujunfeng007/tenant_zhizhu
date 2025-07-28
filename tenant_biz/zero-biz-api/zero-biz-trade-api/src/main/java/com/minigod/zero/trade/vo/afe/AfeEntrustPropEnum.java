package com.minigod.zero.trade.vo.afe;

/**
 * sunline 下单类型
 */
public enum AfeEntrustPropEnum {
    /**
     * 下单类型
     */
    ALO("ALO", "ALO"),   //竞价限价盘
    AO("AO", "AO"), //竞价盘
    ASO("ASO", "ASO"),
    BC("BC", "BC"),
    ELO("ELO", "ELO"),  //增强限价盘
    LO("LO", "LO"),  //限价盘
    M("M", "M"),
    MO("MO", "MO"),  //市价
    O("O", "O"),  //碎股盘
    OBC("OBC", "OBC"),
    ODD("ODD", "ODD"),
    P("P", "P"),
    SLO("SLO", "SLO"), //SLO
    SODD("SODD", "SODD"),
	S("S", "SLO"), // 特别限价单
    V("V", "V");


    private String entrustProp;
    private String counterEntrustProp;

    AfeEntrustPropEnum(String entrustProp, String counterEntrustProp) {
        this.entrustProp = entrustProp;
        this.counterEntrustProp = counterEntrustProp;
    }

    public static String getCounterEntrustProp(String entrustProp) {
        for (AfeEntrustPropEnum i : AfeEntrustPropEnum.values()) {
            if (i.getEntrustProp().equals(entrustProp)) {
                return i.getCounterEntrustProp();
            }
        }
        return null;
    }

    public static String getZsEntrustProp(String hsEntrustProp) {
        for (AfeEntrustPropEnum i : AfeEntrustPropEnum.values()) {
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
