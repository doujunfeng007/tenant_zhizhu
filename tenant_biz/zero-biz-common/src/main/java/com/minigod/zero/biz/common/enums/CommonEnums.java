package com.minigod.zero.biz.common.enums;

public class CommonEnums {

	public enum TerminalType {
		APP("APP", "2", "APP"),
		PC("PC", "3", "PC"),
		H5("H5", "1", "H5"),
		WEB("WEB", "1", "WEB");
		public String value;
		public String varietiesDevice;
		public String description;

		TerminalType(String value, String varietiesDevice, String description) {
			this.value = value;
			this.varietiesDevice = varietiesDevice;
			this.description = description;
		}

		public static String getVarietiesDevice(String value) {
			for (TerminalType terminalType : TerminalType.values()) {
				if (value.equalsIgnoreCase(terminalType.value)) {
					return terminalType.varietiesDevice;
				}
			}
			return null;
		}

		public static String getValue(String varietiesDevice) {
			for (TerminalType terminalType : TerminalType.values()) {
				if (varietiesDevice.equals(terminalType.varietiesDevice)) {
					return terminalType.value;
				}
			}
			return null;
		}
	}

	/**
	 * 适应人群
	 */
	public enum ShowTypeEnums {
		ALL(0, "全站", "A"),
		INDIVIDUAL(1, "个人", "P"),

		;
		private Integer value;
		private String desc;
		private String investValue;

		public Integer getValue() {
			return value;
		}

		public String getDesc() {
			return desc;
		}

		public String getInvestValue() {
			return investValue;
		}

		ShowTypeEnums(Integer value, String desc, String investValue) {
			this.value = value;
			this.desc = desc;
			this.investValue = investValue;
		}

		private static ShowTypeEnums getValue(Integer value) {
			for (ShowTypeEnums enums : ShowTypeEnums.values()) {
				if (enums.getValue() == value) {
					return enums;
				}
			}
			return null;
		}

		private static ShowTypeEnums getInvestValue(String investValue) {
			for (ShowTypeEnums enums : ShowTypeEnums.values()) {
				if (enums.getInvestValue().equalsIgnoreCase(investValue)) {
					return enums;
				}
			}
			return null;
		}
	}

	/**
	 * 跳转方式[1-打开app首页 2-资讯详情首页(若为快讯则定位快讯列表) 3-个股行情首页 4-指定H5链接]
	 */
	public enum JumpWay {
		APP_FIRST_PAGE(1, "打开app首页"),
		NEWS_FIRST_PAGE(2, "资讯详情首页(若为快讯则定位快讯列表)"),
		STOCK_FIRST_PAGE(3, "个股行情首页"),
		H5_URL(4, "指定H5链接"),

		;

		private Integer value;
		private String desc;

		public Integer getValue() {
			return value;
		}

		public String getDesc() {
			return desc;
		}

		JumpWay(Integer value, String desc) {
			this.value = value;
			this.desc = desc;
		}
	}

	/**
	 * 发送方式[0-即时 1-定时]
	 */
	public enum SendWay {
		IMMEDIATELY(0, "即时"),
		PERIODICALLY(1, "定时"),
		;

		private Integer value;
		private String desc;

		public Integer getValue() {
			return value;
		}

		public String getDesc() {
			return desc;
		}

		SendWay(Integer value, String desc) {
			this.value = value;
			this.desc = desc;
		}
	}

	public enum BoardType {
		_XSGG(1, "休市公告"),
		_XGGG(2, "新股信号公告"),

		;
		private int value;
		private String desc;

		public int getValue() {
			return value;
		}

		public String getDesc() {
			return desc;
		}

		private BoardType(int val, String desc) {
			this.value = val;
			this.desc = desc;
		}

		public static String getName(Integer index) {
			for (BoardType eBoardType : BoardType.values()) {
				if (eBoardType.getValue() == index) {
					return eBoardType.desc;
				}
			}
			return null;
		}
	}

	public enum NewsPushWay {
		PUSH(1, "推送"),
		SMS(2, "短信"),
		EMAIL(3, "邮件"),

		;
		private Integer value;
		private String desc;

		public Integer getValue() {
			return value;
		}

		public String getDesc() {
			return desc;
		}

		NewsPushWay(Integer value, String desc) {
			this.value = value;
			this.desc = desc;
		}
	}

	// Ipo预约配置缓存前缀
	public static final String PRE_IPO_CONFIG = "preIpoConfig_";
	// Ipo预约队列缓存前缀
	public static final String PRE_IPO_QUEUE = "preIpoQueue_";

	public static final int DEPOSIT_5 = 5;
	public static final int DEPOSIT_10 = 10;
	public static final int DEPOSIT_20 = 20;

	public static final int IS_QUEUE_STS = 1;

	public static final double DEPOSIT_RATE_20 = 0.95;

	public enum IpoQueueOrderType {
		TYP_1(1, "普通认购"),
		TYP_2(2, "VIP认购"),
		TYP_3(3, "0本金认购"),
		;
		public int value;
		public String description;

		IpoQueueOrderType(int value, String description) {
			this.value = value;
			this.description = description;
		}
	}
	/** 语言（新） **/
	public final static String REQ_LANG = "ACCEPT-LANGUAGE";
	public final static String LANG_DEFAULT = "zh-Hant";
	/**
	 * Ipo 预约融资认购新股队列状态表
	 */
	public enum IpoPredictFinanceQueueStatus {
		STS_0(0, "未开始"),
		STS_1(1, "可预约"),
		STS_2(2, "预约成功"),
		STS_3(3, "预约失败"),
		STS_4(4, "预约撤回"),
		STS_5(5, "已经提交排队"),
		STS_6(6, "预约结束");
		public int value;
		public String description;

		IpoPredictFinanceQueueStatus(int value, String description) {
			this.value = value;
			this.description = description;
		}
	}

	public enum IpoTradeResp {
		CODE_120022("120022","系统清算期间不能接受认购指令"),
		CODE_105322("105322","系统清算期间不能接受认购指令")
		;
		public String value;
		public String description;

		IpoTradeResp(String value,String description) {
			this.value = value;
			this.description = description;
		}
		public static String getIpoTradeRespDesc(String code){
			for(IpoTradeResp obj : IpoTradeResp.values()) {
				if(obj.value.equals(code)) return obj.description;
			}
			return null;
		}
	}
	public enum CustOperationType {
		IP0_APPLY(1,"提交IPO申购"),
		CANCEL_IPO_APPLY(2,"撤销IPO申购"),
		TRADE_SUBMIT(3,"交易下单"),
		TRADE_CANCEL(4,"交易撤单"),
		TRADE_EDIT(5,"交易改单"),
		CONDITION_SUBMIT(6,"条件单下单"),
		CONDITION_CANCEL(7,"条件单撤单"),
		CONDITION_EDIT(8,"条件单改单"),
		FUND_APPLY_INTO(9,"资金申请入金"),
		FUND_APPLY_OUT(10,"资金申请出金"),
		FUND_APPLY_EDDA(11,"资金申请eDDA"),
		FUND_APPLY_EXCHANGE(12,"资金申请换汇"),
		FUND_APPLY_EDDI(13,"资金eDDI划款"),
		FUND_APPLY_INTO_EDIT(14,"资金撤销入金申请"),
		FUND_APPLY_OUT_EDIT(15,"资金撤销出金申请"),
		STOCK_INTO(16,"股票申请转入股票"),
		STOCK_INTO_EDIT(17,"股票撤销转入申请"),
		FUND_SUBSCRIBE(18,"基金申购"),
		FUND_REDEEM(19,"基金赎回"),
		FUND_SUBSCRIBE_CANCEL(20,"基金撤销申购"),
		FUND_REDEEM_CANCEL(21,"基金撤销赎回"),
		FUND_SET_FIXED(22,"基金设置定投"),
		FUND_SET_FIXED_EDIT(23,"基金修改定投设置"),
		FUND_SET_FIXED_CANCEL(24,"基金取消定投设置"),
		RISK_RESULT_SUBMIT(25,"风险测评提交测评结果"),
		QUOTATION_BUY(26,"购买行情");
		public int code;
		public String description;

		CustOperationType(int code,String description) {
			this.code = code;
			this.description = description;
		}
	}

	public enum AppGradeUpMessage{
		APP_CHECK_MESSAGE_0("已是最新版本，无需升级","已是最新版本，無需升級","It’s already the latest version, no need to upgrade"),
		APP_CHECK_MESSAGE_1("发现新版本 (%s)","發現新版本 (%s)","New version found (%s)"),
		APP_CHECK_MESSAGE_2("发现新版本 (%s)","發現新版本 (%s)","New version found (%s)"),
		APP_CHECK_MESSAGE_3("发现新版本 (%s)","發現新版本 (%s)","New version found (%s)");
		public String sc;
		public String tc;
		public String en;
		AppGradeUpMessage(String sc,String tc,String en){
			this.sc = sc;
			this.tc = tc;
			this.en = en;
		}

	}

	public enum StatusEnum {
		YES(1, "是"), NO(0, "不是");

		int code;
		String info;

		StatusEnum(int code, String info) {
			this.code = code;
			this.info = info;
		}

		public int getCode() {
			return code;
		}
		public String getInfo() {
			return info;
		}
	}

	public enum ErrorLevelEnum {
		ERROR(1, "error"),
		WARN(2, "warn"),
		DEBUG(3, "debug"),
		INFO(4, "info")
		;

		int code;
		String info;

		ErrorLevelEnum(int code, String info) {
			this.code = code;
			this.info = info;
		}

		public int getCode() {
			return code;
		}
		public String getInfo() {
			return info;
		}
	}

	public enum OsTypeEnum {
		OS_ANDROID("安卓系统", 0), //
		OS_IOS("IOS系统", 1), //
		OS_WP("WP系统", 2), //
		OS_ALL("全部", 3),
		OS_OTHER("其他", 4); //

		private String typeName;
		private Integer typeValue;

		private OsTypeEnum(String typeName, Integer typeValue) {
			this.typeName = typeName;
			this.typeValue = typeValue;
		}

		public Integer getTypeValue() {
			return this.typeValue;
		}

		public static String getTypeName(Integer index) {
			for (OsTypeEnum osTypeEnum : OsTypeEnum.values()) {
				if (osTypeEnum.getTypeValue().equals(index)) {
					return osTypeEnum.typeName;
				}
			}
			return null;
		}

		public static Integer getTypeValue(Integer index) {
			for (OsTypeEnum osTypeEnum : OsTypeEnum.values()) {
				if (osTypeEnum.getTypeValue().equals(index)) {
					return osTypeEnum.getTypeValue();
				}
			}
			return null;
		}
	}
}
