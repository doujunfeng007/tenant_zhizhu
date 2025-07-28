package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 查询策略单
 * @author sunline
 *
 */
public class EF01180005VO extends EntrustRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private String initDate;
	private String entrustDate;
	private String conditionType;// 策略类型（0-OQ订单;1-市价到价单;2-限价到价单）
	private String touchPrice;//
	private String strategyEnddate;// 策略失效日期
	private String strategyStatus;// 策略状态（1-未执行;2-执行中;3-执行完成;4-暂停;5-强制释放）
	private String strategyType;// 0 normal就是正常的订单，当日失效的，1 GTD（Good Till Date）：在指定日前有效，2 GTC（Good Till Cancelled）：除非交易者自己取消，否则该委托“永久”有效。
	private int secSType;
	private String cancelable = "0"; //取消
	private String modifiable = "0" ; //修改

	public String getEntrustDate() {
		return entrustDate;
	}

	public void setEntrustDate(String entrustDate) {
		this.entrustDate = entrustDate;
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

	public String getCancelable() {
		return cancelable;
	}

	public void setCancelable(String cancelable) {
		this.cancelable = cancelable;
	}

	public String getModifiable() {
		return modifiable;
	}

	public void setModifiable(String modifiable) {
		this.modifiable = modifiable;
	}

	public int getSecSType() {
		return secSType;
	}

	public void setSecSType(int secSType) {
		this.secSType = secSType;
	}

	public String getInitDate() {
		return initDate;
	}

	public void setInitDate(String initDate) {
		this.initDate = initDate;
	}

	public String getStrategyType() {
		return strategyType;
	}

	public void setStrategyType(String strategyType) {
		this.strategyType = strategyType;
	}
}
