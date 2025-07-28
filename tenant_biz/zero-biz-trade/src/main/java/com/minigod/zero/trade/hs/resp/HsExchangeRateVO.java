package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 恒生汇率对象
 */
public class HsExchangeRateVO implements Serializable {
	private int initDate;// 更新日期
	private int validDate; // 有效日期
	private String fromMoneyType;// 源币种
	private String toMoneyType;// 目标币种 '0'人民币 '1'美圆 '2'港币
	private BigDecimal exchangeRate;// 买入汇率
	private BigDecimal reverseRate;// 卖出汇率

	public int getInitDate() {
		return initDate;
	}

	public void setInitDate(int initDate) {
		this.initDate = initDate;
	}

	public String getFromMoneyType() {
		return fromMoneyType;
	}

	public void setFromMoneyType(String fromMoneyType) {
		this.fromMoneyType = fromMoneyType;
	}

	public String getToMoneyType() {
		return toMoneyType;
	}

	public void setToMoneyType(String toMoneyType) {
		this.toMoneyType = toMoneyType;
	}

	public int getValidDate() {
		return validDate;
	}

	public void setValidDate(int validDate) {
		this.validDate = validDate;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public BigDecimal getReverseRate() {
		return reverseRate;
	}

	public void setReverseRate(BigDecimal reverseRate) {
		this.reverseRate = reverseRate;
	}
}
