package com.minigod.zero.trade.vo.resp.options;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chen
 * @ClassName UserPortfolioVO.java
 * @Description TODO
 * @createTime 2024年08月28日 14:20:00
 */
@Data
public class UserOrderVO {

	@ApiModelProperty(value = "期权代码")
	private String optionsCode;

	@ApiModelProperty(value = "期权名称")
	private String optionsName;

	@ApiModelProperty(value = "委托数量")
	private Long entrustQty;

	@ApiModelProperty(value = "委托价格")
	private BigDecimal entrustPrice;

	@ApiModelProperty(value = "买卖方向")
	private String entrustBs;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "委托时间")
	private Date entrustTime;

	@ApiModelProperty(value = "期限")
	private String orderValidity;

	@ApiModelProperty(value = "委托属性")
	private String entrustProp;

	@ApiModelProperty(value = "订单号")
	private String orderId;


}
