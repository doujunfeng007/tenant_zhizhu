package com.minigod.zero.trade.hs.constants;

/**
 * 恒生系统接口使用到的参数常量
 * @author sunline
 *
 */
public class GrmHsConstants {

	/**
	 * 字典key
	 */
	public interface DICT_KEY {
		String BUSINESS_FLAG = "business_flag";
	}

	/**
	 * 市场代码
	 */
	public enum MarketCode {
		HK, // 港股
		US, // 美股
		A // A股
	}

	/**
	 * 恒生交易类别
	 * "K"	香港
	 * "1"	上海A
	 * "D"	上海B
	 * "2"	深圳A
	 * "H"	深证B
	 * "P"	美股
	 * "t"	沪股通
	 * @author sunline
	 *
	 */
	public enum ExchangeType {
		HK("K", "香港", "HK"),
		SHA("1", "	上海A", "SH"),
		SHB("D", "	上海B", "SH"),
		SZA("2", "	深圳A", "SZ"),
		SZB("H", "	深证B", "SZ"),
		US("P", "	美股", "US"),
		HGT("t", "	沪股通", "SH");

		private String exchangeType;
		private String etName;
		private String mktCode;

		ExchangeType(String exchangeType, String etName, String mktCode) {
			this.exchangeType = exchangeType;
			this.etName = etName;
			this.mktCode = mktCode;
		}

		public String getExchangeType() {
			return exchangeType;
		}

		public String getEtName() {
			return etName;
		}

		public String getMktCode() {
			return mktCode;
		}
	}

	/**
	 * ‘1’，‘2’，‘3’，4’，‘5’,‘6’分别表示account_content代表资金帐号，股东内码，和资金卡号，银行帐号，股东帐号，客户编号。
	 */
	public enum ContentType {
		FUND_ACCOUNT("1", "资金账号"),
		CLIENT_NO("6", "客户编号");
		//
		private String code;
		private String desc;

		ContentType(String entrustBs, String desc) {
			this.code = entrustBs;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}
	}

	/**
	 * 买卖方向
	 */
	public enum EntrustBs {
		BUY("1", "买入"),
		SELL("2", "卖出");
		//
		private String code;
		private String desc;

		EntrustBs(String entrustBs, String desc) {
			this.code = entrustBs;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		/**
		 * entrust_bs	委托买卖类别
		 * '1'	买入
		 * '2'	卖出
		 */
	}

	/**
	 * 委托类型（买卖、查询、撤单、改单、补单）
	 */
	public enum EntrustType {
		BUYSELL("0", "买卖"),
		QUERY("1", "查询"),
		CANCEL("2", "撤单"),
		APPEND("3", "补单"),
		MODIFY("B", "改单");
		//
		private String code;
		private String desc;

		EntrustType(String entrustType, String desc) {
			this.code = entrustType;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		/**
		 * entrust_type	委托类别
		 * '0'	买卖
		 * '1'	查询
		 * '2'	撤单
		 * '3'	补单
		 * 'B'	改单
		 */
	}

	/**
	 * 委托属性（订单类型，如竞价单、限价单等）
	 *
	 * entrust_prop	委托属性
	 * 0	(C)Limit Order
	 * d	Ao-At-Auction
	 * e	ELO
	 * g	Al-Auction Limit
	 * h	Limit
	 * i	Quote Order
	 * j	SLO
	 * k	Average Price Order
	 * l	Cancel(Cross Broker)
	 * m	Special Odd Lot
	 * n	委托填单
	 * o	Manual Trade
	 * p	Bulk Cancel
	 * r	Pre Opening
	 * s	Odd Lot
	 * t	Bulk Cancel(Cross Broker)
	 * u	Odd Lot Input
	 * v	Overseas
	 */
	public enum EntrustProp {
		LO_US("0", "限价单"), // 美股限价单
		AO("d", "竞价单"),
		ALO("g", "竞价限价单"),
		LO("h", "限价单"), // 港股限价单
		ELO("e", "增强限价单"),
		SLO("j", "特别限价单"),
		OLI("u", "碎股单"),// 港股碎股单
		;
		//
		private String code;
		private String desc;

		EntrustProp(String entrustProp, String desc) {
			this.code = entrustProp;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

	}

	public enum EntrustStatus {
		NO_REGISTER("0", "No Register(未报)"),
		WAIT_TO_REGISTER("1", "Wait to Register(待报)"),
		HOST_REGISTERED("2", "Host Registered(已报)"),
		WAIT_FOR_CANCEL("3", "Wait for Cancel(已报待撤)"),
		WAIT_FOR_CANCEL_PARTIALLY("4", "Wait for Cancel [Partially Matched](部成待撤)"),
		PARTIALLY_CANCELLED("5", "Partially Cancelled(部撤)"),
		CANCELLED("6", "Cancelled(已撤)"),
		PARTIALLY_FILLED("7", "Partially Filled(部成)"),
		FILLED("8", "Filled(已成)"),
		HOST_REJECT("9", "Host Reject(废单)"),

		ALPHA_WAIT_FOR_MODIFY("A", "Wait for Modify [Registed]"),
		ALPHA_UNREGISTERED("B", "Unregistered"),
		ALPHA_REGISTERING("C", "Registering"),
		ALPHA_REVOKE_CANCEL("D", "Revoke Cancel"),
		ALPHA_WAIT_FOR_CONFIRMING("W", "Wait for Confirming"),
		ALPHA_PRE_FILLED("X", "Pre Filled"),
		ALPHA_WAIT_FOR_MODIFY_PARTIALLY("E", "Wait for Modify [Partially Matched]"),
		ALPHA_REJECT("F", "Reject"),
		ALPHA_CANCELLED("G", "Cancelled[Pre-Order]"),
		ALPHA_WAIT_FOR_REVIEW("H", "Wait for Review"),
		ALPHA_REVIEW_FAIL("J", "Review Fail");

		private String code;
		private String desc;
		EntrustStatus(String entrustProp, String desc) {
			this.code = entrustProp;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}
	}

	/**
	 * 费用类型
	 * @author sunline
	 */
	public enum FeeType {
		PERCENT("0", "Percent"),
		AMOUNT("1", "Amount"),
		PERCENT_PER_LOT("2", "Percent Per Lot"),
		AMOUNT_PER_QTY("5", "Amount Per Qty"),
		ROUNDUP_TO_CCY("7", "RoundupToccy");

		private String code;
		private String desc;
		FeeType(String entrustProp, String desc) {
			this.code = entrustProp;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}
	}

	/**
	 * 委托方式
	 * @author sunline
	 */
	public enum EntrustWay {
		TELEPHONE("1", "电话委托"),
		COUNTER("4", "柜台委托"),
		INTERNET("7", "Internet委托"),
		MOBILE("8", "手机委托"),
		ENTERPRISE_NET_BANK("A", "企业网银"),
		PERSONAL_NET_BANK("B", "个人网银"),
		FIX("F", "Fix渠道");

		private String code;
		private String desc;
		EntrustWay(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}
	}

	public interface IPO_APPLY_TYPE {
		String CASH = "0"; // 现金申购
		String FINANCING = "1"; // 融资申购
	}

	public enum UserType {
		TELLER("1"), // 柜员
		BROKER("2"), // 经纪人
		CUSTOMER("3"); // 客户

		private String code;
		UserType(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}
	}

	/**基金类型*/
	public enum FundType {
		SIGN("1"), // 签约基金
		CONSIGNMENT("2"), // 代销基金
		;

		private String code;
		FundType(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}
	}

	public interface DiscTypeEnum {
		int TYPE_0 = 0; //"不允许盘前盘后触发"
		int TYPE_1 = 1; //"允许盘前盘后触发"
	}

	public enum CurrencyType{
		CNY("0"), // 人民币
		USD("1"), // 美元
		HKD("2"), // 港币
		;

		private String code;
		CurrencyType(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}
		public static String getCurrencyType(String code) {
			for (CurrencyType type : values()) {
				if (type.getCode().equals(code)) {
					return type.name();
				}
			}
			return null;
		}
	}

}
