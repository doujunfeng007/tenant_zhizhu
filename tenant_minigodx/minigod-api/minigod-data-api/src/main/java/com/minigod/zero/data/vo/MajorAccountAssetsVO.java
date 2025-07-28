package com.minigod.zero.data.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/28 19:59
 * @description：大客户Top 10
 */
@Data
public class MajorAccountAssetsVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 活力得资产
	 */
	private BigDecimal hldTotalQuantity;
	/**
	 * 债券易资产
	 */
	private BigDecimal bondTotalQuantity;
	/**
	 * 总资产
	 */
	private BigDecimal totalQuantity;
	/**
	 * 占比
	 */
	private BigDecimal proportion;
}
