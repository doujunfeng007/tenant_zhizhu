package com.minigod.zero.trade.hs.resp;

import java.util.Date;

public class TradeEvent {
	private String fundAccount;
	private String exchangeType;
	private String stockCode;
	private String assetId;
	private String amount;
	private String price;
	private String entrustBs;
	private String entrustType;
//	private //;
	private String ip;
	private Date date;
	private String sessionId;
	//
	public Date getDate() {
		return date;
	}
	public String getEntrustType() {
		return entrustType;
	}
	public void setEntrustType(String entrustType) {
		this.entrustType = entrustType;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getExchangeType() {
		return exchangeType;
	}
	public String getFundAccount() {
		return fundAccount;
	}
	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}
	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getEntrustBs() {
		return entrustBs;
	}
	public void setEntrustBs(String entrustBs) {
		this.entrustBs = entrustBs;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
