package com.minigod.zero.bpmn.module.actionsInfo.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公司行动记录 请求实体
 *
 * @author wengzejie
 * @since 2024-03-13
 */
@Data
public class ActionsInfoBo {

	/**
	 * 交易账号
	 */
	@ApiModelProperty(value = "交易账号")
	private String tradeAccount;

	/**
	 * 股票代码
	 */
	@ApiModelProperty(value = "股票代码")
	private String stockCode;

	/**
	 * 股票名称
	 */
	@ApiModelProperty(value = "股票名称")
	private String stockName;

	/**
	 * 状态[0-未处理 1-已处理 2-已完成 3-已退回]
	 */
	@ApiModelProperty(value = "状态[0-未处理 1-已处理 2-已完成 3-已退回]")
	private Integer status;

}
