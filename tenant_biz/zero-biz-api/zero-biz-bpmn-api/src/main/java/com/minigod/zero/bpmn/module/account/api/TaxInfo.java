package com.minigod.zero.bpmn.module.account.api;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: TaxInfo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/7
 * @Version 1.0
 */
@Data
public class TaxInfo implements Serializable {
    private static final long serialVersionUID = 3292358324087632087L;
    /**
     * 是否有香港税务资格
     */
    private Integer hkTaxChecked;
    /**
     * 香港税务号
     */
    private String hkTaxCode;

    /**
     * 大陆税务号
     */
    private String cnTaxCode;
    /**
     * 是否有大陆税务资格
     */
    private Integer cnTaxChecked;

    /**
     * 其他地区税务号
     */
    private String otherTaxCode;
    /**
     * 是否有其他其他税务资格
     */
    private Integer otherTaxChecked;
    /**
     * 其他地区税务号
     */
    private Integer hasTaxCode;
    /**
     * 无税务编号原因
     */
    private String taxFeasonType;
    /**
     * 不能取得税务编号原因
     */
    private String reasonDesc;
    /**
     * 额外披露
     */
    private String additionalDisclosures;
    /**
     * 其他管辖区国家字典
     */
    private Integer taxJurisdiction;
    /**
     * Name of Individual
     */
    private String englishName;
    /**
     * Country of Citizenship
     */
    private String englishCountry;
    /**
     * Foreign Tax Identifying Number
     */
    private String identifyingNumber;
    /**
     * Date of Birth
     */
    private String birthDate;
    /**
     * [0=否 1=是]
     */
    private Boolean taxComplianceOne = Boolean.TRUE;
    /**
     * [0=否 1=是]
     */
    private Boolean taxComplianceTwo = Boolean.FALSE;

    private String treatyBenefitsCountry;

    private Boolean treatyBenefits;

    private String permanentCountryAddress;

    private String permanentStateOrProvinceText;

    private String permanentProvinceName;

    private String permanentCityName;

    private String permanentCountyName;

    private String permanentAddress;

    private String mailingCountryAddress;

    private String mailingStateOrProvinceText;

    private String mailingProvinceName;

    private String mailingCityName;

    private String mailingCountyName;

    private String mailingAddress;

    @JSONField(name = "taxInfoList")
    @JsonProperty(value = "taxInfoList")
    private List<TaxItem> taxInfoList;
}
