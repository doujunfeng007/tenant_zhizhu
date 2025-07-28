package com.minigod.zero.bpmn.module.account.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: PersonalInfo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/7
 * @Version 1.0
 */
@Data
public class PersonalInfo implements Serializable {
    private static final long serialVersionUID = 3292358324087632087L;

    private Boolean longterm;
    private String placeOfBirth;
    private String idNo;
    private String areaNumber;
    private String formerName;
    private String familyName;
    private String birthday;
    private String familyNameSpell;
    private String givenName;
    private String givenNameSpell;
    private Integer maritalStatus;
    private String areaCode;
    private String cityCode;
    private String nationality;
    private String idCardValidDateStart;
    private Integer educationLevel;
    private String idCardValidDateEnd;
    private Integer appellation;
    private String email;
    private Integer sex;
    private Integer idKind;
    private String authority;
    private Integer placeOfIssue;
}
