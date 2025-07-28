package com.minigod.zero.bpmn.module.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 开户资料地址信息
 *
 * @author eric
 * @since 2024-08-05 15:27:01
 */
@Data
@ApiModel(value = "AccountAddressInfoModifyDTO", description = "开户资料地址信息")
public class AccountAddressInfoModifyDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 日结单及月结单发送方式[0、未知  1、电子邮箱  2、邮寄到住宅地址  3、邮寄到营业地址]
	 */
	@ApiModelProperty(value = "日结单及月结单发送方式[0、未知  1、电子邮箱  2、邮寄到住宅地址  3、邮寄到营业地址]")
	private Integer statementReceiveMode;
	/**
	 * 证件地址
	 */
	@ApiModelProperty(value = "证件地址")
	private String idCardAddress;
	/**
	 * 证件城市
	 */
	@ApiModelProperty(value = "证件城市")
	private String idCardCityName;
	/**
	 * 证件区域
	 */
	@ApiModelProperty(value = "证件区域")
	private String idCardCountyName;
	/**
	 * 证件省份
	 */
	@ApiModelProperty(value = "证件省份")
	private String idCardProvinceName;
	/**
	 * 证件详情
	 */
	@ApiModelProperty(value = "证件详情")
	private String idCardDetailAddress;

	/**
	 * 通讯地址
	 */
	@ApiModelProperty(value = "通讯地址")
	private String contactAddress;

	/**
	 * 联系地址的城市
	 */
	@ApiModelProperty(value = "联系地址的城市")
	private String contactCityName;

	/**
	 * 联系地址的省份
	 */
	@ApiModelProperty(value = "联系地址的省份")
	private String contactProvinceName;

	/**
	 * 联系地址的区域
	 */
	@ApiModelProperty(value = "联系地址的区域")
	private String contactCountyName;

	/**
	 * 通讯地址的国家
	 */
	@ApiModelProperty(value = "通讯地址的国家")
	private String contactRepublicName;

	/**
	 * 联系地址的详细地址
	 */
	@ApiModelProperty(value = "联系地址的详细地址")
	private String contactDetailAddress;

	/**
	 * 住宅地址
	 */
	@ApiModelProperty(value = "住宅地址")
	private String familyAddress;

	/**
	 * 住宅地址的城市
	 */
	@ApiModelProperty(value = "住宅地址的城市")
	private String familyCityName;

	/**
	 * 住宅地址的区域
	 */
	@ApiModelProperty(value = "住宅地址的区域")
	private String familyCountyName;

	/**
	 * 住宅地址的省份
	 */
	@ApiModelProperty(value = "住宅地址的省份")
	private String familyProvinceName;

	/**
	 * 住宅地址的国家
	 */
	@ApiModelProperty(value = "住宅地址的国家")
	private String familyRepublicName;

	/**
	 * 住宅地址的详细地址
	 */
	@ApiModelProperty(value = "住宅地址的详细地址")
	private String familyDetailAddress;

	/**
	 * 永久居住地址
	 */
	@ApiModelProperty(value = "永久居住地址")
	private String permanentAddress;

	/**
	 * 永久城市
	 */
	@ApiModelProperty(value = "永久城市")
	private String permanentCityName;

	/**
	 * 永久区域
	 */
	@ApiModelProperty(value = "永久区域")
	private String permanentCountyName;

	/**
	 * 永久省份
	 */
	@ApiModelProperty(value = "永久省份")
	private String permanentProvinceName;

	/**
	 * 永久居住地址的国家
	 */
	@ApiModelProperty(value = "永久居住地址的国家")
	private String permanentRepublicName;

	/**
	 * 永久详情
	 */
	@ApiModelProperty(value = "永久居住详细地址")
	private String permanentDetailAddress;
}
