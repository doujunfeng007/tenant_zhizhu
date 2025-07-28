package com.minigod.zero.bpmn.module.account.properties;

import lombok.Data;

/**
 * @ClassName: PDFTemplace
 * @Description:
 * @Author chenyu
 * @Date 2024/4/17
 * @Version 1.0
 */
@Data
public class PDFTemplace {
    private String userForm;
	private String iFundUserForm;
    private String userW8;
    private String userSelfProve;
    private String userSelfCertificationOnUs;
    private String standingAuthority;
    private String clientsSignatureCard;
    private String futuresUnpUserSelfProve;
    private String futuresAgreement;
    private String personalDataCollectionStatement;
    private String marginTradingAgreement;
    private String securitiesTradingAgreement;
    private String hongKongStockFeeSchedule;
    private String executeTradingDisclosureDocumentsOnOptimalTerms;
}
