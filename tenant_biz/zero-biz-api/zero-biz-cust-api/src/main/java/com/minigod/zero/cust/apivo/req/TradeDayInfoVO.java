package com.minigod.zero.cust.apivo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-07-06 15:02
 * @Description: 获取某个日期的交易日信息
 */
@Data
public class TradeDayInfoVO implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "日期：yyyy-MM-dd")
	private String date;

	@ApiModelProperty(value = "市场：HK-香港交易所，ML-A股，US-美国市场")
	private String marketType;
}
