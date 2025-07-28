package com.minigod.zero.bpm.vo.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * edda请求数据
 *
 * @author Zhe.Xiao
 */
@Data
public class CashEddaInfoReqVo implements Serializable {
	/**
	 * 数据Id
	 */
	private Long keyId;
	/**
	 * 银行 1大陆 2香港 3其他
	 */
	private Integer bankType;
	/**
	 * 交易账户
	 */
	private String clientId;
	/**
	 * 资金帐号
	 */
	private String fundAccount;
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
	 * 存入账户
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
	 * 状态 0未授权 1授权中 2授权失败 3授权成功 4解除授权
	 */
	private Integer eddaState;
	/**
	 * 失败原因
	 */
	private String eddaFailReason;
	/**
	 * 单笔限额
	 */
	private BigDecimal bankQuota;
	/**
	 * icon
	 */
	private String icon;
	/**
	 * 银行预留手机号
	 */
	private String bankPhone;
	/**
	 * 汇丰edda短信验证码
	 */
	private String otpPassword;

}
