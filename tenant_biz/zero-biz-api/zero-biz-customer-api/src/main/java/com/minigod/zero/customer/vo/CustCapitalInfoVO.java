package com.minigod.zero.customer.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/7 17:20
 * @description：
 */
@Data
public class CustCapitalInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 交易账号
	 */
	@ApiModelProperty(value = "交易账号")
	private String tradeAccount;
	/**
	 * 资金账号
	 */
	@ApiModelProperty(value = "资金账号")
	private String capitalAccount;
	/**
	 * 账号类型：1-现金 2-融资
	 */
	@ApiModelProperty(value = "账号类型：0-现金 M-保证金")
	private String accountType;
	/**
	 * 币种[0-人民币，1-美元，2-港币]
	 */
	@ApiModelProperty(value = "币种[0-人民币，1-美元，2-港币]")
	private String moneyType;
	/**
	 * 可提取余额
	 */
	@ApiModelProperty(value = "可提取余额")
	private BigDecimal enableBalance;
}

