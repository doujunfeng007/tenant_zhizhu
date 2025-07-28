package com.minigod.zero.bpmn.module.stocktransfer.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CashSharesDataDto implements Serializable {
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Integer id;

	/**
	 * 股票信息
	 */
	@ApiModelProperty(value = "股票信息")
	private String sharesName;
	/**
	 * 股票代码
	 */
	@ApiModelProperty(value = "股票代码")
	private String sharesCode;
	/**
	 * 转仓数量
	 */
	@ApiModelProperty(value = "转仓数量")
	private Integer sharesNum;
	/**
	 * 股票类型 1港股 2美股
	 */
	@ApiModelProperty(value = "股票类型 1港股 2美股")
	private Integer sharesType;
	/**
	 * 是否全部加载 0 否 1 是
	 */
	@ApiModelProperty(value = "是否全部加载 0 否 1 是")
	private Integer isFind;
	/**
	 * 关联转入股票表
	 */
	@ApiModelProperty(value = "关联转入股票表")
	private Integer stockId;
}
