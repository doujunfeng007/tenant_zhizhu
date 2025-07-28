package com.minigod.zero.bpmn.module.account.bo;

import com.minigod.zero.bpmn.module.constant.OrganizationOpenAccountMessageConstant;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.util.Date;
import java.util.List;

/**
 * 机构开户参数对象
 *
 * @author eric
 * @since 2024-05-31 14:54:02
 */
@Data
@ApiModel(value = "机构开户参数对象", description = "机构开户参数对象")
public class OrganizationOpenInfoBo {
	/**
	 * 1.线上H5 2.线上APP 3.线下后台
	 */
	@ApiModelProperty(value = "开户方式")
	private Integer openAccountAccessWay;
	/**
	 * 公司名称
	 */
	@ApiModelProperty(value = "公司名称")
	private String companyName;

	/**
	 * 公司地址
	 */
	@ApiModelProperty(value = "公司地址")
	private String companyAddress;

	/**
	 * 公司注册证书(链接)
	 */
	@ApiModelProperty(value = "公司注册证书(链接)")
	private String companyRegistCertUrl;

	/**
	 * 商业登记证书(链接)
	 */
	@ApiModelProperty(value = "商业登记证书(链接)")
	private String businessRegistCertUrl;

	/**
	 * 注册地
	 */
	@ApiModelProperty(value = "注册地")
	private String registrationLocation;

	/**
	 * 业务性质
	 */
	@ApiModelProperty(value = "业务性质")
	private String businessNature;

	/**
	 * 资金来源
	 */
	@ApiModelProperty(value = "资金来源")
	private String fundingSource;

	/**
	 * 联络人
	 */
	@ApiModelProperty(value = "联络人")
	private String contact;

	/**
	 * 联络人电话区号
	 */
	@ApiModelProperty(value = "联络人电话区号")
	private String contactPhoneArea;


	/**
	 * 联络人电话号码
	 */
	@ApiModelProperty(value = "联络人电话号码")
	private String contactPhoneNumber;

	/**
	 * 联络人邮箱
	 */
	@ApiModelProperty(value = "联络人邮箱")
	private String contactEmail;

	/**
	 * 开户用途
	 */
	@ApiModelProperty(value = "开户用途")
	private List<String> purposeOpenAccount;

	/**
	 * 银行名称
	 */
	@ApiModelProperty(value = "银行名称")
	private String bankName;

	/**
	 * 银行代码
	 */
	@ApiModelProperty(value = "银行代码")
	private String swiftCode;

	/**
	 * 风险承受程度:[6=非常进取型 5=进取型 4=中度进取型 3=平稳型 2=中度保守型 1=保守型]
	 */
	@ApiModelProperty(value = "风险承受程度:[6=非常进取型 5=进取型 4=中度进取型 3=平稳型 2=中度保守型 1=保守型]")
	private Integer acceptRisk;

	/**
	 * 失效日期
	 */
	@ApiModelProperty(value = "失效日期")
	private Date expiryDate;

	/**
	 * 银行账户名称
	 */
	@ApiModelProperty(value = "银行账户名称")
	private String accountName;

	/**
	 * 银行账户号码
	 */
	@ApiModelProperty(value = "银行账户号码")
	private String accountNumber;

	/**
	 * 机构开户董事、股东、授权签署信息
	 */
	@ApiModelProperty(value = "机构开户董事、股东、授权签署信息")
	private List<OrganizationOpenShareholderInfoBo> shareholderInfoList;

	public void checkValidate() {
		Validate.notNull(openAccountAccessWay, I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_ACCESSWAY_NULL_NOTICE));
		Validate.notNull(companyName, I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_COMPANY_NAME_NULL_NOTICE));
		Validate.notNull(companyAddress, I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_COMPANY_ADDRESS_NULL_NOTICE));
		Validate.notNull(registrationLocation, I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_REGISTRATION_LOCATION_NULL_NOTICE));
		Validate.notNull(businessNature, I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_BUSINESS_NATURE_NULL_NOTICE));
		Validate.notNull(fundingSource, I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_SOURCE_OF_FUNDS_NULL_NOTICE));
		Validate.notNull(contact, I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_CONTACT_PERSON_NULL_NOTICE));
		Validate.notNull(contactPhoneArea, I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_CONTACT_PERSON_AREA_NULL_NOTICE));
		Validate.notNull(contactPhoneNumber, I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_CONTACT_PERSON_NUMBER_NULL_NOTICE));
		Validate.notNull(contactEmail, I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_CONTACT_EMAIL_NULL_NOTICE));
		Validate.notNull(purposeOpenAccount, I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_PURPOSE_NULL_NOTICE));
		Validate.notNull(bankName, I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_BANK_NULL_NOTICE));
		Validate.notNull(swiftCode, I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_BANK_CODE_NULL_NOTICE));
		if (openAccountAccessWay == 3) {
			Validate.notNull(acceptRisk, I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_RISK_LEVEL_NULL_NOTICE));
		}
		Validate.notNull(accountName, I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_BANK_ACCOUNT_NAME_NULL_NOTICE));
		Validate.notNull(accountNumber, I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_BANK_ACCOUNT_NUMBER_NULL_NOTICE));
		if (shareholderInfoList != null && !shareholderInfoList.isEmpty()) {
			shareholderInfoList.forEach(shareholderInfo -> {
				shareholderInfo.checkValidate();
			});
		}
	}
}
