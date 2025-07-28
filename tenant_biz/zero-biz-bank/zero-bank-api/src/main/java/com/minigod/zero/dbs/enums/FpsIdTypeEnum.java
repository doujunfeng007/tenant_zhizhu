package com.minigod.zero.dbs.enums;

import lombok.Getter;

/**
 * FPS ID 账户类型
 *
 * @author chenyu
 * @title FpsIdTypeEnum
 * @date 2023-04-22 18:01
 * @description
 */
public enum FpsIdTypeEnum {

    BANK(1,"B", "AN", "Account Number"),
    ID(2,"S", "SVID", "FPS Identifier"),
    MOBILE(3,"M", "MOBN", "Mobile Number"),
    EMAIL(4,"E", "EMAL", "Email Address"),
    FPS_HKID(5,"H","HKID","FPS HKID 香港身份证");

    @Getter
    private Integer index;
    @Getter
    private String value;
    @Getter
    private String name;
    @Getter
    private String desc;

    FpsIdTypeEnum(Integer index, String value, String name, String desc) {
        this.index = index;
        this.value = value;
        this.name = name;
        this.desc = desc;
    }

    public static String getName(Integer index) {
        for (FpsIdTypeEnum c : FpsIdTypeEnum.values()) {
            if (c.getIndex() == index) {
                return c.getName();
            }
        }
        return null;
    }

    public static FpsIdTypeEnum getEnum(Integer index){
        for (FpsIdTypeEnum typeEnum : FpsIdTypeEnum.values()) {
            if (typeEnum.index == index) {
                return typeEnum;
            }
        }
        return null;
    }


}
