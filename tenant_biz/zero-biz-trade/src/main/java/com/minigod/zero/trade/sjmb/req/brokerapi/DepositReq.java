package com.minigod.zero.trade.sjmb.req.brokerapi;

import lombok.Data;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * @author chen
 * @ClassName DepositReq.java
 * @Description TODO
 * @createTime 2024年03月01日 14:40:00
 */

@Data
public class DepositReq implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 泰坦系统使用的账号ID
	 */
	private String accountId;

	/**
	 * 资金组
	 */
	private String segmentId;

	/**
	 * 币种
	 */
	private String currency;

	/**
	 * 金额
	 */
	private String amount;

	/**
	 *  转入方银行卡Id，通过[获取公司出入金银行账户]接口查找
	 */
	private String inwardBankAccId;

	/**
	 * 转出方银行卡Id，outwardBankAccount，outwardBankAccId不能同时为空
	 */
	private String outwardBankAccId;


	/**
	 * 转出方银行账号，outwardBankAccount，outwardBankAccId不能同时为空
	 */
	private String outwardBankAccount;

	/**
	 * 转出方银行名称
	 */
	private String outwardBankName;

	/**
	 * 入金凭证1，支持JPG，JPEG，PNG，GIF格式，每个文件大小不超过5M。
	 */
	private List<File> receipts;

	/**
	 * 备注
	 */
	private String remark;


	/**
	 * 流水号
	 */
	private String transactionId;

}
