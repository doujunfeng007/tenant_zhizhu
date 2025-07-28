package com.minigod.zero.bpmn.module.account.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 *  税务信息表
 *
 * @author Chill
 */
@Data
@TableName("open_account_taxation_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountTaxationInfo对象", description = "税务信息表")
public class AccountTaxationInfoEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 预约流水号
	 */
	@ExcelProperty(value = "预约流水号")
	@ApiModelProperty("预约流水号")
	private String applicationId;

	/**
	 * 司法管辖区
	 */
	@ExcelProperty(value = "司法管辖区")
	@ApiModelProperty("司法管辖区")
	private String taxCountry;

	/**
	 * 税务编号
	 */
	@ExcelProperty(value = "税务编号")
	@ApiModelProperty("税务编号")
	private String taxNumber;

	/**
	 * 是否有税务编号[0=否 1=是]
	 */
	@ExcelProperty(value = "是否有税务编号[0=否 1=是]")
	@ApiModelProperty("是否有税务编号[0=否 1=是]")
	private Integer hasTaxNumber;

	/**
	 * 理由类型
	 */
	@ExcelProperty(value = "理由类型")
	@ApiModelProperty("理由类型")
	private String reasonType;

	/**
	 * 理由描述
	 */
	@ExcelProperty(value = "理由描述")
	@ApiModelProperty("理由描述")
	private String reasonDesc;

	/**
	 * 额外披露
	 */
	@ExcelProperty(value = "额外披露")
	@ApiModelProperty("额外披露")
	private String additionalDisclosures;

}
