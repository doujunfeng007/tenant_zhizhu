package com.minigod.zero.bpm.vo.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: 客户出金数据实体类
 * @author: sunline
 * @date: 2019/4/8 11:22
 * @version: v1.0
 */
@Data
public class ClientFundWithdrawApplyProtocol {

	//交易帐号
	private String clientId;
	//用户号
	private Long custId;
	//资金帐号
	private String fundAccount;
	//收款人名称
	private String clientNameSpell;
	//提取方式[0-香港银行卡 1-大陆银行卡]
	private Integer withdrawType;
	//收款银行名称
	private String bankName;
	//收款银行帐户
	private String bankNo;
	//SWIFT代码
	private String swiftCode;
	//联系地址
	private String contactAddress;
	//币种代码[0-人民币 1-美元 2-港币]
	private String moneyType;
	//提取金额
	private BigDecimal occurBalance;
	//冻结资金
	private BigDecimal frozenBalance;
	//银行代码
	private String bankCode;

	//提取手续费
	private BigDecimal chargeMoney;
	// 出金凭证
	private List<String> withdrawImage;
	//银行机构号
	private String bankId;
	//出金方式
	private Integer busType;
	//中台出金记录id
	private Long bizId;
	//资金冻结反向流水号
	private String revertSerialNo;
	//资金冻结日期
	private String initDate;
}
