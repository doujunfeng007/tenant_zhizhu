package com.minigod.zero.trade.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 存入资金
 */
@Data
@ApiModel(value = "存入资金", description = "存入资金")
public class CashDepositVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "资金账号")
	private String capitalAccount;

	@ApiModelProperty(value = "币种 0-人民币 1-美元 2-港币")
	private String moneyType;

	@ApiModelProperty(value = "手续费币种")
	private String feeMoneyType;

	@ApiModelProperty(value = "发生金额")
	private String occurBalance;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "生效日期（不传取的是系统初始化日期，不能大于系统初始化日期，用于利息回算）")
	private String valueDate;

	@ApiModelProperty(value = "本地备注")
	private String localeRemark;

	@ApiModelProperty(value = "付款银行bankId")
	private String bankId;

	@ApiModelProperty(value = "付款银行账号")
	private String bankAccount;

}
