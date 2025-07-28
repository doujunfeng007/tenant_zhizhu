package com.minigod.zero.data.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 出入金汇总统计
 */
@ApiModel(value = "出入金汇总统计")
@Data
public class DepositAndWithdrawalFundsSummaryVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "港币入金总金额(HKD)")
	private BigDecimal depositHKD = BigDecimal.ZERO;
	@ApiModelProperty(value = "美元入金总金额(USD)")
	private BigDecimal depositUSD = BigDecimal.ZERO;
	@ApiModelProperty(value = "人民币入金总金额(CNY)")
	private BigDecimal depositCNY = BigDecimal.ZERO;

	@ApiModelProperty(value = "今日港币入金总金额(HKD)")
	private BigDecimal todayDepositHKD = BigDecimal.ZERO;
	@ApiModelProperty(value = "今日美元入金总金额(USD)")
	private BigDecimal todayDepositUSD = BigDecimal.ZERO;
	@ApiModelProperty(value = "今日人民币入金总金额(CNY)")
	private BigDecimal todayDepositCNY = BigDecimal.ZERO;

	@ApiModelProperty(value = "港币出金总金额(HKD)")
	private BigDecimal withdrawalHKD = BigDecimal.ZERO;
	@ApiModelProperty(value = "美元出金总金额(USD)")
	private BigDecimal withdrawalUSD = BigDecimal.ZERO;
	@ApiModelProperty(value = "人民币出金总金额(CNY)")
	private BigDecimal withdrawalCNY = BigDecimal.ZERO;

	@ApiModelProperty(value = "今日港币出金总金额(HKD)")
	private BigDecimal todayWithdrawalHKD = BigDecimal.ZERO;
	@ApiModelProperty(value = "今日美元出金总金额(USD)")
	private BigDecimal todayWithdrawalUSD = BigDecimal.ZERO;
	@ApiModelProperty(value = "今日人民币出金总金额(CNY)")
	private BigDecimal todayWithdrawalCNY = BigDecimal.ZERO;

	@ApiModelProperty(value = "所有入金总金额(HKD)")
	private BigDecimal totalDepositHKD = BigDecimal.ZERO;
	@ApiModelProperty(value = "所有出金总金额(HKD)")
	private BigDecimal totalWithdrawalHKD = BigDecimal.ZERO;

	@ApiModelProperty(value = "今日入金总金额(HKD)")
	private BigDecimal todayTotalDepositHKD = BigDecimal.ZERO;
	@ApiModelProperty(value = "今日出金总金额(HKD)")
	private BigDecimal todayTotalWithdrawalHKD = BigDecimal.ZERO;
	@ApiModelProperty(value = "今日净增总金额(HKD)")
	private BigDecimal todayIncreaseTotalHKD = BigDecimal.ZERO;
	@ApiModelProperty(value = "今日净流出总金额(HKD)")
	private BigDecimal todayDecreaseTotalHKD = BigDecimal.ZERO;
	@ApiModelProperty(value = "日入金记录")
	private List<DayDepositRecords> dayDepositRecords;
	@ApiModelProperty(value = "日出金记录")
	private List<DayWithdrawalRecords> dayWithdrawalRecords;

	@Data
	@ApiModel(value = "日入金记录")
	public static class DayDepositRecords {
		@ApiModelProperty(value = "日期")
		private String day;
		@ApiModelProperty(value = "入金金额")
		private BigDecimal depositHKD;
	}

	@Data
	@ApiModel(value = "日出金记录")
	public static class DayWithdrawalRecords {
		@ApiModelProperty(value = "日期")
		private String day;
		@ApiModelProperty(value = "出金金额")
		private BigDecimal withdrawalHKD;
	}
}
