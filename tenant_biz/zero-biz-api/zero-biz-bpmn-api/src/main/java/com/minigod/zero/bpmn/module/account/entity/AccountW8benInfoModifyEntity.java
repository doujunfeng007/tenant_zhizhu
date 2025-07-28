package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  W-8BEN表格美国税务局表修改信息
 *
 * @author eric
 * @date 2024-08-02 18:52:01
 */
@Data
@TableName("open_account_w8ben_info_modify")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountW8benInfoModify对象", description = "W-8BEN表格美国税务局表修改信息")
public class AccountW8benInfoModifyEntity extends TenantEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 申请记录ID
	 */
	@ApiModelProperty(value = "申请记录ID")
	private Long applyId;

	/**
	 * 预约流水号
	 */
	@ApiModelProperty(value = "预约流水号")
	private String applicationId;

	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Long userId;

	/**
	 * 永久居住地址国家
	 */
	@ApiModelProperty(value = "永久居住地址国家")
	private String w8PermanentResidenceAddressCountry;

	/**
	 * 永久居住地省市区
	 */
	@ApiModelProperty(value = "永久居住地省市区")
	private String w8PermanentResidenceProvinceCityCounty;

	/**
	 * 永久居住地址
	 */
	@ApiModelProperty(value = "永久居住地址")
	private String w8PermanentResidenceAddress;

	/**
	 * 邮寄地址国家
	 */
	@ApiModelProperty(value = "邮寄地址国家")
	private String w8MailingAddressCountry;

	/**
	 * 省市区
	 */
	@ApiModelProperty(value = "省市区")
	private String w8MailingAddressProvinceCityCounty;

	/**
	 * 邮寄详细地址
	 */
	@ApiModelProperty(value = "邮寄详细地址")
	private String w8MailingAddress;
}
