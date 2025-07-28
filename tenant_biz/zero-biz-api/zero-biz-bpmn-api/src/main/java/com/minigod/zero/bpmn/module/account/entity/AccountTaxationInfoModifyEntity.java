package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  税务信息修改表
 *
 * @author eric
 * @date 2024-08-02 18:48:01
 */
@Data
@TableName("open_account_taxation_info_modify")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountTaxationInfoModify对象", description = "税务信息修改表")
public class AccountTaxationInfoModifyEntity extends TenantEntity {
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
	 * 司法管辖区
	 */
	@ApiModelProperty(value = "司法管辖区")
	private String taxCountry;

	/**
	 * 税务编号
	 */
	@ApiModelProperty(value = "税务编号")
	private String taxNumber;

	/**
	 * 是否有税务编号[0=否 1=是]
	 */
	@ApiModelProperty(value = "是否有税务编号[0=否 1=是]")
	private Integer hasTaxNumber;

	/**
	 * 理由类型
	 */
	@ApiModelProperty(value = "理由类型")
	private String reasonType;

	/**
	 * 理由描述
	 */
	@ApiModelProperty(value = "理由描述")
	private String reasonDesc;

	/**
	 * 额外披露
	 */
	@ApiModelProperty(value = "额外披露")
	private String additionalDisclosures;
}
