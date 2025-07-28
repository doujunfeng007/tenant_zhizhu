package com.minigod.zero.bpmn.module.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 客户资产投资信息
 *
 * @author eric
 * @since 2024-08-05 16:12:01
 */
@Data
@ApiModel(value = "AccountAssetInvestmentInfoModifyDTO", description = "客户资产投资信息")
public class AccountAssetInvestmentInfoModifyDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 年收入范围类型[1=<25万 2=25万-50万 3=50万-100万 4=>100万]
	 */
	@ApiModelProperty(value = "年收入范围类型[1=<25万 2=25万-50万 3=50万-100万 4=>100万]")
	private Integer annualIncome;

	/**
	 * 年收入范围类型名称[1=<25万 2=25万-50万 3=50万-100万 4=>100万]
	 */
	@ApiModelProperty(value = "年收入范围类型名称[1=<25万 2=25万-50万 3=50万-100万 4=>100万]")
	private String annualIncomeName;

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
	 * 净资产名称
	 */
	@ApiModelProperty(value = "净资产名称")
	private String netAssetName;

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
	 * 每月交易金额名称
	 */
	@ApiModelProperty(value = "每月交易金额名称")
	private String tradeAmountName;

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
