package com.minigod.zero.bpmn.module.common.bo;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
public class OcrReqParams  implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer openType;
    private Integer cardType; // ocr识别卡类型 1 银行卡 2 内地身份证 3 港澳台通行证 4 护照
    private IdCardOptions idCardOptions;
    private PassportOptions passportOptions;

    @Getter
    public class IdCardOptions implements Serializable {
        private Integer cardSide; // 身份证正反面 1 "FRONT" | 2 "BACK"
        private Object config;
    }

    @Getter
    public class PassportOptions implements Serializable {
        // 护照类型（默认CN）： CN：支持中国大陆居民护照，字段较多，精度更高； HK：支持中国香港护照（部分主要字段）； GENERAL：支持国外护照（部分主要字段）； THAI：支持泰国护照（部分主要字段）
        private Integer passportType; // 护照类型 1 CN | 2 HK | 3 GENERAL | 4 THAI
    }
}
