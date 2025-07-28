package com.minigod.zero.trade.vo.req.options;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chen
 * @ClassName InquiryPlaceOrderReq.java
 * @Description 询价下单
 * @createTime 2024年08月27日 17:21:00
 */
@Data
public class InquiryPlaceOrderReq {

	@NotBlank(message = "期权代码不能为空")
	@ApiModelProperty(value = "期权代码")
	private String optionsCode;

	@NotBlank(message = "买入/卖出不能为空")
	@ApiModelProperty(value = "买卖方向")
	private String entrustBs;

	@NotBlank(message = "委托属性不能为空")
	@ApiModelProperty(value = "委托属性 LO:Limit Order MO:Market Order")
	private String entrustProp;

	@NotNull(message = "委托价格不能为空")
	@ApiModelProperty(value = "委托价格")
	private BigDecimal entrustPrice;

	@NotNull(message = "委托数量不能为空")
	@ApiModelProperty(value = "委托数量")
	private Long entrustQty;

	@NotBlank(message = "期限不能为空")
	@ApiModelProperty(value = "期限 DAY 当日有效  GTC 取消前有效")
	private String orderValidity;

	@NotBlank(message = "期权账号不能为空")
	@ApiModelProperty(value = "期权账号")
	private String optionsAccount;

	@ApiModelProperty(value = "统一账户")
	private String fundAccount;

	@ApiModelProperty(value = "看涨看跌")
	private String orderType;

}
