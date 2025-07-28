package com.minigod.zero.trade.hs.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EF01110148VO implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 交易日期
	 */
	@JSONField(name="init_date")
    private String businessDate;

	/**
	 * 成交时间
	 */
	@JSONField(name="business_time")
	private String businessTime;

	/**
	 * 买卖方向
	 */
	@JSONField(name="entrust_bs")
    private String entrustBs;

	/**
	 * 委托状态
	 */
	@JSONField(name="entrust_status")
    private String entrustStatus;

	/**
	 * 委托价格
	 */
	@JSONField(name="entrust_price")
    private String entrustPrice;

	/**
	 * 委托数量
	 */
	@JSONField(name="entrust_amount")
	private String entrustAmount;

	/**
	 * 成交数量
	 */
	@JSONField(name="business_amount")
	private String businessAmount;

	/**
	 * 成交金额
	 */
	@JSONField(name="business_balance")
	private String businessBalance;

	/**
	 * 股票名称
	 */
	@JSONField(name="stock_name")
	private String stockName;

	/**
	 * 委托日期
	 */
	@JSONField(name="entrust_date")
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
	 * 委托编号
	 */
	@JSONField(name="entrust_no_x")
	private String entrustNo;

	/**
	 * 证券代码
	 */
	@JsonProperty(value = "stock_code",access = JsonProperty.Access.WRITE_ONLY)
	private String stockCode;

	/**
	 * 交易类别
	 */
	@JsonProperty(value = "exchange_type",access = JsonProperty.Access.WRITE_ONLY)
	private String exchangeType;

	/**
	 * 资产ID
	 */
	private String assetId;

	/**
	 * 费用明细
	 */
	private EF01110181VO feeDetail;
}
