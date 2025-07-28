package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/9 21:00
 * @description： 活利得持仓vo
 */
@Data
public class CustomerHldHoldingVO {

	private List<MarketValue> marketValues = new ArrayList<>();

	private List<YesterdayEarnings> yesterdayEarnings = new ArrayList<>();

	private List<TotalEarnings> totalEarnings = new ArrayList<>();

	private List<ProfitHolding> profitHoldings = new ArrayList<>();

	private List<UnrealizedPosition> unrealizedPosition = new ArrayList<>();


	public void addMarketValue(String currency,BigDecimal value){
		this.marketValues.add(new MarketValue(currency,value));
	}

	public void addYesterdayEarnings(String currency,BigDecimal value){
		this.yesterdayEarnings.add(new YesterdayEarnings(currency, value));
	}

	public void addTotalEarnings(String currency,BigDecimal value){
		this.totalEarnings.add(new TotalEarnings(currency, value));
	}

	public void addProfitHolding(String hldName,BigDecimal holdMarketValue,
								 BigDecimal yesterdayEarnings,BigDecimal totalEarnings,
								 BigDecimal totalDividend){
		this.profitHoldings.add(new ProfitHolding(hldName, holdMarketValue, yesterdayEarnings, totalEarnings, totalDividend));
	}

	public void addUnrealizedPosition(String hldName,String hldCreateTime,BigDecimal portion){
		this.unrealizedPosition.add(new UnrealizedPosition(hldName, hldCreateTime, portion));
	}

	/**
	 * 市值
	 */
	@Data
	class MarketValue{
		private String currency;
		private BigDecimal value;

		public MarketValue(String currency,BigDecimal value){
			this.currency = currency;
			this.value = value;
		}
	}

	/**
	 * 昨日收益
	 */
	@Data
	class YesterdayEarnings{
		private String currency;
		private BigDecimal value;
		public YesterdayEarnings(String currency,BigDecimal value){
			this.currency = currency;
			this.value = value;
		}
	}

	/**
	 * 累计总收益
	 */
	@Data
	class TotalEarnings{
		private String currency;
		private BigDecimal value;
		public TotalEarnings(String currency,BigDecimal value){
			this.currency = currency;
			this.value = value;
		}
	}

	/**
	 * 收益持仓
	 */
	@Data
	class ProfitHolding{
		private String hldName;
		private BigDecimal holdMarketValue;
		private BigDecimal yesterdayEarnings;
		private BigDecimal totalEarnings;
		private BigDecimal totalDividend;

		public ProfitHolding(String hldName,BigDecimal holdMarketValue,
							 BigDecimal yesterdayEarnings,BigDecimal totalEarnings,
							 BigDecimal totalDividend){
			this.hldName = hldName;
			this.yesterdayEarnings= yesterdayEarnings;
			this.holdMarketValue = holdMarketValue;
			this.totalDividend = totalDividend;
			this.totalEarnings = totalEarnings;

		}

	}

	/**
	 * 未收益持仓
	 */
	@Data
	class UnrealizedPosition{
		private String hldName;
		private String hldCreateTime;
		private BigDecimal portion;

		public UnrealizedPosition(String hldName,String hldCreateTime,BigDecimal portion){
			this.hldName = hldName;
			this.hldCreateTime = hldCreateTime;
			this.portion = portion;
		}
	}
}
