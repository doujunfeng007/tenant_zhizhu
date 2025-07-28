package com.minigod.zero.dbs.enums;

import lombok.Getter;

/**
 * @ClassName: PayTypeEnum
 * @Description:
 * @Author chenyu
 * @Date 2024/3/20
 * @Version 1.0
 */
public enum PayTypeEnum {
    UN_KNOW(0, "UN_KNOW",  "","未知"),
    FPS_PPP(1, "FPS_PPP", "PPP","香港快捷PPP接口"),
    FPS_GPP(2, "FPS_GPP", "GPP","香港快捷GPP接口"),
    DBS_ACT(3, "DBS_ACT", "ACT","DBS同行转账接口"),
    DBS_RTGS(4, "DBS_RTGS", "RTGS","DBS香港本地转账接口 Chats/RTGS"),
    DBS_TT(5, "TT", "TT","DBS国际转账接口"),
    FPS_GCP(6, "FPS_GCP", "GCP","香港快捷GCP接口");

    @Getter
    private int index;
    @Getter
    private String value;
    @Getter
    private String shortValue;
    @Getter
    private String desc;

    PayTypeEnum(int index, String value, String shortValue, String desc) {
        this.index = index;
        this.value = value;
        this.shortValue = shortValue;
        this.desc = desc;
    }

    public static PayTypeEnum getEnum(String value) {
        for (PayTypeEnum typeEnum : PayTypeEnum.values()) {
            if (typeEnum.getValue().equals(value)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static PayTypeEnum getEnum(int value) {
        for (PayTypeEnum typeEnum : PayTypeEnum.values()) {
            if (typeEnum.getIndex() == value) {
                return typeEnum;
            }
        }
        return null;
    }
}
