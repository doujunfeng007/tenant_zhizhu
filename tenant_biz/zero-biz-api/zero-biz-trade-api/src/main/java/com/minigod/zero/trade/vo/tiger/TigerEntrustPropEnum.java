package com.minigod.zero.trade.vo.tiger;

/**
 * sunline 委托状态
 */
public enum TigerEntrustPropEnum {
    /**
     * 委托状态
     */
	ALO("ALO", "AL"),   //竞价限价盘
	AO("AO", "AM"), //竞价盘
	ASO("ASO", "ASO"),
	BC("BC", "BC"),
	ELO("ELO", "LMT"),  //增强限价盘
	LO("LO", "LMT"),  //限价盘
	M("M", "M"),
	MO("MO", "MKT"),  //市价
	O("O", "O"),  //碎股盘
	OBC("OBC", "OBC"),
	ODD("ODD", "ODD"),
	P("P", "P"),
	SLO("SLO", "SLO"), //SLO
	SODD("SODD", "SODD"),
	S("S", "LMT"), // 特别限价单
	V("V", "V");

    private String entrustProp;
    private String tigerEntrustProp;

    TigerEntrustPropEnum(String entrustProp, String tigerEntrustProp) {
        this.entrustProp = entrustProp;
        this.tigerEntrustProp = tigerEntrustProp;
    }

    public static String getTigerEntrustProp(String entrustProp) {
        for (TigerEntrustPropEnum i : TigerEntrustPropEnum.values()) {
            if (i.getEntrustProp().equals(entrustProp)) {
                return i.getTigerEntrustProp();
            }
        }
        return null;
    }

    public static String getZsEntrustProp(String tigerEntrustProp) {
        for (TigerEntrustPropEnum i : TigerEntrustPropEnum.values()) {
            if (i.getTigerEntrustProp().equals(tigerEntrustProp)) {
                return i.getEntrustProp();
            }
        }
        return null;
    }



    public  String getEntrustProp() {
        return entrustProp;
    }

    public String getTigerEntrustProp() {
        return tigerEntrustProp;
    }

}
