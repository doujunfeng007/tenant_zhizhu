package com.minigod.zero.trade.vo.sjmb.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName JournalOrdersVO.java
 * @Description TODO
 * @createTime 2023年09月28日 13:57:00
 */
@Data
public class HistoryOrderVO implements Serializable {

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
	 * 委托类别
	 */
	private String exchangeType;

	/**
	 * 委托价格
	 */
	private BigDecimal price;

	/**
	 * 委托数量
	 */
	private BigDecimal qty;

	/**
	 * 成交价格
	 */
	private BigDecimal businessPrice;

	/**
	 * 成交数量
	 */
	private BigDecimal businessQty;
	/**
	 * 成交时间
	 */
	private String businessTime;

	/**
	 * 成交金额
	 */
	private BigDecimal businessBalance;

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


	private int lotSize;
	private String currency;
	private String rejectReason;
}
