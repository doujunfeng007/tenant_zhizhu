package com.minigod.zero.data.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 债券易、活利得日结单返回实体类
 *
 * @param
 * @return
 */
@Data
public class CustomerHldBondDailyAccountVO {
	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 公司logo地址
	 */
	private String companyLogoUrl;

	/**
	 * 结单日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String accountDate;

	/**
	 * 客户名称
	 */
	private String custName;

	/**
	 * 账户号码
	 */
	private String accountNumber;

	/**
	 * 账户币种
	 */
	private String accountCurrency;

	/**
	 * 联系地址
	 */
	private String accountContactAddress;


	/**
	 * 债券易日结单买入确认单集合
	 */
	private List<BuyConfirmForm> bondBuyDailyForms;

	/**
	 * 债券易日结单卖出确认单集合
	 */
	private List<SellConfirmForm> bondSellDailyForms;

	/**
	 * 债券易日结单持仓总览集合
	 */
	private List<HoldingOverView> bondHoldingDailyViews;

	/**
	 * 活利得日结单买入确认单集合
	 */
	private List<HldTradingBuyStatementDailyVO> hldBuyDailyVOs;

	/**
	 * 活利得日结单卖出确认单集合
	 */
	private List<HldTradingSaleStatementDailyVO> hldSaleDailyVOs;

	/**
	 * 活利得日结单持仓总览集合
	 */
	private List<HldHoldingHistoryStatementDailyVO> hldHoldingDailyVOs;

	/**
	 * 活利得市值总额
	 */
	private BigDecimal hldSumMarketValue;

	/**
	 * 活利得累计收益利息
	 */
	private BigDecimal hldTotalGainLossSum;

	/**
	 * 债券易市值总额
	 */
	private BigDecimal bondSumMarketValue;

	private String subAccountId;






	@Data
	public static class BuyConfirmForm {
		/**
		 * 证券名称
		 */
		private String name;
		/**
		 * 证券代码
		 */
		private String fundCode;

		/**
		 * 交易日/交收日
		 */
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		@JSONField(format = "yyyy-MM-dd HH:mm:ss")
		private Timestamp transactionDateOnly;

		/**
		 * 买入份额
		 */
		private BigDecimal quantity;

		/**
		 * 面值
		 */
		private BigDecimal nominalValue;

		/**
		 * 票面金额
		 */
		private BigDecimal faceAmount;

		/**
		 * 成交价格（%）
		 */
		private BigDecimal businessPrice;

		/**
		 * 买入金额
		 */
		private BigDecimal amount;

		/**
		 * 应计利息
		 */
		private BigDecimal transactionGainLoss;

		/**
		 * 总交收金额
		 */
		private BigDecimal totalSettlementAmount;
		/**
		 * 币种
		 */
		private String currency;
		/**
		 * 订单id
		 */
		private String orderId;
		/**
		 * 订单类型  1:买;2:卖;3:交换买;4:交换卖;11 IPO 买入
		 */
		private Integer orderType;

		/**
		 * 汇率
		 */
		private BigDecimal rate;
		/**
		 * 手续费
		 */
		private BigDecimal fee;
	}


	@Data
	public static class SellConfirmForm {
		/**
		 * 证券名称
		 */
		private String name;
		/**
		 * 证券代码
		 */
		private String fundCode;

		/**
		 * 交易日/交收日
		 */
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		@JSONField(format = "yyyy-MM-dd HH:mm:ss")
		private Timestamp transactionDateOnly;

		/**
		 * 买入份额
		 */
		private BigDecimal quantity;

		/**
		 * 面值
		 */
		private BigDecimal nominalValue;

		/**
		 * 票面金额
		 */
		private BigDecimal faceAmount;

		/**
		 * 成交价格（%）
		 */
		private BigDecimal businessPrice;

		/**
		 * 买入金额
		 */
		private BigDecimal amount;

		/**
		 * 应计利息
		 */
		private BigDecimal transactionGainLoss;

		/**
		 * 总交收金额
		 */
		private BigDecimal totalSettlementAmount;


		private String currency;

		/**
		 * 订单id
		 */
		private String orderId;

		/**
		 * 订单类型  1:买;2:卖;3:交换买;4:交换卖;11 IPO 买入
		 */
		private Integer orderType;

		/**
		 * 汇率
		 */
		private BigDecimal rate;
		/**
		 * 手续费
		 */
		private BigDecimal fee;

	}

	@Data
	public static class HoldingOverView {
		private String id;
		/**
		 * 产品代码
		 */
		private String productId;

		/**
		 * 产品代码
		 */
		private String bondCode;

		/**
		 * 产品名称
		 */
		private String bondName;

		/**
		 * 币种
		 */
		private String currency;

		/**
		 * 份额/数量
		 */
		private BigDecimal totalQuantity;

		/**
		 * 单位面值
		 */
		private BigDecimal nominalValue;

		/**
		 * 票面金额
		 */
		private BigDecimal faceAmount;

		/**
		 * 市价
		 */
		private BigDecimal price;

		/**
		 * 应计利息
		 */
		private BigDecimal realizedGainLoss;

		/**
		 * 市值
		 */
		private BigDecimal marketValue;

		/**
		 * 成本价
		 */
		private BigDecimal averageCost;

		/**
		 * 成本值
		 */
		private BigDecimal averageValue;

		/**
		 * 汇率
		 */
		private BigDecimal rate;

		/**
		 * 市值港币
		 */
		private BigDecimal hkMarketValue;

		/**
		 * 成本值港币
		 */
		private BigDecimal hkAverageValue;

		private Date createTime;
	}
}
