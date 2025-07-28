package com.minigod.zero.trade.vo.sjmb.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author chen
 * @ClassName JournalOrdersVO.java
 * @Description TODO
 * @createTime 2023年09月28日 13:57:00
 */
@Data
public class JournalOrdersVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 委托编号
	 */
	private String orderNo;

	/**
	 * 委托时间
	 */
	private String orderTime;
	/**
	 * 资产ID
	 */
	private String assetId;

	/**
	 * 资产ID
	 */
	private String stockCode;

	/**
	 * 资产名称
	 */
	private String stockName;
	/**
	 * 股票简体名称
	 */
	private String stockNameZhCN;
	/**
	 * 股票繁体名称
	 */
	private String stockNameZhHK;
	/**
	 * 资产类别
	 */
	private int secSType;

	/**
	 * 买卖方向
	 */
	private String bsFlag;

	/**
	 * 市场
	 */
	private String exchangeType;

	/**
	 * 委托价格
	 */
	private BigDecimal price =new BigDecimal("0");

	/**
	 * 委托数量
	 */
	private BigDecimal qty =new BigDecimal("0");

	/**
	 * 成交价格
	 */
	private BigDecimal businessPrice  =new BigDecimal("0");

	/**
	 * 成交数量
	 */
	private BigDecimal businessQty =new BigDecimal("0");
	/**
	 * 成交时间
	 */
	private String businessTime;

	/**
	 * 成交金额
	 */
	private BigDecimal businessBalance =new BigDecimal("0");

	/**
	 * 订单类型
	 */
	private String orderType;

	/**
	 * 委托状态
	 */
	private String orderStatus;

	/**
	 * 委托日期
	 */
	private String entrustDate;

	/**
	 * 委托时间
	 */
	private String entrustTime;

	/**
	 * 委托属性
	 */
	private String entrustProp;

	/**
	 * 是否可撤
	 */
	private String cancelable="1";

	/**
	 * 是否可改
	 */
	private String modifiable="1";

	private int lotSize;
	private String currency;
	private String rejectReason;

	@ApiModelProperty(value = "触发价格")
	private BigDecimal strategyPrice;

	@ApiModelProperty(value = "1.上涨到触发价格触发,2.下跌到触发价格触发")
	private Integer strategyAction;

	@ApiModelProperty(value = "期限 DAY 当日有效  GTC 取消前有效")
	private String orderValidity;

	@ApiModelProperty(value = "条件单的状态：C0.待触发,C1.已取消(撤单),C2.已失效,C3.已触发,C4.已失败")
	private String entrustStatus;

	@ApiModelProperty(value = "条件单有效期 字典trade_strategy_order_validity")
	private Integer deadlineDate;

	/**
	 * STOCK OPTION
	 */
	private String secType;

	/**
	 * 成交明细
	 */
	private List<OrdersDetailVO> ordersDetailList;

	@Data
	public static  class OrdersDetailVO {

		/**
		 * 成交价格
		 */
		private BigDecimal price =new BigDecimal("0");

		/**
		 * 成交数量
		 */
		private BigDecimal qty =new BigDecimal("0");

		/**
		 * 成交时间
		 */
		private String businessTime;
	}
}
