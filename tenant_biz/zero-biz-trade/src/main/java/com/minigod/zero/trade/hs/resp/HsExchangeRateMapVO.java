package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 恒生汇率对象
 */
public class HsExchangeRateMapVO implements Serializable {
	// 目标币种
	private String toMoneyType;
	// key=源币种,value=目标币种汇率对象
	private Map<String, HsExchangeRateVO> exchangeRateMap = new HashMap<>();

	private Date updateTime;//更新时间

	public String getToMoneyType() {
		return toMoneyType;
	}

	public void setToMoneyType(String toMoneyType) {
		this.toMoneyType = toMoneyType;
	}

	public Map<String, HsExchangeRateVO> getExchangeRateMap() {
		return exchangeRateMap;
	}

	public void setExchangeRateMap(Map<String, HsExchangeRateVO> exchangeRateMap) {
		this.exchangeRateMap = exchangeRateMap;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
