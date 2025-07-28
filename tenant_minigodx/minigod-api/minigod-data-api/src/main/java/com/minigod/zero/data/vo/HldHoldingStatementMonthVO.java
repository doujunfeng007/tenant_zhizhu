package com.minigod.zero.data.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName: com.minigod.zero.customer.vo.hldHoldingStatementMonthVO
 * @Description: 活利得月结单持仓总览
 * @Author: linggr
 * @CreateDate: 2024/5/24 0:52
 * @Version: 1.0
 */
@Data
public class HldHoldingStatementMonthVO {

	/**
	 * 证券名称
	 */
	private String hldName;
	/**
	 * 证券代码
	 */
	private String hldCode;

	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 买入份额
	 */
	private String quantity;
	/**
	 * 单位面值
	 */
	private String nominalValue;

	/**
	 * 票面金额
	 */
	private BigDecimal faceAmount;

	/**
	 * 应计利息
	 */
	private BigDecimal transactionGainLoss;

	/**
	 * 兑换汇率
	 */
	private BigDecimal rate;
	/**
	 * 市值 (港币)
	 */
	private BigDecimal hldMarket;
	/**
	 * 市价
	 */
	private String price;

	/*public void setPrice(BigDecimal price) {
		if (price != null) {
			// 设置价格字段的精度为2，并使用四舍五入模式
			BigDecimal price1 = price.setScale(2, BigDecimal.ROUND_DOWN);
			this.price = price1;
		} else {
			this.price = null;
		}
	}*/

	public void setFaceAmount(BigDecimal faceAmount) {
		this.faceAmount = faceAmount.setScale(2, BigDecimal.ROUND_DOWN);
	}

	public void setTransactionGainLoss(BigDecimal transactionGainLoss) {
		this.transactionGainLoss = transactionGainLoss.setScale(2, BigDecimal.ROUND_DOWN);
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate.setScale(2, BigDecimal.ROUND_DOWN);
	}

	public void setHldMarket(BigDecimal hldMarket) {
		this.hldMarket = hldMarket.setScale(2, BigDecimal.ROUND_DOWN);
	}
}
