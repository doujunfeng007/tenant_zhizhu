package com.minigod.zero.trade.vo.sjmb.resp;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName TotalAssetInfoVO.java
 * @Description TODO
 * @createTime 2024年09月27日 14:39:00
 */
@Data
public class TotalAssetInfoVO {

	/**
	 * 币种
	 */
	private String currency;

	/**
	 * 总资产
	 */
	private BigDecimal asset =new BigDecimal("0");

	/**
	 * 港股市场资产
	 */
	private AssetInfoVO hkStockAssetInfo;

	/**
	 * 美股市场资产
	 */
	private AssetInfoVO usStockAssetInfo;

	/**
	 * 中华通市场资产
	 */
	private AssetInfoVO cnyStockAssetInfo;

	/**
	 * 美股期权市场资产
	 */
	private AssetInfoVO usOptionsAssetInfo;

	/**
	 * 	港股基金市场资产
	 */
	private AssetInfoVO hkFundAssetInfo;

	/**
	 * 美元基金账户
	 */
	private AssetInfoVO usFundAssetInfo;


}
