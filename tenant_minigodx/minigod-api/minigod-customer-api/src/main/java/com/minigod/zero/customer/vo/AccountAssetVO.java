package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/15 9:19
 * @description： h5账户资产信息
 */
@Data
public class AccountAssetVO {
	private BigDecimal totalAsset = BigDecimal.ZERO;
	private BigDecimal availableAmount = BigDecimal.ZERO;
	private BigDecimal cashAmount = BigDecimal.ZERO;

	private BigDecimal hkdAmount = BigDecimal.ZERO;
	private BigDecimal hkdPosition = BigDecimal.ZERO;

	private BigDecimal usdAmount = BigDecimal.ZERO;
	private BigDecimal usdPosition = BigDecimal.ZERO;

	private BigDecimal cnyAmount = BigDecimal.ZERO;
	private BigDecimal cnyPosition = BigDecimal.ZERO;

}
