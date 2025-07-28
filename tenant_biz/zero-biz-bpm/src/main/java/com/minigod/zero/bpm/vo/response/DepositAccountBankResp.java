package com.minigod.zero.bpm.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: sunline
 * @date: 2018/11/21 14:19
 * @version: v1.0
 */
@Data
public class DepositAccountBankResp implements Serializable{
    private static final long serialVersionUID = -6793554936144510254L;
	private String bankName; // 银行名称
	private String bankAccount; // 银行账号
	private String bankAccountName; // 账户户名
	private Integer bankType; // 银行卡类型 1:大陆银行卡 2:香港银行卡
	private String bankCode; // 银行名称
	private Integer comFund; //其他入金：0：不支持，1：支持
	private Integer eddaFund;//edda入金：0：不支持，1：支持
	private String bindSource;//首次绑定来源：edda ；other
	private String bankId;//银行机构号
	private String swiftCode;//swiftCode
}
