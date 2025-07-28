package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 421 查询历史委托
 * @author sunline
 *
 */
public class EF01100421VO extends EntrustRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private String entrustDate;
	private String stockNamegb;
	//证券子类型
	private int secSType;
	private String conditionType;// 策略类型（0-OQ订单;1-市价到价单;2-限价到价单）
	private String touchPrice;//
	private String strategyType;
	private String strategyEnddate;// 策略失效日期
	private String strategyStatus;// 策略状态（1-未执行;2-执行中;3-执行完成;4-暂停;5-强制释放）
	private String initDate;

	public String getEntrustDate() {
		return entrustDate;
	}

	public void setEntrustDate(String entrustDate) {
		this.entrustDate = entrustDate;
	}

	public int getSecSType() {
		return secSType;
	}

	public void setSecSType(int secSType) {
		this.secSType = secSType;
	}

	public String getStockNamegb() {
		return stockNamegb;
	}

	public void setStockNamegb(String stockNamegb) {
		this.stockNamegb = stockNamegb;
	}

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public String getTouchPrice() {
		return touchPrice;
	}

	public void setTouchPrice(String touchPrice) {
		this.touchPrice = touchPrice;
	}

	public String getStrategyEnddate() {
		return strategyEnddate;
	}

	public void setStrategyEnddate(String strategyEnddate) {
		this.strategyEnddate = strategyEnddate;
	}

	public String getStrategyStatus() {
		return strategyStatus;
	}

	public void setStrategyStatus(String strategyStatus) {
		this.strategyStatus = strategyStatus;
	}

	public String getStrategyType() {
		return strategyType;
	}

	public void setStrategyType(String strategyType) {
		this.strategyType = strategyType;
	}

	public String getInitDate() {
		return initDate;
	}

	public void setInitDate(String initDate) {
		this.initDate = initDate;
	}
}
