package com.minigod.zero.bpm.vo.response;

import lombok.Data;

/**
 * @description:
 * @author: sunline
 * @date: 2019/6/24 11:56
 * @version: v1.0
 */
@Data
public class DepositSuccessBankInfo {
	private String clientId; //交易账号
	private String fundAccount; //资金账号
	private Integer bankType; //银行卡类型
	private String bankName; //银行名称
	private String bankNo; //银行账号
	private String bankAccount; //银行名称
	private String bankCode; //银行代码
	private Integer comFund; //其他入金：0：不支持，1：支持
	private Integer eddaFund;//edda入金：0：不支持，1：支持
	private String bindSource;//卡绑定来源：edda ；other
	private String bankId;
	private String swiftCode;
}
