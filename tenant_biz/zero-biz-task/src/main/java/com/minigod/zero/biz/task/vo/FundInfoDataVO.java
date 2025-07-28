package com.minigod.zero.biz.task.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 第三方平台基金数据封装
 *
 * @author eric
 * @since 2024-05-21 10:56:09
 */
@Data
public class FundInfoDataVO implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 状态码
	 */
	@JSONField(name = "code")
	private int code;
	/**
	 * 数据
	 */
	@JSONField(name = "data")
	private FundInfoData data;

	/**
	 * 基金数据
	 */
	@Data
	public static class FundInfoData {
		/**
		 * 开始记录条数
		 */
		@JSONField(name = "start")
		private int start;

		/**
		 * 当前记录条数
		 */
		@JSONField(name = "count")
		private int count;

		/**
		 * 总记录条数
		 */
		@JSONField(name = "total")
		private int total;

		/**
		 * 基金列表
		 */
		@JSONField(name = "pages")
		private List<FundInfoPage> pages;
	}

	/**
	 * 基金列表
	 */
	@Data
	public static class FundInfoPage {
		/**
		 * 基金代码
		 */
		@JSONField(name = "productid")
		private Long productId;

		/**
		 * 基金代码
		 */
		@JSONField(name = "secid")
		private Long secId;
		/**
		 * 基金名称
		 */
		@JSONField(name = "name")
		private String name;
		/**
		 * 法定名称
		 */
		@JSONField(name = "legalname")
		private String legalName;

		/**
		 * 英文全称
		 */
		@JSONField(name = "englishname")
		private String englishname;

		/**
		 * 币种
		 */
		@JSONField(name = "currency")
		private String currency;

		/**
		 * 基金代码
		 */
		@JSONField(name = "productcode")
		private String productCode;
		/**
		 * 种子
		 */
		@JSONField(name = "sedol")
		private String sedol;

		/**
		 * 花旗代码
		 */
		@JSONField(name = "citicode")
		private String citicode;

		@JSONField(name = "isin")
		private String isin;

		/**
		 * 类型
		 */
		@JSONField(name = "type")
		private Integer type;

		/**
		 * 类型ID
		 */
		@JSONField(name = "assetclassid")
		private Long assetClassId;

		/**
		 * 类型
		 */
		@JSONField(name = "assetclass")
		private String assetClass;

		/**
		 * REO资产类别
		 */
		@JSONField(name = "reoassetclass")
		private String reoAssetClass;

		/**
		 * 子类型ID
		 */
		@JSONField(name = "subassetclassid")
		private Long subAssetClassId;

		/**
		 * 子类型
		 */
		@JSONField(name = "subassetclass")
		private String subAssetClass;

		/**
		 * REO资产子类别
		 */
		@JSONField(name = "reosubassetclass")
		private String reoSubAssetClass;

		/**
		 * REO中文名称
		 */
		@JSONField(name = "reochinesename")
		private String reoChineseName;

		/**
		 * REO风险等级
		 */
		@JSONField(name = "reoriskscore")
		private Integer reoRiskScore;

		/**
		 * 风险等级
		 */
		@JSONField(name = "risklevel")
		private String riskLevel;

		/**
		 * 可申购
		 */
		@JSONField(name = "buyable")
		private Integer buyAble;

		/**
		 * 可赎回
		 */
		@JSONField(name = "sellable")
		private Integer sellAble;

		/**
		 * 近一天收益率
		 */
		@JSONField(name = "_1dr")
		private String return1d;

		/**
		 * 近一月收益率
		 */
		@JSONField(name = "_1mr")
		private String return1m;

		/**
		 * 近三月收益率
		 */
		@JSONField(name = "_3mr")
		private String return3m;

		/**
		 * 近六月收益率
		 */
		@JSONField(name = "_6mr")
		private String return6m;

		/**
		 * 近一年收益率
		 */
		@JSONField(name = "_1yr")
		private String return1y;

		/**
		 * 近两年收益率
		 */
		@JSONField(name = "_2yr")
		private String return2y;

		/**
		 * 近三年收益率
		 */
		@JSONField(name = "_3yr")
		private String return3y;


		/**
		 * 近四年收益率
		 */
		@JSONField(name = "_4yr")
		private String return4y;


		/**
		 * 近五年收益率
		 */
		@JSONField(name = "_5yr")
		private String return5y;


		/**
		 * 年初至今收益率
		 */
		@JSONField(name = "_ytd")
		private String returnYtd;

		/**
		 * 自创立以来的收益率
		 */
		@JSONField(name = "_sinceinception")
		private String sinceInception;

		/**
		 * 净值更新时间
		 */
		@JSONField(name = "performancepricedate")
		private String performancePriceDate;

		/**
		 * 净值描述
		 */
		@JSONField(name = "performancedescription")
		private Integer performanceDescription;

		/**
		 * 分红
		 */
		@JSONField(name = "dividend_yield")
		private String dividendYield;

		/**
		 * 年度管理费
		 */
		@JSONField(name = "annualmanagementfee")
		private String annualManagementFee;

		/**
		 * 业绩费
		 */
		@JSONField(name = "performancefee")
		private String performanceFee;

		/**
		 * 申购数量精度
		 */
		@JSONField(name = "quantityprecision")
		private String quantityPrecision;

		/**
		 * 交易数量精度
		 */
		@JSONField(name = "tradequantityprecision")
		private String tradeQuantityPrecision;

		/**
		 * 最小初始投资金额
		 */
		@JSONField(name = "mininitialinvestmentamount")
		private String mininitialInvestmentAmount;

		/**
		 * 最小追加投资金额
		 */
		@JSONField(name = "minfollowoninvestmentamount")
		private String minfollowonInvestmentAmount;

		/**
		 * 最小定期购买投资额
		 */
		@JSONField(name = "minregularbuyinvestmentamount")
		private String minregularBuyInvestmentAmount;

		/**
		 * 最新净值
		 */
		@JSONField(name = "price")
		private Price price;

		/**
		 * 选择度
		 */
		@JSONField(name = "selection")
		private Integer selection;

		/**
		 * 热度
		 */
		@JSONField(name = "hot")
		private Integer hot;

		/**
		 * 衍生
		 */
		@JSONField(name = "derivate")
		private String derivate;

		/**
		 * 标签
		 */
		@JSONField(name = "tags")
		private String[] tags;

		/**
		 * 有鱼评分
		 */
		@JSONField(name = "youyuscore")
		private String youYuScore;

		/**
		 * 开售时间
		 */
		@JSONField(name = "opentime")
		private String opentime;

		/**
		 * cutoff时间
		 */
		@JSONField(name = "cutofftime")
		private String cutoffTime;

		/**
		 * 订单返回
		 */
		@JSONField(name = "orderreturn")
		private String orderReturn;

		/**
		 * 1年波动率
		 */
		@JSONField(name = "stddev1yr")
		private String stddev1yr;

		/**
		 * 3年波动率
		 */
		@JSONField(name = "stddev3yr")
		private String stddev3yr;

		/**
		 * 5年波动率
		 */
		@JSONField(name = "stddev5yr")
		private String stddev5yr;

		/**
		 * 10年波动率
		 */
		@JSONField(name = "stddev10yr")
		private String stddev10yr;

		/**
		 * 1年夏普比率
		 */
		@JSONField(name = "sharperatio1yr")
		private String sharpeRatio1yr;

		/**
		 * 3年夏普比率
		 */
		@JSONField(name = "sharperatio3yr")
		private String sharpeRatio3yr;

		/**
		 * 5年夏普比率
		 */
		@JSONField(name = "sharperatio5yr")
		private String sharpeRatio5yr;

		/**
		 * 10年夏普比率
		 */
		@JSONField(name = "sharperatio10yr")
		private String sharpeRatio10yr;

		/**
		 * 1年最大回撤
		 */
		@JSONField(name = "maxdrawdown1yr")
		private String maxDrawDown1yr;

		/**
		 * 3年最大回撤
		 */
		@JSONField(name = "maxdrawdown3yr")
		private String maxDrawDown3yr;

		/**
		 * 5年最大回撤
		 */
		@JSONField(name = "maxdrawdown5yr")
		private String maxDrawDown5yr;

		/**
		 * 10年最大回撤
		 */
		@JSONField(name = "maxdrawdown10yr")
		private String maxDrawDown10yr;

		/**
		 * 详情
		 */
		@JSONField(name = "detail")
		private Detail detail;

		/**
		 * 日涨幅时间
		 */
		@JSONField(name = "DailyPerformanceTime")
		private String dailyPerformanceTime;
	}

	/**
	 * 基金代码
	 */
	@Data
	public static class MstarId {
		/**
		 * 基金代码
		 */
		@JSONField(name = "shareclassid")
		private String shareClassId;

		/**
		 * 净值代码
		 */
		@JSONField(name = "performanceid")
		private String performanceId;
	}

	/**
	 * 净值
	 */
	@Data
	public static class Price {
		/**
		 * 货币
		 */
		@JSONField(name = "currency")
		private String currency;

		/**
		 * 日期
		 */
		@JSONField(name = "date")
		private String date;

		/**
		 * 净值
		 */
		@JSONField(name = "value")
		private String value;
	}

	/**
	 * 详情
	 */
	@Data
	public static class Detail {
		/**
		 * 基金规模
		 */
		@JSONField(name = "fundsize")
		private String fundSize;

		/**
		 * 基金规模结束日期
		 */
		@JSONField(name = "fundsizeenddate")
		private String fundSizeEndDate;

		/**
		 * 基金规模币种
		 */
		@JSONField(name = "fundsizecurrency")
		private String fundSizeCurrency;
	}
}
