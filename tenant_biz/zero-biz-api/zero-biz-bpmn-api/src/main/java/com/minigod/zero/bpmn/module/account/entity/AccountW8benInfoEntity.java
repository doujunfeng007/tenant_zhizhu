package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 *  W-8BEN表格美国税务局表信息
 *
 * @author Chill
 */
@Data
@TableName("open_account_w8ben_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountW8benInfo对象", description = "W-8BEN表格美国税务局表信息")
public class AccountW8benInfoEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 预约流水号
	 */
	@ApiModelProperty(value = "预约流水号")
	private String applicationId;
	/**
	 * Name of Individual
	 */
	@ApiModelProperty(value = "Name of Individual")
	private String w8IndividualName;
	/**
	 * Country of Citizenship
	 */
	@ApiModelProperty(value = "Country of Citizenship")
	private String w8CitizenshipCountry;
	/**
	 * Permanent Residence Address
	 */
	@ApiModelProperty(value = "Permanent Residence Address")
	private String w8PermanentResidenceAddress;
	/**
	 * Permanent Residence Address Country
	 */
	@ApiModelProperty(value = "Permanent Residence Address Country")
	private String w8PermanentResidenceAddressCountry;
	/**
	 * 省市区
	 */
	@ApiModelProperty(value = "省市区")
	private String w8PermanentResidenceProvinceCityCounty;
	/**
	 * Foreign Tax Identifying Number
	 */
	@ApiModelProperty(value = "Foreign Tax Identifying Number")
	private String w8ForeignTaxIdentifyingNumber;
	/**
	 * Date of Birth
	 */
	@ApiModelProperty(value = "Date of Birth")
	private String w8BirthDay;
	/**
	 * w8_treat_benefits
	 */
	@ApiModelProperty(value = "w8_treat_benefits")
	private Integer w8TreatBenefits;
	/**
	 * w8_treat_benefits_country
	 */
	@ApiModelProperty(value = "w8_treat_benefits_country")
	private String w8TreatBenefitsCountry;
	/**
	 * w8_mailing_address_country
	 */
	@ApiModelProperty(value = "w8_mailing_address_country")
	private String w8MailingAddressCountry;
	/**
	 * 省市区
	 */
	@ApiModelProperty(value = "省市区")
	private String w8MailingAddressProvinceCityCounty;
	/**
	 * w8_mailing_address
	 */
	@ApiModelProperty(value = "w8_mailing_address")
	private String w8MailingAddress;
	/**
	 * [否 是]
	 */
	@ApiModelProperty(value = "[否 是]")
	private Boolean taxComplianceOne;
	/**
	 * [否 是]
	 */
	@ApiModelProperty(value = "[否 是]")
	private Boolean taxComplianceTwo;

}
