package com.minigod.zero.bpmn.module.account.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: AccountDetailVO
 * @Description:
 * @Author chenyu
 * @Date 2024/2/20
 * @Version 1.0
 */
@Data
public class AccountDetailVO implements Serializable {
    private static final long serialVersionUID = 7658118290589771303L;

    //个人开户信息
    private AccountOpenInfoVO info;
    //个人ca认证
    private AccountCaVerityInfoVO caVerityInfo;
    //个人银行四要素
    private AccountBankVerityInfoVO bankVerityInfo;
    //个人开户资料信息
    private List<AccountOpenImageVO> images;
    //个人开户税务信息
    private List<AccountAdditionalFileVO> additionalFiles;
    //个人财产种类信息
    private List<AccountPropertyTypeVO> propertyTypes;
    //个人风险披露
    private List<AccountRiskDisclosureVO> riskDisclosures;
    //税务
    private List<AccountTaxationInfoVO> taxationInfos;
    //身份信息披露
    private AccountIdentityConfirmVO accountIdentityConfirm;
    //w8
    private AccountW8benInfoVO AccountW8benInfo;
    //补充证件
    private AccountSupplementCertificateVO AccountSupplementCertificate;
    //入金信息
    private AccountDepositInfoVO AccountDepositInfo;

    private OfficerSignatureTatementVO accountOfficerSignatureTatement;
}
