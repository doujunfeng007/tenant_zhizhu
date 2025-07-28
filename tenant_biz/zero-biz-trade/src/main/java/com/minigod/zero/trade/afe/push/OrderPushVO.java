package com.minigod.zero.trade.afe.push;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @author chen
 * @ClassName OrderPushVO.java
 * @Description TODO
 * @createTime 2024年05月06日 18:30:00
 */
@Data
public class OrderPushVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JSONField(name ="SubmitTime")
	private String submitTime;

	@JSONField(name ="OSN")
	private String osn;

	@JSONField(name ="Status")
	private String status;

	@JSONField(name ="ExeQty")
	private String exeQty;

	@JSONField(name ="Qty")
	private String qty;

	@JSONField(name ="OstdQty")
	private String ostdQty;

	@JSONField(name ="Price")
	private String price;

	@JSONField(name ="OrderType")
	private String orderType;

	@JSONField(name ="CurrCD")
	private String currCD;

	@JSONField(name ="StockCD")
	private String stockCD;

	@JSONField(name ="SName")
	private String sName;

	@JSONField(name ="TName")
	private String tName;

	@JSONField(name ="Ename")
	private String ename;

	@JSONField(name ="Side")
	private String side;

	@JSONField(name ="Country")
	private String country;

	@JSONField(name ="OrderID")
	private String orderId;

	@JSONField(name ="GTD")
	private String gtd;

	@JSONField(name ="SysOrderNo")
	private String sysOrderNo;

	@JSONField(name ="MarketID")
	private String marketId;

	@JSONField(name ="LastUpdDt")
	private String lastUpdDt;

	@JSONField(name ="Msg_ID")
	private String msgId;

	private String tradeAccount;


}
