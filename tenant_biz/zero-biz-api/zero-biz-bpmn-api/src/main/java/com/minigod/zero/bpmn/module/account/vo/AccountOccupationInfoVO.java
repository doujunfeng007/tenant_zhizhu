package com.minigod.zero.bpmn.module.account.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 客户职业信息返回对象
 *
 * @author eric
 * @since 2024-08-05 16:05:04
 */
@Data
@ApiModel(value = "AccountOccupationInfoModifyVO", description = "客户职业信息返回对象")
public class AccountOccupationInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 就业情况类型[1-全职 2-在职 3-自雇 4-待业 5-其他]
	 */
	@ApiModelProperty(value = "就业情况类型[1-全职 2-在职 3-自雇 4-待业 5-其他]")
	private Integer professionCode;
	/**
	 * 就业情况类型名称
	 */
	@ApiModelProperty(value = "就业情况类型名称")
	private String professionCodeName;
	/**
	 * 就业情况其它类型
	 */
	@ApiModelProperty(value = "就业情况其它类型")
	private String professionCodeOther;
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
	 * 公司电话
	 */
	@ApiModelProperty(value = "公司电话")
	private String companyPhoneNumber;

	/**
	 * 公司地址的城市
	 */
	@ApiModelProperty(value = "公司地址的城市")
	private String companyCityName;

	/**
	 * 公司地址的区域
	 */
	@ApiModelProperty(value = "公司地址的区域")
	private String companyCountyName;

	/**
	 * 公司地址的省份
	 */
	@ApiModelProperty(value = "公司地址的省份")
	private String companyProvinceName;

	/**
	 * 公司地址的国家
	 */
	@ApiModelProperty(value = "公司地址的国家")
	private String companyRepublicName;

	/**
	 * 公司地址的详细地址
	 */
	@ApiModelProperty(value = "公司地址的详细地址")
	private String companyDetailAddress;

	/**
	 * 职位级别[1-普通员工 2-中层管理 3-高层管理]
	 */
	@ApiModelProperty(value = "职位级别[1-普通员工 2-中层管理 3-高层管理]")
	private String jobPosition;

	/**
	 * 职位级别名称
	 */
	@ApiModelProperty(value = "职位级别名称")
	private String jobPositionName;

	/**
	 * 其他职位
	 */
	@ApiModelProperty(value = "其他职位")
	private String jobPositionOther;

	/**
	 * 公司邮箱
	 */
	@ApiModelProperty(value = "公司邮箱")
	private String companyEmail;

	/**
	 * 公司传真
	 */
	@ApiModelProperty(value = "公司传真")
	private String companyFacsimile;

	/**
	 * 公司业务性质
	 */
	@ApiModelProperty(value = "公司业务性质")
	private Integer companyBusinessNature;

	/**
	 * 公司业务性质名称
	 */
	@ApiModelProperty(value = "公司业务性质名称")
	private String companyBusinessNatureName;

	/**
	 * 公司其它业务性质
	 */
	@ApiModelProperty(value = "公司其它业务性质")
	private String companyBusinessNatureOther;
}
