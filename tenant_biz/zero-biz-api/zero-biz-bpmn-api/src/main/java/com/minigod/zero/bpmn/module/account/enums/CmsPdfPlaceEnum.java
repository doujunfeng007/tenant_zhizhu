package com.minigod.zero.bpmn.module.account.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName: CmsPdfPlaceEnum
 * @Description:
 * @Author chenyu
 * @Date 2022/9/5
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum CmsPdfPlaceEnum {
    /**
     * 开户表格
     */
    ACCOUNT_OPENING_FORM("01", 3, 1),
    ACCOUNT_OPENING_FORM_ADDITIONAL("01", 1, 1),
    /**
     * 保证金
     */
    MARGIN_AGREEMENT("09", 3, 10),
    MARGIN_AGREEMENT_ADDITIONAL("09", 1, 10),
    /**
     * W8
     */
    W8("22", 3, 2),
    W8_ADDITIONAL("22", 1, 2),
    /**
     * 银行证明
     */
    BANK_PROOF("18", 3,18),
    BANK_PROOF_ADDITIONAL("18", 1,18),
    /**
     * 身份证明
     */
    IDENTIFICATION_DOCUMENT("32", 3,32),
    IDENTIFICATION_DOCUMENT_ADDITIONAL("32", 1,32),
    /**
     * 地址证明
     */
    ADDRESS_DOCUMENT("33", 3,33),
    ADDRESS_DOCUMENT_ADDITIONAL("33", 1,33),
    /**
     * SAS名称筛选
     */
    SAS_NAME_FILTERING("38", 3,38),
    AML_SAS_NAME_FILTERING("38", 2,38),
    SAS_NAME_FILTERING_ADDITIONAL("38", 1,38),
    /**
     * 信用记录查核
     */
    CHECK_YOUR_CREDIT_HISTORY("39", 3,39),
    CHECK_YOUR_CREDIT_HISTORY_ADDITIONAL("39", 1,39),
    /**
     * 其他
     */
    OTHER("41", 3, 0),
    ;
    private String code;
    private int type;
    private int fileType;

    public static CmsPdfPlaceEnum find(int type, int fileType){
        for (CmsPdfPlaceEnum enumd : values()) {
            if (enumd.getType()==(type)&&enumd.getFileType()==fileType) {
                return enumd;
            }
        }
        return OTHER;
    }
}
