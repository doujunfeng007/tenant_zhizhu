package com.minigod.zero.biz.common.mkt.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-07-03 14:55
 * @Description: esop查询K线数据
 */
@Data
public class StkDayVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 资产ID
	 */
	@ApiModelProperty(value = "资产ID")
	private String assetId;

	/**
	 * 开始日期
	 */
	@ApiModelProperty(value = "开始日期")
	private String startDate;

	/**
	 * 结束日期
	 */
	@ApiModelProperty(value = "结束日期")
	private String endDate;
}
