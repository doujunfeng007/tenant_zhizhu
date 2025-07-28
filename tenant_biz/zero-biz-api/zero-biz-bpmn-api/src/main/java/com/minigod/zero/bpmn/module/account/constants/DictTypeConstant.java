package com.minigod.zero.bpmn.module.account.constants;

/**
 * @ClassName: DictTypeConstant
 * @Description:
 * @Author chenyu
 * @Date 2022/8/20
 * @Version 1.0
 */
public interface DictTypeConstant {
	/**
	 * 国家地区字典值
	 */
	String COUNTRY_OR_REGION = "country_or_region";
	String COUNTRY_OR_REGION_EN = "country_or_region_en";
	/**
	 * 系统邮箱地址
	 */
	String SYSTEM_EMAIL_ADDRESS = "system_email_address";
	/**
	 * 税务地区
	 * （废除，不能用，因为跟COUNTRY_OR_REGION的字典码不致，对应不起来，统一使用COUNTRY_OR_REGION）
	 */
	@Deprecated
	String TAX_JURISDICTION = "tax_jurisdiction";
	/**
	 * 职位等级
	 */
	String JOB_POSITION = "job_position";
	/**
	 * 性别
	 */
	String SEX = "sys_user_sex";
	/**
	 * 入金币种
	 */
	String DEPOSIT_CURRENCY = "deposit_currency";
	/**
	 * 转出银行
	 */
	String OUT_BANK = "out_bank";
	/**
	 * 关系
	 */
	String CUSTOMER_RELATIONS = "customer_relations";
	/**
	 * 交易股票频率
	 */
	String TRADE_STOCK_FREQUENCY = "trade_stock_frequency";
	/**
	 * 交易股票金额
	 */
	String TRADE_STOCK_MONEY = "trade_stock_money";
	/**
	 * 国籍
	 */
	String NATIONALITY = "nationality";
	/**
	 * 国籍英文
	 */
	String NATIONALITY_EN = "nationality_en";
	/**
	 * 无税务编号原因
	 */
	String TAX_REASON_VALUE = "tax_reason_value";
	/**
	 * 公司业务性质
	 */
	String BUSINESS_NATURE = "business_nature";
	/***
	 * 职业类别
	 */
	String PROFESSION_CODE = "profession_code";
	/**
	 * 年收入
	 */
	String ANNUAL_INCOME_HKD = "cms_annual_income_HKD";
	/**
	 * 净资产
	 */
	String NET_ASSET = "net_asset";
	/**
	 * 税务认证
	 */
	String TAX_REASON_TYPE = "tax_reason_type";
}
