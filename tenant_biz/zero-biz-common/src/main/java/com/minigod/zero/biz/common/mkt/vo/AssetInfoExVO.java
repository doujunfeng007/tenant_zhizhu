package com.minigod.zero.biz.common.mkt.vo;

/**
 * 分页查询码表扩展类
 */
public class AssetInfoExVO extends AssetInfoVO {
	private static final long serialVersionUID = -5595277459988172171L;
	private String chgPct;// 涨跌幅

	public String getChgPct() {
		return chgPct;
	}

	public void setChgPct(String chgPct) {
		this.chgPct = chgPct;
	}
}
