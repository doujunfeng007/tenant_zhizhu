package com.minigod.zero.customer.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * @ClassName: com.minigod.zero.customer.vo.HldBondDailyVO
 * @Description:  债券易、活利得日结单返回实体类
 * @Author: linggr
 * @CreateDate: 2024/7/9 15:47
 * @Version: 1.0
 */
@Data
public class HldBondDailyVO {
	static String DECIMAL_DEFAULT_FORMAT = "#,##0.00";
	static DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_DEFAULT_FORMAT);


	/**
	 * 客户名称
	 */
	private String custName;

	/**
	 * 账户号码/户口号
	 */
	private String custId;
	/**
	 * 理财账号
	 */
	private String accountId;

	/**
	 * 联系地址
	 */
	private String address;
	/**
	 * 联系地址
	 */
	private String address1;
	/**
	 * 联系地址
	 */
	private String address2;
	/**
	 * 联系地址
	 */
	private String address3;
	/**
	 * 联系地址
	 */
	private String address4;

	/**
	 * 客户主任
	 */
	private String accountExecutive;

	/**
	 * 结单日期
	 */
	private String accountDate;
	/**
	 * 打印日期
	 */
	private String accountDate2;
	/**
	 * 结单日期2 月底
	 */
	private String accountDate3;

	/**
	 * 投资组合总览: 现金变动结余
	 */
	private List<HldBondDailyVO.CashChangeBalanceVO> cashChangeBalances;

	/**
	 * 投资组合总览: 投资总汇(持仓总览)
	 */
	private List<HldBondDailyVO.InvestmentPoolVO> investmentPools;

	/**
	 * 交易成交单据
	 */
	private List<HldBondDailyVO.TransactionContractNoteVO> transactionContractNote;

	/**
	 * 交易成交单据 hld
	 */
	//private List<HldBondDailyVO.TransactionHldContractNoteVO> transactionHldContractNotes;
	//private TransactionHldContractNoteVO noteVO;
	private List<HldBondDailyVO.TransactionContractNoteVO> transactionHldContractNote;
	/**
	 * 交易成交单据 zqy
	 */
	//private List<HldBondDailyVO.TransactionBondContractNoteVO> transactionBondContractNotes;
	private List<HldBondDailyVO.TransactionContractNoteVO> transactionBondContractNote;

	/**
	 * 户口出入金额变动
	 */
	private List<HldBondDailyVO.ChangeAccountVO> changeAccount;

	/**
	 * 持货结存
	 */
	private List<HldBondDailyVO.HoldingVO> holdings;
	private List<HldBondDailyVO.HoldingVO> hldholdings;
	private List<HldBondDailyVO.HoldingVO> bondholdings;

	/**
	 * 交易成交单据 :总交收金额
	 */
	private BigDecimal totalSettlementAmount;
	/**
	 * 交易成交单据 :总交收金额 hld
	 */
	private BigDecimal totalHldSettlementAmount;
	/**
	 * 交易成交单据 :总交收金额 bond
	 */
	private BigDecimal totalBondSettlementAmount;
	/**
	 * 持货结存 : 总市值
	 */
	private BigDecimal marketValue;
	/**
	 * 持货结存 累计总盈亏 (HKD 等值)
	 */
	private BigDecimal marketTotalGainLoss;


	/**
	 * 投资总汇 投资组合总值 (HKD 等值)
	 */
	private BigDecimal investmentPoolValue;
	/**
	 * 投资总汇 累计总盈亏 (HKD 等值)
	 */
	private BigDecimal investmentTotalGainLoss;
	/**
	 * 户口出入金额变动 汇总
	 */
	private BigDecimal changeAccountValue;


	/**
	 * 投资组合总览: 现金变动结余
	 */
	@Data
	public static class CashChangeBalanceVO {
		/**
		 * 币种
		 */
		private String currency;

		/**
		 * 昨日结余
		 */
		private BigDecimal yesterdayBalance;

		/**
		 * 今日变动额
		 */
		private BigDecimal todayChangeBalance;

		/**
		 * 净结余: 上日结余+今日变动额+今日交收+待交收
		 */
		private BigDecimal todayBalance;

		/**
		 * 今日交收
		 */
		private BigDecimal pendingBalance;

		/**
		 * 待交收
		 */
		private BigDecimal backBalance = BigDecimal.ZERO;

		public BigDecimal getYesterdayBalance() {
			return yesterdayBalance==null?BigDecimal.ZERO: yesterdayBalance.setScale(2, RoundingMode.DOWN);
		}

		public BigDecimal getTodayChangeBalance() {
			return todayChangeBalance==null?BigDecimal.ZERO: todayChangeBalance.setScale(2, RoundingMode.DOWN);
		}

		public BigDecimal getTodayBalance() {
			return todayBalance==null?BigDecimal.ZERO: todayBalance.setScale(2, RoundingMode.DOWN);
		}

		public BigDecimal getPendingBalance() {
			return pendingBalance==null?BigDecimal.ZERO: pendingBalance.setScale(2, RoundingMode.DOWN);
		}
	}


	/**
	 * 投资组合总览: 投资总汇
	 */
	@Data
	public static class InvestmentPoolVO {
		/**
		 * 币种
		 */
		private String currency;
		/**
		 * 活力德
		 */
		private BigDecimal totalHldValue;
		/**
		 * 债券易
		 */
		private BigDecimal totalBondValue;

		/**
		 * 活力德买入
		 */
		private BigDecimal totalHldBuyValue;

		/**
		 * 债券易买入
		 */
		private BigDecimal totalBondBuyValue;

		/**
		 * 活力德 卖出
		 */
		private BigDecimal totalHldSaleValue;

		/**
		 * 债券易 卖出
		 */
		private BigDecimal totalBondSaleValue;


		/**
		 * 基金
		 */
		private BigDecimal totalFundValue = new BigDecimal(0.00);
		/**
		 * 股票
		 */
		private BigDecimal totalStockValue = new BigDecimal(0.00);
		/**
		 * 数字货币
		 */
		private BigDecimal totalDigitalValue = new BigDecimal(0.00);
		/**
		 * 其他
		 */
		private BigDecimal totalRestsValue = new BigDecimal(0.00);
		/**
		 * 总投资
		 */
		private BigDecimal totalValue;
		private BigDecimal totalHkValue;
		/**
		 * 汇率
		 */
		private BigDecimal rate;
		private String rates;



	}


	/**
	 * 交易成交单据 hld
	 */
	@Data
	public static class TransactionHldContractNoteVO {
		private List<HldBondDailyVO.TransactionContractNoteVO> transactionContractNote;
		/**
		 * 总金额(HKD 等值)
		 */
		private BigDecimal totalAmount;
	}

	/**
	 * 交易成交单据 bond 债券易
	 */
	@Data
	public static class TransactionBondContractNoteVO {
		private List<HldBondDailyVO.TransactionContractNoteVO> transactionContractNote;
		/**
		 * 总金额(HKD 等值)
		 */
		private BigDecimal totalAmount;
	}

	/**
	 * 交易成交单据
	 */
	@Data
	public static class TransactionContractNoteVO {
		/**
		 * hld 1  bond 2
		 *
		 */
		private Integer type;
		/**
		 * 交易/交收日期
		 */
		private Date tradeDate;

		/**
		 * 交易类别[0B-买入 OS-卖出]
		 */
		private String tradeKind;

		/**
		 * 参考编号
		 */
		private String transactionAlias;

		/**
		 * 产品名称
		 */
		private String productName;
		/**
		 * 产品代码
		 */
		private String productCode;
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
		 * 成交价格
		 */
		private BigDecimal businessPrice;

		/**
		 * 成交金额
		 */
		private BigDecimal businessBalance;

		/**
		 * 应付日数
		 */
		private BigDecimal daysPayable;

		/**
		 * 应计利息
		 */
		private BigDecimal realizedGainLoss;


		/**
		 * 交收金额
		 */
		private BigDecimal settlementAmount;
		/**
		 * 汇率
		 */
		private BigDecimal rate;

		public BigDecimal getSettlementAmount() {
			BigDecimal bigDecimal = settlementAmount == null ? BigDecimal.ZERO : settlementAmount.setScale(2, RoundingMode.DOWN);
			if (Objects.equals(tradeKind, "买入")){
				bigDecimal = bigDecimal.multiply(BigDecimal.valueOf(-1));
			}
			return bigDecimal;
		}
	}


	/**
	 * 户口出入金额变动
	 */
	@Data
	public static class ChangeAccountVO {

		/**
		 * 参考编号
		 */
		private String transactionAlias;
		/**
		 * 到期/交收日
		 */
		private String tradeDate;
		/**
		 * 内容说明
		 */
		private String contents;
		/**
		 * 内容说明2
		 */
		private String contentTwos;

		/**
		 * 份额/数量
		 */
		private BigDecimal totalQuantity;

		/**
		 * 存入金额
		 */
		private BigDecimal depositAccount;

		/**
		 * 提取金额
		 */
		private BigDecimal withdrawAmount;

		/**
		 * 净结余
		 */
		private BigDecimal netBalance;
		/**
		 * 汇率
		 */
		private BigDecimal rate;

		/**
		 * 币种
		 */
		private String currency;

	}


	/**
	 * 持货结存
	 */
	@Data
	public static class HoldingVO {
		/**
		 * 市场 活利得 债券易
		 */
		private String market;
		/**
		 * 市场 1活利得 2债券易
		 */
		private Integer  type;
		/**
		 * 产品名称
		 */
		private String productName;
		/**
		 * 产品代码
		 */
		private String productCode;
		/**
		 * 币种
		 */
		private String currency;

		/**
		 * 昨日结余/今日结余
		 */
		private BigDecimal todayBalance;

		/**
		 * 当日变动
		 */
		private BigDecimal todayChangeBalance;


		/**
		 * 净结余
		 */
		private BigDecimal netBalance;

		/**
		 * 收市价
		 */
		private BigDecimal closingPrice;
		/**
		 * 汇率
		 */
		private BigDecimal rate;
		/**
		 * 当日市值
		 */
		private BigDecimal dayMktValue;

		/**
		 * 累计利息
		 */
		private BigDecimal totalGainLoss;
		/**
		 * 累计利息 hk
		 */
		private BigDecimal totalHkGainLoss;

	}
}
