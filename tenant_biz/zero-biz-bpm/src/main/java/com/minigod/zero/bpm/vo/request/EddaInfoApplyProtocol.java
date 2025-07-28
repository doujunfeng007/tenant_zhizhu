package com.minigod.zero.bpm.vo.request;

import lombok.Data;

@Data
public class EddaInfoApplyProtocol {
	/**
	 * 流水号
	 */
	private String applicationId;
	/**
	 * 交易帐号
	 */
	private String clientId;
	/**
	 * 资金帐号
	 */
	private String fundAccount;
	/**
	 * 银行 1大陆 0香港 2其他
	 */
	private Integer bankType;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 银行代码
	 */
	private String bankCode;
	/**
	 * 银行bankid
	 */
	private String bankId;
	/**
	 * 存入银行账户
	 */
	private String depositAccount;
	/**
	 * 存入账户名称
	 */
	private String depositAccountName;
	/**
	 * 存入账户类型:1 港币账户; 2 综合多币种账户
	 */
	private Integer depositAccountType;
	/**
	 * 银行开户证件类型:1 中华人民共和国居民身份证, 2 中华人民共和国往来港澳通行证, 3 香港居民身份证, 4 护照
	 */
	private Integer bankIdKind;
	/**
	 * 银行开户证件号码
	 */
	private String bankIdNo;
	/**
	 * 收款银行core
	 */
	private String benefitBankCore;
	/**
	 * 收款账号
	 */
	private String benefitNo;
	/**
	 * 收款账户名称
	 */
	private String benefitAccount;
	/**
	 * 银行预留手机号
	 */
	private String bankPhone;

	/**
	 * 汇丰edda返回的授权请求唯一标识
	 */
	private String mandateIdentification;
	/**
	 * 汇丰edda短信验证的唯一标识
	 */
	private String otpIdentificationNumber;
	/**
	 * 汇丰edda短信验证码
	 */
	private String otpPassword;
}
