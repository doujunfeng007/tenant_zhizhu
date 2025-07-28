package com.minigod.zero.customer.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/21 9:42
 * @description：
 */
@Data
public class CustomerOpenAccountInfoDTO implements Serializable {
	private Long custId;
	/**
	 * 邮件
	 */
	private String email;
	/**
	 * 联系国家地区
	 */
	private String contactCountyName;
	/**
	 * 联系地址
	 */
	private String contactAddress;
	/**
	 * 通讯国家地区
	 */
	private String contactRepublicName;
	/**
	 * 通讯地址
	 */
	private String contactDetailAddress;
	/**
	 * 就业类型
	 */
	private Integer professionCode;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 职业职位
	 */
	private Integer jobPosition;
	/**
	 * 业务性质
	 */
	private Integer companyBusinessNature;
	/**
	 * 税务号
	 */
	private String taxNumber;
	/**
	 * 收入类型
	 */
	private Integer annualIncome;
	/**
	 * 具体收入
	 */
	private String annualIncomeOther;
	/**
	 * 净资产类型
	 */
	private Integer net_Asset;
	/**
	 * 净资产金额
	 */
	private String netAssetOther;
	/**
	 * 财富来源类型
	 */
	private String capitalSource;
	/**
	 * 财富来源
	 */
	private String expectedCapitalSource;
	/**
	 * 每月交易金额
	 */
	private Integer tradeAmount;
	/**
	 * 每月交易具体金额
	 */
	private String tradeAmountOther;
	/**
	 * 每月交易频率
	 */
	private Integer tradeFrequency;
}
