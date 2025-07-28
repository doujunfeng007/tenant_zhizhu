package com.minigod.zero.bpmn.module.account.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName: CmsPlaceEnum
 * @Description:
 * @Author chenyu
 * @Date 2022/9/5
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum CmsImagePlaceEnum {

    /**
     * 银行证明
     */
    BANK_PROOF("18", 10),
    /**
     * 入金凭证
     */
    GOLDEN_CREDENTIALS("18", 8),
    /**
     * 签名
     */
    SIGNATURE("01", 3),
    /**
     * 身份证明
     */
    IDENTIFICATION_DOCUMENT("32", 1),
    /**
     * 地址证明
     */
    ADDRESS_DOCUMENT("33", 6),
    OTHER("41", 0),
    ;
    private String code;
    private int location;

    public static CmsImagePlaceEnum find(int location) {
        for (CmsImagePlaceEnum enumd : values()) {
            if (enumd.getLocation() == (location)) {
                return enumd;
            }
        }
        return OTHER;
    }
}
