package com.minigod.zero.trade.vo.req.options;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chen
 * @ClassName InquiryPlaceOrderReq.java
 * @Description 询价下单
 * @createTime 2024年08月27日 17:21:00
 */
@Data
public class InquiryCancelOrderReq {

	@NotBlank(message = "订单号不能为空")
	@ApiModelProperty(value = "订单号")
	private String orderId;



}
