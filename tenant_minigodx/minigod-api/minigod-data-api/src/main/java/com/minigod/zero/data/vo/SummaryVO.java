package com.minigod.zero.data.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(value = "汇总数据结构")
public class SummaryVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "币种")
	public String currency;
	@ApiModelProperty(value = "结算金额")
	public BigDecimal settlement;
	@ApiModelProperty(value = "昨日余额")
	public BigDecimal lastDayBalance;
	@ApiModelProperty(value = "入金金额")
	public BigDecimal depositBalance;
	@ApiModelProperty(value = "出金金额")
	public BigDecimal withdrawalBalance;

	public static SummaryVO buildSummary(BigDecimal lastDayBalance, BigDecimal depositBalance, BigDecimal withdrawalBalance, BigDecimal settlement) {
		SummaryVO summary = new SummaryVO();
		summary.setCurrency("HKD");
		summary.setDepositBalance(depositBalance);
		summary.setSettlement(settlement);
		summary.setLastDayBalance(lastDayBalance);
		summary.setWithdrawalBalance(withdrawalBalance);
		return summary;
	}
}

