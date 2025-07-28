package com.minigod.zero.data.vo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/28 19:52
 * @description：
 */

@Data
public class CustomerAssetVO implements Serializable {
	private static final long serialVersionUID = 1L;
	// 客户信息
	private String customerName;
	private String accountId;

	// 64类型持仓
	private BigDecimal hldHkdTotalQuantity = BigDecimal.ZERO;
	private BigDecimal hldUsdTotalQuantity = BigDecimal.ZERO;
	private BigDecimal hldCnyTotalQuantity = BigDecimal.ZERO;

	// 65类型持仓
	private BigDecimal bondHkdTotalQuantity = BigDecimal.ZERO;
	private BigDecimal bondUsdTotalQuantity = BigDecimal.ZERO;
	private BigDecimal bondCnyTotalQuantity = BigDecimal.ZERO;

	// 现金
	private BigDecimal hkdTotalQuantity = BigDecimal.ZERO;
	private BigDecimal usdTotalQuantity = BigDecimal.ZERO;
	private BigDecimal cnyTotalQuantity = BigDecimal.ZERO;

	// 各币种总资产
	private BigDecimal hkdAssets = BigDecimal.ZERO;
	private BigDecimal usdAssets = BigDecimal.ZERO;
	private BigDecimal cnyAssets = BigDecimal.ZERO;
}
