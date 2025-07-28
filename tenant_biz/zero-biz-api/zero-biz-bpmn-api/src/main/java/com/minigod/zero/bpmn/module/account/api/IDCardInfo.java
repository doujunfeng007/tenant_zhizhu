package com.minigod.zero.bpmn.module.account.api;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: IDCardInfo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/7
 * @Version 1.0
 */
@Data
public class IDCardInfo implements Serializable {
    private static final long serialVersionUID = 3292358324087632087L;

    private Integer supIdKind;
    private Integer isPermanentResident;
    @JsonProperty(value = "isHKIdCard")
    @JSONField(name = "isHKIdCard")
    private Integer isHkIdCard;
    private String supIdCardNumber;
    private String supIdCardValidDateEnd;
    private String supIdCardValidDateStart;
    private String supCertificateNo;
    private Integer passportCitizenIdCard;
    private Integer placeOfIssue;
    @JsonProperty(value = "passportHkIdCard")
    @JSONField(name = "passportHkIdCard")
    private Integer passportHkIdCard;
}
