package com.minigod.zero.trade.vo.strategy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author:yanghu.luo
 * @create: 2023-05-27 11:08
 * @Description: 普通订单和条件单改单撤单
 */
@Data
public class CommonOrderVO implements Serializable {
	private static final long serialVersionUID = -1L;
	/**
	 * 资金帐号
	 */
	@ApiModelProperty(value = "资金帐号")
	private String capitalAccount;
	/**
	 * 资产ID
	 */
	@ApiModelProperty(value = "资产ID")
	private String assetId;
	/**
	 * 委托数量
	 */
	@ApiModelProperty(value = "委托数量")
	private String entrustAmount;
	/**
	 * 委托价格
	 */
	@ApiModelProperty(value = "委托价格")
	private BigDecimal entrustPrice;
	/**
	 * 触发价格
	 */
	@ApiModelProperty(value = "触发价格")
	private BigDecimal strategyPrice;
	/**
	 * 委托编号
	 */
	@ApiModelProperty(value = "委托编号")
	private String entrustNo;
	/**
	 * 订单类型 1-普通订单，2-条件单
	 */
	@ApiModelProperty(value = "订单类型 1-普通订单，2-条件单")
	private Integer orderType;

	/**
	 * 操作类型 1-改单，2-撤单
	 */
	@ApiModelProperty(value = "操作类型 1-改单，2-撤单")
	private Integer operationType;

	/**
	 * esop下单 交易账号
	 */
	@ApiModelProperty(value = "esop下单 交易账号")
	private String tradeAccount;

	/**
	 * esop下单 交易密码
	 */
	@ApiModelProperty(value = "esop下单 交易密码")
	private String password;
	private String moneyType;

	/**
	 * 买卖方向 1-买入，2-卖出
	 */
	@ApiModelProperty(value = "买卖方向 1-买入，2-卖出")
	private String entrustBs;
}
