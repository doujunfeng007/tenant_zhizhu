package com.minigod.zero.trade.hs.resp;

public class ClientCashSumInfoHkdUsd extends ClientCashSumInfo {
	private static final long serialVersionUID = 5420261256002945219L;
	//
	private String assetHKD;
	private String assetUSD;
	private String fetchBalanceHKD;
	private String fetchBalanceUSD;
	private String frozenBalanceHKD;
	private String frozenBalanceUSD;
	private String enableBalanceHKD;
	private String enableBalanceUSD;

	public String getAssetHKD() {
		return assetHKD;
	}

	public void setAssetHKD(String assetHKD) {
		this.assetHKD = assetHKD;
	}

	public String getAssetUSD() {
		return assetUSD;
	}

	public void setAssetUSD(String assetUSD) {
		this.assetUSD = assetUSD;
	}

	public String getFetchBalanceHKD() {
		return fetchBalanceHKD;
	}
	public void setFetchBalanceHKD(String fetchBalanceHKD) {
		this.fetchBalanceHKD = fetchBalanceHKD;
	}
	public String getFetchBalanceUSD() {
		return fetchBalanceUSD;
	}
	public void setFetchBalanceUSD(String fetchBalanceUSD) {
		this.fetchBalanceUSD = fetchBalanceUSD;
	}
	public String getFrozenBalanceHKD() {
		return frozenBalanceHKD;
	}
	public void setFrozenBalanceHKD(String frozenBalanceHKD) {
		this.frozenBalanceHKD = frozenBalanceHKD;
	}
	public String getFrozenBalanceUSD() {
		return frozenBalanceUSD;
	}
	public void setFrozenBalanceUSD(String frozenBalanceUSD) {
		this.frozenBalanceUSD = frozenBalanceUSD;
	}

	public String getEnableBalanceHKD() {
		return enableBalanceHKD;
	}

	public void setEnableBalanceHKD(String enableBalanceHKD) {
		this.enableBalanceHKD = enableBalanceHKD;
	}

	public String getEnableBalanceUSD() {
		return enableBalanceUSD;
	}

	public void setEnableBalanceUSD(String enableBalanceUSD) {
		this.enableBalanceUSD = enableBalanceUSD;
	}
}
