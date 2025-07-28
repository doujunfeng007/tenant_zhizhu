package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  客户开户详细资料修改表
 *
 * @author eric
 * @since 2024-08-02 17:25:14
 */
@Data
@TableName("customer_account_open_info_modify")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountOpenInfoModify对象", description = "客户开户详细资料修改表")
public class AccountOpenInfoModifyEntity extends TenantEntity {
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
	 * 住所电话
	 */
	@ApiModelProperty(value = "住所电话")
	private String familyPhone;

	/**
	 * 婚姻：1.单身 2.已婚
	 */
	@ApiModelProperty(value = "婚姻：1.单身 2.已婚")
	private Integer maritalStatus;

	/**
	 * 国籍
	 */
	@ApiModelProperty(value = "国籍")
	private String nationality;

	/**
	 * 身份证生效日期
	 */
	@ApiModelProperty(value = "身份证生效日期")
	private String idCardValidDateStart;

	/**
	 * 身份证失效日期
	 */
	@ApiModelProperty(value = "身份证失效日期")
	private String idCardValidDateEnd;

	/**
	 * 教育程度:1.小学或以下 2.中学 3.大专 4.大学或以上
	 */
	@ApiModelProperty(value = "教育程度:1.小学或以下 2.中学 3.大专 4.大学或以上")
	private Integer educationLevel;

	/**
	 * 电子邮箱
	 */
	@ApiModelProperty(value = "电子邮箱")
	private String email;

	/**
	 * 日结单及月结单发送方式[0、未知  1、电子邮箱  2、邮寄到住宅地址  3、邮寄到营业地址]
	 */
	@ApiModelProperty(value = "日结单及月结单发送方式[0、未知  1、电子邮箱  2、邮寄到住宅地址  3、邮寄到营业地址]")
	private Integer statementReceiveMode;

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
	@ApiModelProperty(value = "永久详情")
	private String permanentDetailAddress;

	/**
	 * 就业情况类型[1-全职 2-在职 3-自雇 4-待业 5-其他]
	 */
	@ApiModelProperty(value = "就业情况类型[1-全职 2-在职 3-自雇 4-待业 5-其他]")
	private Integer professionCode;

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
	 * 公司其它业务性质
	 */
	@ApiModelProperty(value = "公司其它业务性质")
	private String companyBusinessNatureOther;

	/**
	 * 年收入范围类型[1=<25万 2=25万-50万 3=50万-100万 4=>100万]
	 */
	@ApiModelProperty(value = "年收入范围类型[1=<25万 2=25万-50万 3=50万-100万 4=>100万]")
	private Integer annualIncome;

	/**
	 * 年收入具体金额
	 */
	@ApiModelProperty(value = "年收入具体金额")
	private String annualIncomeOther;

	/**
	 * 净资产
	 */
	@ApiModelProperty(value = "净资产")
	private Integer netAsset;

	/**
	 * 净资产具体金额
	 */
	@ApiModelProperty(value = "净资产具体金额")
	private String netAssetOther;

	/**
	 * 财富来源[1-工资 2-业务利润 3-投资获取的资本 4-租金 5-退休金 6-家人成员提供 7-其他]
	 */
	@ApiModelProperty(value = "财富来源[1-工资 2-业务利润 3-投资获取的资本 4-租金 5-退休金 6-家人成员提供 7-其他]")
	private String capitalSource;

	/**
	 * 最初资金其它收入来源描述
	 */
	@ApiModelProperty(value = "最初资金其它收入来源描述")
	private String capitalSourceOther;

	/**
	 * 预期财富来源[1-工资 2-业务利润 3-投资获取的资本 4-租金 5-退休金 0-其他]
	 */
	@ApiModelProperty(value = "预期财富来源[1-工资 2-业务利润 3-投资获取的资本 4-租金 5-退休金 0-其他]")
	private String expectedCapitalSource;

	/**
	 * 预期资金其它收入来源
	 */
	@ApiModelProperty(value = "预期资金其它收入来源")
	private String expectedCapitalSourceOther;

	/**
	 * 每月交易金额
	 */
	@ApiModelProperty(value = "每月交易金额")
	private Integer tradeAmount;

	/**
	 * 每月交易具体金额
	 */
	@ApiModelProperty(value = "每月交易具体金额")
	private String tradeAmountOther;

	/**
	 * 使用/交易频率[1-每日 2-每周 3-每月 4-每季 5-每半年 6-每年 0-其他]
	 */
	@ApiModelProperty(value = "使用/交易频率[1-每日 2-每周 3-每月 4-每季 5-每半年 6-每年 0-其他]")
	private Integer tradeFrequency;

	/**
	 * 其他使用/交易频率说明
	 */
	@ApiModelProperty(value = "其他使用/交易频率说明")
	private String tradeFrequencyOther;
}
