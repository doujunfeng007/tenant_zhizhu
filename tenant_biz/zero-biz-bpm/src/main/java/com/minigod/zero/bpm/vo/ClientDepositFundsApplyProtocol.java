package com.minigod.zero.bpm.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: 客户入金数据实体类
 * @author: sunline
 * @date: 2019/6/17 9:40
 * @version: v1.0
 */
@Data
public class ClientDepositFundsApplyProtocol {
	//中台入金id
	private Long bizId;
	//交易账号
	private String clientId;
	//用户号
	private Long custId;
	//资金账号
	private String fundAccount;
	//swiftCode
	private String swiftCode;
	//入金方式[0-香港银行卡 1-大陆银行卡 2-其他银行]
	private Integer depositType;
	//汇款方式[0-未知 1-网银汇款 2-支票汇款 3-FPS 4-EDDA]
	private Integer remittanceType;
	//汇款银行代码
	private String depositBankCode;
	//汇款银行
	private String depositBank;
	//汇款账号
	private String depositNo;
	//汇款账号名称
	private String depositAccount;
	//收款银行
	private String benefitBank;
	//收款银行代码
	private String benefitBankCode;
	//收款账号
	private String benefitNo;
	//收款账户名称
	private String benefitAccount;
	//申请金额
	private BigDecimal depositBalance;
	//联系地址
	private String contactAddress;
	//申请时间
	private String applicationTime;
	//币种代码[0-人民币 1-美元 2-港币]
	private Integer moneyType;
	//入金凭证
	private List<String> depositImage;
	//汇款银行Id（EDDA汇款使用）
	private String depositBankId;
}
