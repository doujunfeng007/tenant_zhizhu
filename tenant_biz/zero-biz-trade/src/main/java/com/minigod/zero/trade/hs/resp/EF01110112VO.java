package com.minigod.zero.trade.hs.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class EF01110112VO implements Serializable {

	/**
	 * 委托编号
	 */
	@JSONField(name="entrust_no_x")
	private String orderNo;

	/**
	 * 委托时间
	 */
	private String orderTime;
	/**
	 * 资产ID
	 */
	@JSONField(name="assetId")
	private String assetId;

	/**
	 * 资产ID
	 */
	@JSONField(name="stock_code")
	private String stockCode;

	/**
	 * 资产名称
	 */
	@JSONField(name="stock_name")
	private String stockName;
	/**
	 * 股票简体名称
	 */
	@JSONField(name="stock_namegb")
	private String stockNameZhCN;
	/**
	 * 股票繁体名称
	 */
	@JSONField(name="stock_namebig5")
	private String stockNameZhHK;
	/**
	 * 资产类别
	 */
	@JSONField(name="stock_type")
	private int secSType;

	/**
	 * 买卖方向
	 */
	@JSONField(name="entrust_bs")
	private String bsFlag;

	/**
	 * 委托类别
	 */
	@JSONField(name="exchange_type")
	private String exchangeType;

	/**
	 * 委托价格
	 */
	@JSONField(name="entrust_price")
	private String price;

	/**
	 * 委托数量
	 */
	@JSONField(name="entrust_amount")
	private String qty;

	/**
	 * 成交数量
	 */
	@JSONField(name="businessPrice")
	private String businessPrice;

	/**
	 * 成交数量
	 */
	@JSONField(name="business_amount")
	private String businessQty;
	/**
	 * 成交时间
	 */
	@JSONField(name="business_time")
	private String businessTime;

	/**
	 * 成交金额
	 */
	@JSONField(name="business_balance")
	private String businessBalance;

	/**
	 * 订单类型
	 */
	@JSONField(name="entrust_prop")
	private String orderType;

	/**
	 * 委托状态
	 */
	@JSONField(name="entrust_status")
	private String orderStatus;

	/**
	 * 委托日期
	 */
	@JSONField(name="curr_date")
	private String entrustDate;

	/**
	 * 委托时间
	 */
	@JSONField(name="entrust_time")
	private String entrustTime;

	/**
	 * 委托属性 0-限价单(美股限价单)，d-竞价单，g-竞价限价单，h-限价单(港股限价单)，e-增强限价单，j-特别限价单，u-碎股单(港股碎股单)
	 */
	@JSONField(name="entrust_prop")
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

}


