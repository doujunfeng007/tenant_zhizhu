package com.minigod.zero.bpmn.module.account.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: OccupationInfo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/7
 * @Version 1.0
 */
@Data
public class OccupationInfo implements Serializable {
    private static final long serialVersionUID = 3292358324087632087L;

    private String companyName;
    private String companyPhoneNumber;
    private String companyPhoneAreaCode;
    private String companyPhoneCityCode;
    private String companyAddress;
    private String companyCityName;
    private String companyCountyName;
    private String companyDetailAddress;
    private String companyProvinceName;
    private String companyRepublicName;
    private String jobPosition;
    private String jobPositionOther;
    private String companyEmail;
    private String companyFacsimileAreaCode;
    private String companyFacsimileCityCode;
    private Integer companyBusinessNature;
    private String companyBusinessNatureOther;
    private Integer professionCode;
    private String companyFacsimile;
}
