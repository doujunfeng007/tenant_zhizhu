package com.minigod.zero.trade.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author:yanghu.luo
 * @create: 2023-06-03 16:09
 * @Description: 条件单信息列表
 */
@Data
public class BpmStrategyOrderListVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 客户ID
	 */
	@ApiModelProperty(value = "客户ID")
	private String custId;
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
	 * 资产ID
	 */
	@ApiModelProperty(value = "资产ID")
	private String assetId;
	/**
	 * 股票名称
	 */
	@ApiModelProperty(value = "股票名称")
	private String stockName;
	/**
	 * 订单编号
	 */
	@ApiModelProperty(value = "订单编号")
	private Long id;

	/**
	 * 有效期 1-当日有效，2-撤单前有效
	 */
	@ApiModelProperty(value = "有效期 1-当日有效，2-撤单前有效")
	private Integer deadlineDate;
	/**
	 * 触发价格
	 */
	@ApiModelProperty(value = "触发价格")
	private BigDecimal strategyPrice;
	/**
	 * 1.上涨到触发价格触发,2.下跌到触发价格触发
	 */
	@ApiModelProperty(value = "1.上涨到触发价格触发,2.下跌到触发价格触发")
	private Integer strategyAction;
	/**
	 * 委托类型：1.指定价格,2.最新价格,3.买一价格,4.卖一价格
	 */
	@ApiModelProperty(value = "委托类型：1.指定价格,2.最新价格,3.买一价格,4.卖一价格")
	private String entrustProp;
	/**
	 * 委托数量
	 */
	@ApiModelProperty(value = "委托数量")
	private Integer entrustAmount;
	/**
	 * 委托价格
	 */
	@ApiModelProperty(value = "委托价格")
	private BigDecimal entrustPrice;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**
	 * 是否允许盘前盘后触发：0否，1是
	 */
	@ApiModelProperty(value = "是否允许盘前盘后触发：0否，1是")
	private Integer discType;
	/**
	 * 委托编号
	 */
	@ApiModelProperty(value = "委托编号")
	private String entrustNo;
	/**
	 * 总数
	 */
	@ApiModelProperty(value = "总数")
	private Long total;
}
