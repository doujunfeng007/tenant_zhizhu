package com.minigod.zero.bpmn.module.account.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName: ImageDescEnum
 * @Description:
 * @Author chenyu
 * @Date 2022/8/20
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum ImageDescEnum {
    ID_CARD_FRONT(1,101,"身份证人像面"),
    ID_CARD_BACK(1,102,"身份证国徽面"),
    HK_ID_CARD(1,105,"香港身份证"),
	HK_NON_PERMANENT_ID_CARD(1,106,"香港非永居身份证"),
    HK_TEMP_ID_CARD(1,107,"香港居民身份证人像正面照"),
    HK_VISA(1,108,"香港签证身份书"),
    PASSPORT_1(1,104,"护照1"),
    PASSPORT_2(1,109,"护照2"),
    PASSPORT_3(1,110,"护照3"),
    AVATAR_FINGER_1(5,501,"手举1个指头照"),
    AVATAR_FINGER_2(5,502,"手举2个指头照"),
    AVATAR_FINGER_3(5,503,"手举3个指头照"),
    AVATAR_FINGER_4(5,504,"手举4个指头照"),
    AVATAR_FINGER_5(5,505,"手举5个指头照"),
    AVATAR_CARD(4,401,"手举身份证正面照"),
    AVATAR_HEAD(4,402,"头像"),
    BANK_CARD_FACE(2,201,"银行卡正面照"),
    ADDRESS(6,604,"住宅地址证明"),
    PERMANENT_ADDRESS(6,605,"永久地址证明"),
    COMMUNICATION_ADDRESS(6,606,"通讯地址证明"),
    EMPLOYER_HK_1(7,701,"雇主同意书_1"),
    EMPLOYER_HK_2(7,702,"雇主同意书_2"),
    EMPLOYER_HK_3(7,703,"雇主同意书_3"),
    DEPOSIT_CREDENTIALS_1(8,801,"入金凭证1"),
    DEPOSIT_CREDENTIALS_2(8,802,"入金凭证2"),
    DEPOSIT_CREDENTIALS_3(8,803,"入金凭证3"),
    BANK_STATEMENT_1(10,804,"银行账户证明1"),
    BANK_STATEMENT_2(10,805,"银行账户证明2"),
    BANK_STATEMENT_3(10,806,"银行账户证明3"),
    FORMER_NAME_1(12,807,"曾用名凭证1"),
    FORMER_NAME_2(12,808,"曾用名凭证2"),
    FORMER_NAME_3(12,809,"曾用名凭证3"),
    LIVING_VIDEO(9,901,"活体视频"),
    LIVING_IMAGE(9,902,"活体图片"),
    SIGN(3,301,"签名"),
	ATTACHMENT(11,1001,"附件"),
	W8_SIGN(14,1401,"w8协议_签名"),
	UNKNOWN(0,0,"未知图片");

    public static ImageDescEnum find(int location, int locationType){
        for (ImageDescEnum enumd : values()) {
            if (enumd.getLocation()==(location)&&enumd.getLocationType()==locationType) {
                return enumd;
            }
        }
        return UNKNOWN;
    }

    private final int location;
    private final int locationType;
    private final String fileName;
}
