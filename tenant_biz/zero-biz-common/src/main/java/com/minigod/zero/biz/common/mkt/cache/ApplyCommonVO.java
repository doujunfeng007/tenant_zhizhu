package com.minigod.zero.biz.common.mkt.cache;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApplyCommonVO implements Serializable {

    private static final long serialVersionUID = -4966894073880687751L;
	/**
	 * 股票名称
	 */
	private String stkName; // 股票名称
	/**
	 * 资产ID
	 */
    private String assetId; // 资产ID
	/**
	 * 股票类型
	 */
    private Integer stkType; //股票类型
	/**
	 * 股票子类型
	 */
	private Integer stkSubType; //股票子类型
}
