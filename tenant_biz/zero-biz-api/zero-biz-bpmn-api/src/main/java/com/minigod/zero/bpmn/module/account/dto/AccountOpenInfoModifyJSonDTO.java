package com.minigod.zero.bpmn.module.account.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 客户开户资料修改JSON对象
 *
 * @author: eric
 * @since 2024-08-05 15:24:15
 */
@Data
@ApiModel(value = "客户开户资料修改JSON对象")
public class AccountOpenInfoModifyJSonDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "预约流水号")
	private String applicationId;
	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "客户个人信息")
	@JSONField(name = "personalPageInfo")
	@JsonProperty(value = "personalPageInfo")
	private AccountPersonalInfoModifyDTO accountPersonalInfo;

	@ApiModelProperty(value = "客户地址信息")
	@JSONField(name = "addressPageInfo")
	@JsonProperty(value = "addressPageInfo")
	private AccountAddressInfoModifyDTO accountAddressInfo;

	@ApiModelProperty(value = "客户职业信息")
	@JSONField(name = "occupationPageInfo")
	@JsonProperty(value = "occupationPageInfo")
	private AccountOccupationInfoModifyDTO accountOccupationInfo;

	@ApiModelProperty(value = "客户资产投资信息")
	@JSONField(name = "assetInvestmentPageInfo")
	@JsonProperty(value = "assetInvestmentPageInfo")
	private AccountAssetInvestmentInfoModifyDTO accountAssetInvestmentInfo;

	@ApiModelProperty(value = "客户身份确认信息")
	@JSONField(name = "accountIdentityConfirm")
	@JsonProperty(value = "accountIdentityConfirm")
	private AccountIdentityConfirmModifyDTO accountIdentityConfirm;

	@ApiModelProperty(value = "客户税务信息")
	@JSONField(name = "accountTaxationInfo")
	@JsonProperty(value = "accountTaxationInfo")
	private AccountTaxationInfoModifyDTO accountTaxationInfo;
}
