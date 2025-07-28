package com.minigod.zero.cms.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 出金银行卡配置信息
 */
@Data
public class SecWithdrawalsBankVo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer sort;//序号
	private Integer bankType;//银行卡类型[1-大陆银行卡 2-香港银行卡]
	private String receiptBankName;//收款银行名称
	private String receiptBankCode;//收款银行编码
	private String chargeMoney;//手续费
	private String chargeMoneyRemark;//手续费备注
	private String timeArrival;//出金到账时间
	private String effectiveTime;//可用时间
	private String swiftCode;//swift编码
    private String appBanklogo; // 银行logo图url
    private String pcBanklogo; // 银行logo图url
    private String bgColor;//背景颜色
	private String bankId;

}
