package com.minigod.zero.bpmn.module.account.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minigod.zero.bpmn.module.account.api.TaxItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 税务信息修改表
 * 解析为AccountTaxationInfoModifyEntity对象存储
 *
 * @author eric
 * @since 2024-08-02 19:37:01
 */
@Data
@ApiModel(value = "AccountTaxationInfoModifyDTO", description = "税务信息修改")
public class AccountTaxationInfoModifyDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 永久居住地址国家
	 */
	@ApiModelProperty(value = "永久居住地址国家")
	private String permanentCountryAddress;
	/**
	 * 永久居住地址省份
	 */
	@ApiModelProperty(value = "永久居住地址省份")
	private String permanentProvinceName;

	/**
	 * 永久居住地址城市
	 */
	@ApiModelProperty(value = "永久居住地址城市")
	private String permanentCityName;

	/**
	 * 永久居住地址区县
	 */
	@ApiModelProperty(value = "永久居住地址区县")
	private String permanentCountyName;

	/**
	 * 永久居住地址
	 */
	@ApiModelProperty(value = "永久居住地址")
	private String permanentAddress;

	/**
	 * 邮寄地址国家
	 */
	@ApiModelProperty(value = "邮寄地址国家")
	private String mailingCountryAddress;
	/**
	 * 邮寄地址省份
	 */
	@ApiModelProperty(value = "邮寄地址省份")
	private String mailingProvinceName;

	/**
	 * 邮寄地址城市
	 */
	@ApiModelProperty(value = "邮寄地址城市")
	private String mailingCityName;

	/**
	 * 邮寄地址区县
	 */
	@ApiModelProperty(value = "邮寄地址区县")
	private String mailingCountyName;

	/**
	 * 邮寄地址
	 */
	@ApiModelProperty(value = "邮寄地址")
	private String mailingAddress;
	/**
	 * 税务信息列表
	 * 解析为AccountW8benInfoModifyEntity对象存储
	 */
	@JSONField(name = "taxInfoList")
	@JsonProperty(value = "taxInfoList")
	private List<TaxItem> taxInfoList;
}
