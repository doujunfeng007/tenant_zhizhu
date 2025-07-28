package com.minigod.zero.bpmn.module.account.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: IdentityConfirmInfo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/7
 * @Version 1.0
 */
@Data
public class IdentityConfirmInfo implements Serializable {
    private static final long serialVersionUID = 3292358324087632087L;

    private Integer isEmployed;
    private Integer isIllegal;
    private Integer isExecutive;
	private Integer isBeneficialOwner;
	private Integer isAccountUltimatelyResponsible;
    private String employedRelation;
    private String illegalDetail;
    private Integer isHaveLinealRelatives;
    private String linealRelativesJobTime;
    private Integer isMateHaveAccount;
    private String mateAccount;
    private String linealRelativesName;
    private String executiveCompanyCode;
    private String employedName;
    private Integer customerRelations;
    private String executiveCompanyName;
    private Integer isStockRightMore35;
    private Integer isRegisterEmployee;
    private String centerCode;
    private String organizationName;
    private String linealRelativesJob;
    private String account;
}
