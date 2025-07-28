package com.minigod.zero.cust.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:yanghu.luo
 * @create: 2023-03-29 14:28
 * @Description: 交易账号绑定
 */
@Data
@ApiModel(value = "交易账号绑定入参对象", description = "交易账号绑定入参对象")
public class TradeAccountBind2faVO extends TradeUnlockReq{

	@ApiModelProperty(value = "交易账号", required = true)
	private String account;

	@ApiModelProperty(value = "交易账号状态", required = true)
	private String status;
}
