package com.minigod.zero.bpmn.module.account.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AddressInfo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/7
 * @Version 1.0
 */
@Data
public class AddressInfo implements Serializable {
    private static final long serialVersionUID = 3292358324087632087L;

    private String idCardCityName;
    private String idCardCountyName;
    private String idCardProvinceName;
    private String idCardDetailAddress;
    private String placeOfBirth;
    private Integer statementReceiveMode;
    private String contactAddress;
    private String contactCityName;
    private String contactCountyName;
    private String contactDetailAddress;
    private String contactProvinceName;
    private String contactRepublicName;
    private String familyAddress;
    private String familyCityName;
    private String familyCountyName;
    private String familyDetailAddress;
    private String familyProvinceName;
    private String familyRepublicName;
    private String permanentAddress;
    private String permanentCityName;
    private String permanentCountyName;
    private String permanentDetailAddress;
    private String permanentProvinceName;
    private String permanentRepublicName;
}
