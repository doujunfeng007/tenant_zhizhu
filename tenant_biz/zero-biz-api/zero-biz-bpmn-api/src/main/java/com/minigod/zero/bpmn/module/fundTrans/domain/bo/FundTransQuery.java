package com.minigod.zero.bpmn.module.fundTrans.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class FundTransQuery {
	/**
	 * STOCK_TO_FUND / FUND_TO_STOCK
	 */
	@ApiModelProperty(value = "调拨方向",notes = "STOCK_TO_FUND / FUND_TO_STOCK")
	private String directions;

	@ApiModelProperty(value = "币种",notes = "HKD/USD/CNY")
	private String currency;


	@ApiModelProperty(value = "状态",notes = "1审核中 2已撤销 3已完成")
	private Integer status;

	private Long userId;

	private String withdrawBusinessType;

	private String depositBusinessType;

	public String getWithdrawBusinessType() {
		if(StringUtils.isNotBlank(directions)){
			return  directions.split("_TO_")[0];
		}
		return "";
	}

	public String getDepositBusinessType() {
		if(StringUtils.isNotBlank(directions)){
			return  directions.split("_TO_")[1];
		}
		return "";
	}
}
