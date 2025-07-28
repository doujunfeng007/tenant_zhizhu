package com.minigod.zero.trade.vo.strategy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author:yanghu.luo
 * @create: 2023-06-10 19:35
 * @Description: 费用以及费项
 */
@Data
public class FeeVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 费用
	 */
	private BigDecimal fee;
	/**
	 * 订单金额+(-)费用
	 * 买入方向：预估金额 = 委托订单金额 + 费用
	 * 卖出方向：预估金额 = 委托订单金额 - 费用
	 */
	private BigDecimal netBalance;

	/**
	 * 佣金类型 0-百分比，1-每股，2-每笔
	 */
	private Integer feeType;
	/**
	 * 佣金费率
	 */
	private String feeRatio;
	/**
	 * 佣金最低金额
	 */
	private String minFare;
	/**
	 * 佣金
	 */
	@JsonProperty(value = "feeFare0",access = JsonProperty.Access.WRITE_ONLY)
	private Boolean feeFare0 = false;
	/**
	 * 平台费用
	 */
	@JsonProperty(value = "feeFare5",access = JsonProperty.Access.WRITE_ONLY)
	private Boolean feeFare5 = false;
}
