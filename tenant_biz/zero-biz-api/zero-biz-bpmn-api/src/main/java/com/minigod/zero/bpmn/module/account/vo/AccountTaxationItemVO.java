package com.minigod.zero.bpmn.module.account.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "AccountTaxationItemVO", description = "税务信息项")
public class AccountTaxationItemVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 是否有税务编号[0=否 1=是]
	 */
	@ExcelProperty(value = "是否有税务编号[0=否 1=是]")
	@ApiModelProperty("是否有税务编号[0=否 1=是]")
	private Integer hasTaxCode;
	/**
	 * 理由描述
	 */
	@ExcelProperty(value = "理由描述")
	@ApiModelProperty("理由描述")
	private String reasonDesc;
	/**
	 * 税务编号
	 */
	@ExcelProperty(value = "税务编号")
	@ApiModelProperty("税务编号")
	private String taxCode;
	/**
	 * 理由类型
	 */
	@ExcelProperty(value = "理由类型")
	@ApiModelProperty("理由类型")
	private String taxFeasonType;
	/**
	 * 司法管辖区
	 */
	@ExcelProperty(value = "司法管辖区")
	@ApiModelProperty("司法管辖区")
	private String taxJurisdiction;
}
