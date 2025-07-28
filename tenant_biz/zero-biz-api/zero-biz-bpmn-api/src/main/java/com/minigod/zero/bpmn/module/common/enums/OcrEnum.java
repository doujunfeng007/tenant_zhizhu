package com.minigod.zero.bpmn.module.common.enums;

import lombok.Getter;

public final class OcrEnum {
    @Getter
    public enum IdCardSide {
        FRONT(1, "FRONT"),
        BACK(2, "BACK");


        private Integer code;
        private String value;

        private IdCardSide(Integer code, String value) {
            this.code = code;
            this.value = value;
        }

        public static IdCardSide getData(Integer code) {
            for (IdCardSide data : IdCardSide.values()) {
                if (code.equals(data.getCode())) {
                    return data;
                }
            }
            return null;
        }
    }

    ;

    // 护照类型 1 CN | 2 HK | 3 GENERAL | 4 THAI
    @Getter
    public enum PassportType {
        CN(1, "CN"),
        HK(2, "HK"),
        GENERAL(3, "GENERAL"),
        THAI(4, "THAI");


        private Integer code;
        private String value;

        private PassportType(Integer code, String value) {
            this.code = code;
            this.value = value;
        }

        public static PassportType getData(Integer code) {
            for (PassportType data : PassportType.values()) {
                if (code.equals(data.getCode())) {
                    return data;
                }
            }
            return null;
        }
    }


    @Getter
    public enum CardType {
        IdCardOCR(1, "身份证"),
        PassportOCR(3, "护照"),
        HKIdCard(2,"香港身份证"),
        BankCardOCR(4, "银行卡"),
        PermitOCR(5, "港澳通行证"),
        MLIDPassport(6,"港澳台地区及境外护照"),
        HmtResidentPermit(7,"港澳台居住证");

        private Integer code;
        private String message;

        private CardType(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public static boolean isContainCertType(Integer code) {
            boolean bool = false;
            for (CardType type : CardType.values()) {
                if (code.equals(type.getCode())) {
                    bool = true;
                }
            }
            return bool;
        }
    }

    @Getter
    public enum ImageLocation {
        BankCard("2", "201", "银行卡"),
        IdCardFront("1", "101", "身份证正面"),
        IdCardBack("1", "102", "身份证反面"),
        Permit("1", "108", "港澳通行证"), // 港澳台居住证身份书
        Passport("1", "106", "护照"),
        HKIdCard("1","105","香港身份证"),
        MLIDPassport("1","104","港澳台地区及境外护照"),
        HmtResidentPermit("1","107","港澳台居住证"),
        LivingVideo("1","901","用户视频");


        private String locationKey;
        private String locationType;
        private String message;

        private ImageLocation(String locationKey, String locationType, String message) {
            this.locationKey = locationKey;
            this.locationType = locationType;
            this.message = message;
        }
    }

}
