package com.minigod.zero.bpmn.module.account.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minigod.zero.bpmn.module.account.api.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: OpenAccountBo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/7
 * @Version 1.0
 */
@Data
public class OpenAccountBo extends BaseAccount implements Serializable {
    private static final long serialVersionUID = 6920958839481424131L;
    @JSONField(name = "riskDisclosurePageInfo")
    @JsonProperty(value = "riskDisclosurePageInfo")
    private RiskDisclosureInfo riskDisclosureInfo;
    @JSONField(name = "personalPageInfo")
    @JsonProperty(value = "personalPageInfo")
    private PersonalInfo personalInfo;
    @JSONField(name = "idCardPageInfo")
    @JsonProperty(value = "idCardPageInfo")
    private IDCardInfo idCardInfo;
    @JSONField(name = "occupationPageInfo")
    @JsonProperty(value = "occupationPageInfo")
    private OccupationInfo occupationInfo;
    @JSONField(name = "taxPageInfo")
    @JsonProperty(value = "taxPageInfo")
    private TaxInfo taxInfo;
    @JSONField(name = "identityConfirmPageInfo")
    @JsonProperty(value = "identityConfirmPageInfo")
    private IdentityConfirmInfo identityConfirmInfo;
    @JSONField(name = "assetInvestmentPageInfo")
    @JsonProperty(value = "assetInvestmentPageInfo")
    private AssetInvestmentInfo assetInvestmentInfo;
    @JSONField(name = "addressPageInfo")
    @JsonProperty(value = "addressPageInfo")
    private AddressInfo addressInfo;
    @JSONField(name = "agreementConfirmPageInfo")
    @JsonProperty(value = "agreementConfirmPageInfo")
    private AgreementConfirmInfo agreementConfirmInfo;
    @JSONField(name = "accountPageInfo")
    @JsonProperty(value = "accountPageInfo")
    private AccountInfo accountInfo;
    @JSONField(name = "depositPageInfo")
    @JsonProperty(value = "depositPageInfo")
    private DepositInfo depositInfo;
    @JsonProperty(value = "imageList")
    private List<ImageInfo> imageList;
    @JsonProperty(value = "bankVerityInfoList")
    private List<BankVerityInfo> bankVerityInfoList;
	@JSONField(name = "verifyFour")
	@JsonProperty(value = "verifyFour")
    private VerifyFour verifyFour;
    private String tenantId;
	/**
	 * 投资知识问卷
	 */
	@JSONField(name = "investKnowledgeQuestionnaires")
	@JsonProperty(value = "investKnowledgeQuestionnaires")
	private InvestQuestionnaires investQuestionnaires;
}
