package com.minigod.zero.bpm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 转仓股票 数据传输对象
 */
@Data
public class CashSharesConfigReqDTO {

	/**
	 * 股票名称
	 */
	@ApiModelProperty(value = "股票名称")
	private String stocksName;

	/**
	 * 转账数量
	 */
	@ApiModelProperty(value = "转账数量")
	private int transferNumber;

	/**
	 * 转入股票  0 港股 1 美股
	 */
	@ApiModelProperty(value = "转入股票  0 港股 1 美股")
	private int isShares;

	/**
	 * 股票代码
	 */
	@ApiModelProperty(value = "股票代码")
	private String stocksCode;

}
