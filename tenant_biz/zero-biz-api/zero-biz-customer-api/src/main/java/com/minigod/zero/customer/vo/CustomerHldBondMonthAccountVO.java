package com.minigod.zero.customer.vo;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * 活利得、债券易月结单返回实体类
 *
 * @param
 * @return
 */
@Data
public class CustomerHldBondMonthAccountVO {
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
	 * 债券易月结单交易明细集合
	 */
	private List<TradeDetail> bondTradeMonthDetails;

	/**
	 * 债券易月结单持仓总览集合
	 */
	private List<HoldingOverView> bondHoldingMonthViews;

	/**
	 * 债券易月结单市值总额
	 */
	private BigDecimal bondSumMarketValue;

	/**
	 * 活利得月结单交易明细集合
	 */
	private List<HldStatementMonthVO> hldTradeMonthVOs;

	/**
	 * 活利得月结单持仓总览集合
	 */
	private List<HldHoldingStatementMonthVO> hldHoldingMonthVOs;

	/**
	 * 活利得月结单市值总额 持仓vo hldMarket sum
	 */
	private BigDecimal hldSumMarketValue;

	/**
	 * 活利得月结单累积利息收益
	 */
	private BigDecimal hldTotalGainLossSum;

	private String subAccountId;


	@Data
	public static class TradeDetail {
		/**
		 * 交易日
		 */
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		@DateTimeFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
		)
		private Date transactionDateOnly;
		/**
		 * 交易种类/交易编号
		 */
		private String transactionAlias;

		/**
		 * 产品名称
		 */
		private String name;

		/**
		 * 产品代码
		 */
		private String fundCode;

		/**
		 * 币种
		 */
		private String currency;

		/**
		 * 票面金额
		 */
		private BigDecimal faceAmount;

		/**
		 * 成交价格（%）
		 */
		private BigDecimal settledAmount;

		/**
		 * 应计利息
		 */
		private BigDecimal realizedGainLoss;

		/**
		 * 结算金额
		 */
		private BigDecimal sumAmount;

		/**
		 * 已变现损益
		 */
		private BigDecimal realizedGainsLosses;

	}

	@Data
	public static class HoldingOverView {
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
	}
}
