package com.minigod.zero.trade.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-06-03 15:44
 * @Description: bpm查询条件单请求
 */
@Data
public class BpmStrategyOrderVO implements Serializable {
	private static final long serialVersionUID = -1L;
	/**
	 * 客户ID
	 */
	@ApiModelProperty(value = "客户ID")
	private String custId;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	private String phoneNumber;
	/**
	 * 证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]
	 */
	@ApiModelProperty(value = "证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]")
	private String idKind;
	/**
	 * 证件号码
	 */
	@ApiModelProperty(value = "证件号码")
	private String idCard;
	/**
	 * 交易账号
	 */
	@ApiModelProperty(value = "交易账号")
	private String tradeAccount;
	/**
	 * 资金账号
	 */
	@ApiModelProperty(value = "资金账号")
	private String capitalAccount;
	/**
	 * 条件单类型 股价条件单，止盈止损单
	 */
	@ApiModelProperty(value = "条件单类型")
	private String strategyType;
	/**
	 * 买卖方向：1买入，2卖出
	 */
	@ApiModelProperty(value = "买卖方向：1买入，2卖出")
	private Integer entrustBs;
	/**
	 * 订单状态：C0.待触发,C1.已取消(撤单),C2.已失效,C3.已触发
	 */
	@ApiModelProperty(value = "订单状态：C0.待触发,C1.已取消(撤单),C2.已失效,C3.已触发")
	private String entrustStatus;
	/**
	 * 委托编号
	 */
	@ApiModelProperty(value = "委托编号")
	private String entrustNo;

	/**
	 * 当前页
	 */
	@ApiModelProperty(value = "当前页")
	private Integer current;

	/**
	 * 每页的数量
	 */
	@ApiModelProperty(value = "每页的数量")
	private Integer size;
}
