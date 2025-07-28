package com.minigod.zero.trade.hs.resp;

import com.alibaba.fastjson2.annotation.JSONField;

import java.io.Serializable;

public class EF01110173VO extends FundJourRecord implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 证券代码
	 */
	@JSONField(name="stock_code")
	private String stockCode;
	/**
	 * 证券名称
	 */
	@JSONField(name="stock_name")
	private String stockName;
}
