package com.minigod.zero.data.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class SystemCommonEnum {
	private static final Map<Integer, CodeType> map = new HashMap<>();

	/**
	 * 公共模块相关
	 */
	public enum CodeType {
		OK(0, "调用成功"),
		ERROR(-1, "error"),
		NONE_DATA(201, "无满足条件的数据"),
		EXIST_ERROR(300, "有重复值存在"),
		PARAMETER_DISMATCH(301, "参数不匹配"),
		PARAMETER_ERROR(400, "参数错误"),
		//PARAMS_PARAMETER_ERROR(401, "params参数错误"), // 未登录跳转状态
		SING_PARAMETER_ERROR(402, "签名参数SIGN错误"),
		KEY_PARAMETER_ERROR(403, "签名参数KEY错误"),
		SESSION_PARAMETER_ERROR(405, "参数SESSION_ID错误"),
		SOCKET_ERROR(404, "网络异常"),
		INTERNAL_ERROR(500, "请求异常，请重试"),
		UNBIND_WECHAT_ACC(600, "未绑定微信账号"),
		DISPLAY_ERROR(888, "系统异常"),
		SIGN_ERROR(889, "签名错误"),
		ERROR_UNKNOWN(9999, "未知错误"),
		SESSION_INVALID(-9999, "未登录或者session已失效"),
		WEB_SUCCESS(0, "ok"),
		WEB_ERROR(-1, "error"),
		WEB_DUPLICATE(-2, "exist"),
		UN_SUPPORTED(-100, "不支持的方法"),
		FREEZE_ERROR(101, "冻结出错"),
		NONE_DBS_TRANSFER(102, "非DBS银行付款"),
		DATA_FORMAT_ERROR(103, "数据格式错误"),
		ERROR_NOT_LOGIN(401, "未登录"),
		BANK_PROCESS_FAIL(402, "DBS银行处理失败"),
		BANK_PROCESSING(403, "DBS银行处理中"),
		BANK_EXEC_LIMITED(404, "超过限额"),
		GT_WITHDRAW_NONE_SUCCESS(405, "柜台出账还未成功"),
		API_GATEWAY_ERROR(501, "DBS网关错误"),
		/**
		 * 银行卡登记
		 */
		OPEN_BANK_CARD_REG_EXISTED(20000, "收款银行卡已登记"),
		/**
		 * DBS 银行操作
		 */
		DBS_REMIT_TSE_FAILED_NO_SETTLEMENT(3000, "DBS付款失败未完成结算"),
		DBS_REMIT_UNCOMMIT(3001, "DBS付款未提交，请等待自动调度处理"),
		DBS_REMIT_PROCESSED(3002, "DBS付款状态已为最新"),
		DBS_REMIT_NONE_REMITTANCE(3003, "非RTGS/TT支付通道无需更新"),

		;

		private int code;
		private String message;

		CodeType(int code, String message) {
			this.code = code;
			this.message = message;
			map.put(code, this);
		}

		public int getCode() {
			return code;
		}

		public String code() {
			return String.valueOf(code);
		}

		public String getMessage() {
			return message;
		}

		public static String getMessage(int code) {
			CodeType codeType = map.get(code);
			if (codeType == null) {
				return ERROR_UNKNOWN.getMessage();
			}
			return codeType.getMessage();
		}
	}

	public enum BusinessType {

		UN_KNOW(0, "unKnow", "未知"),
		BANK_CARD(1, "bankCard", "客户银行卡"),
		DEPOSIT_VOUCHER(2, "despositVouch", "入金凭证");

		private Integer code;
		private String name;
		private String desc;

		BusinessType(Integer code, String name, String desc) {
			this.code = code;
			this.name = name;
			this.desc = desc;
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public static BusinessType getEnum(int code) {
			for (BusinessType typeEnum : BusinessType.values()) {
				if (typeEnum.getCode() == code) {
					return typeEnum;
				}
			}
			return null;
		}
	}

	/**
	 * 是否类型
	 */
	public enum YesNo {
		NO(0, "否"),
		YES(1, "是");

		private final int index;
		private final String name;

		YesNo(int index, String name) {
			this.index = index;
			this.name = name;
		}

		public static String getName(int index) {
			for (YesNo c : YesNo.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		public int getIndex() {
			return index;
		}

		public String getIndexStr() {
			return String.valueOf(index);
		}

		public String getName() {
			return name;
		}
	}

	public enum BankCardTypeEnum {
		HK(1, "香港银行卡", "香港本地银行"),
		CN(2, "中国大陆银行", "中国大陆银行"),
		OTHER(3, "海外银行", "海外银行");

		@Getter
		private final Integer type;
		@Getter
		private final String area;
		@Getter
		private final String name;

		public String getTypeStr() {
			return String.valueOf(type);
		}

		BankCardTypeEnum(Integer type, String name, String area) {
			this.type = type;
			this.name = name;
			this.area = area;
		}

		public static String getName(Integer type) {
			for (BankCardTypeEnum c : BankCardTypeEnum.values()) {
				if (c.type.equals(type)) {
					return c.name;
				}
			}
			return null;
		}

		public static String getArea(Integer type) {
			for (BankCardTypeEnum c : BankCardTypeEnum.values()) {
				if (c.type.equals(type)) {
					return c.area;
				}
			}
			return null;
		}
	}

	/**
	 * 币种代码
	 */
	public enum MoneyType {
		CNY(0, "CNY", "人民币"),
		USD(1, "USD", "美元"),
		HKD(2, "HKD", "港币");

		@Getter
		private final String name;
		@Getter
		private final Integer index;
		@Getter
		private final String cname;

		MoneyType(Integer index, String name, String cname) {
			this.index = index;
			this.name = name;
			this.cname = cname;
		}

		public static String getName(Integer index) {
			for (MoneyType c : MoneyType.values()) {
				if (c.getIndex().equals(index)) {
					return c.name;
				}
			}
			return null;
		}

		public static String getCname(String name) {
			for (MoneyType c : MoneyType.values()) {
				if (c.getName().equals(name)) {
					return c.cname;
				}
			}
			return null;
		}

		public static String getValue(String name) {
			for (MoneyType c : MoneyType.values()) {
				if (c.getName().equals(name)) {
					return c.name;
				}
			}
			return null;
		}

		public static String getIndexStr(String name) {
			for (MoneyType c : MoneyType.values()) {
				if (c.getName().equals(name)) {
					return String.valueOf(c.index);
				}
			}
			return null;
		}

		public static Integer getIndex(String name) {
			for (MoneyType c : MoneyType.values()) {
				if (c.getName().equals(name)) {
					return c.index;
				}
			}
			return null;
		}
	}

	/**
	 * 申请来源[1-网上交易  2-网上营业厅 3-综合后台录入 6-手机证券]
	 */
	public enum ApplySource {
		ONLINE_TRADE(1, "客户提交"),
		BACK_ADMIN(2, "后台录入");
		@Getter
		private final Integer type;
		private String name;

		ApplySource(Integer type, String name) {
			this.type = type;
			this.name = name;
		}

		public static String getName(Integer type) {
			for (ApplySource c : ApplySource.values()) {
				if (c.type.equals(type)) {
					return c.name;
				}
			}
			return null;
		}
	}

	/**
	 * 币种代码（英文）
	 */
	public enum SecMoneyTypeEn {

		SEC_MONEY_TYPE_EN_CNY("CNY", "0"),
		SEC_MONEY_TYPE_EN_USD("USD", "1"),
		SEC_MONEY_TYPE_EN_HKD("HKD", "2");

		private final String index;
		private final String name;

		SecMoneyTypeEn(String index, String name) {
			this.index = index;
			this.name = name;
		}

		public static String getName(String index) {
			for (SecMoneyTypeEn c : SecMoneyTypeEn.values()) {
				if (c.getIndex().equals(index)) {
					return c.name;
				}
			}
			return null;
		}

		public static String getIndex(String name) {
			for (SecMoneyTypeEn c : SecMoneyTypeEn.values()) {
				if (c.getName().equals(name)) {
					return c.getIndex();
				}
			}
			return null;
		}

		public String getIndex() {
			return index;
		}

		public String getName() {
			return name;
		}
	}


	/**
	 * 取款方式
	 */
	public enum TransferTypeEnum {
		OVERSEAS(1, "电汇至香港以外银行", "电汇"),
		HK(2, "香港银行普通转账", "转账提款"),
		HK_LOCAL(3, "香港银行本地转账", "特快转账提款"),
		CHECK(4, "支票", "支票"),
		FPSID(5, "香港快捷FPS ID", "FPSID"),
		OFFLINE(99, "线下转账", "手工转账");

		@Getter
		private final Integer type;
		@Getter
		private final String name;
		@Getter
		private final String payName;

		TransferTypeEnum(Integer type, String name, String payName) {
			this.type = type;
			this.name = name;
			this.payName = payName;
		}

		public static String getName(Integer type) {
			for (TransferTypeEnum c : TransferTypeEnum.values()) {
				if (c.getType().equals(type)) {
					return c.name;
				}
			}
			return null;
		}

		public static TransferTypeEnum getEnum(Integer type) {
			for (TransferTypeEnum typeEnum : TransferTypeEnum.values()) {
				if (typeEnum.getType().equals(type)) {
					return typeEnum;
				}
			}
			return null;
		}

	}

	/**
	 * 通用处理过程状态
	 */
	public enum CommonProcessStatus {
		COMMON_PROCESS_STATUS_UN_KNOW(0, 0),    // 未知
		COMMON_PROCESS_STATUS_WAITING(1, 1),    // 待处理
		COMMON_PROCESS_STATUS_SUCCEED(2, 2),    // 成功
		COMMON_PROCESS_STATUS_FAILED(3, 3),     // 失败
		COMMON_PROCESS_STATUS_IN_PROCESS(4, 4), // 处理中
		COMMON_PROCESS_STATUS_UN_PROCESS(5, 5); // 未处理

		public static final int COMMON_PROCESS_STATUS_UN_KNOW_VALUE = 0;
		public static final int COMMON_PROCESS_STATUS_WAITING_VALUE = 1;
		public static final int COMMON_PROCESS_STATUS_SUCCEED_VALUE = 2;
		public static final int COMMON_PROCESS_STATUS_FAILED_VALUE = 3;
		public static final int COMMON_PROCESS_STATUS_IN_PROCESS_VALUE = 4;
		public static final int COMMON_PROCESS_STATUS_UN_PROCESS_VALUE = 5;

		public final int getNumber() {
			return value;
		}

		public static CommonProcessStatus valueOf(int value) {
			switch (value) {
				case 0:
					return COMMON_PROCESS_STATUS_UN_KNOW;
				case 1:
					return COMMON_PROCESS_STATUS_WAITING;
				case 2:
					return COMMON_PROCESS_STATUS_SUCCEED;
				case 3:
					return COMMON_PROCESS_STATUS_FAILED;
				case 4:
					return COMMON_PROCESS_STATUS_IN_PROCESS;
				case 5:
					return COMMON_PROCESS_STATUS_UN_PROCESS;
				default:
					return null;
			}
		}

		private final int value;

		private CommonProcessStatus(int index, int value) {
			this.value = value;
		}

	}

	/**
	 * 通用处理结果
	 */
	public enum CommonProcessResultStatus {

		COMMON_PROCESS_STATUS_WAIT(0, 0, "待执行"),    // 未知
		COMMON_PROCESS_STATUS_SUCCEED(1, 1, "成功"),    // 成功
		COMMON_PROCESS_STATUS_FAILED(2, 2, "失败");    // 失败

		public static final int COMMON_PROCESS_STATUS_WAIT_VALUE = 0;
		public static final int COMMON_PROCESS_STATUS_SUCCEED_VALUE = 1;
		public static final int COMMON_PROCESS_STATUS_FAILED_VALUE = 2;

		public final int getNumber() {
			return value;
		}

		public static CommonProcessResultStatus valueOf(int value) {
			switch (value) {
				case 0:
					return COMMON_PROCESS_STATUS_WAIT;
				case 1:
					return COMMON_PROCESS_STATUS_SUCCEED;
				case 2:
					return COMMON_PROCESS_STATUS_FAILED;
				default:
					return null;
			}
		}

		@Getter
		private final int value;
		@Getter
		private final String desc;

		private CommonProcessResultStatus(int index, int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

	}

	/**
	 * DBS付款方式
	 * 1-FPS PPP 2-FPS GCP 3-ACT DBS同行转账 4-RTGS 香港本地转账 5-TT 海外电汇
	 */
	public enum PayTypeEnum {

		UN_KNOW(0, "UN_KNOW", "", "未知"),
		FPS_PPP(1, "FPS_PPP", "PPP", "香港快捷PPP接口"),
		FPS_GPP(2, "FPS_GPP", "GPP", "香港快捷GPP接口"),
		DBS_ACT(3, "DBS_ACT", "ACT", "DBS同行转账接口"),
		DBS_RTGS(4, "DBS_RTGS", "RTGS", "DBS香港本地转账接口 Chats/RTGS"),
		DBS_TT(5, "TT", "TT", "DBS国际转账接口"),
		FPS_GCP(6, "FPS_GCP", "GCP", "香港快捷GCP接口");

		@Getter
		private int index;
		@Getter
		private String value;
		@Getter
		private String shortValue;
		@Getter
		private String desc;

		PayTypeEnum(int index, String value, String shortValue, String desc) {
			this.index = index;
			this.value = value;
			this.shortValue = shortValue;
			this.desc = desc;
		}

		public static PayTypeEnum getEnum(String value) {
			for (PayTypeEnum typeEnum : PayTypeEnum.values()) {
				if (typeEnum.getValue().equals(value)) {
					return typeEnum;
				}
			}
			return null;
		}

		public static PayTypeEnum getEnum(int value) {
			for (PayTypeEnum typeEnum : PayTypeEnum.values()) {
				if (typeEnum.getIndex() == value) {
					return typeEnum;
				}
			}
			return null;
		}

	}

	// 银行状态 0未提交 1失败 2成功 3 已提交 4 处理中
	public enum BankStateTypeEnum {
		UN_COMMNIT(0, "未提交"),
		FAIL(1, "失败"),
		SUCCESS(2, "成功"),
		COMMITTED(3, "已提交"),
		PROCESSING(4, "处理中"),
		COMMITTING(5, "正在提交中");

		private int value;
		private String desc;

		BankStateTypeEnum(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public static BankStateTypeEnum getEnum(Integer value) {
			for (BankStateTypeEnum typeEnum : BankStateTypeEnum.values()) {
				if (typeEnum.value == value) {
					return typeEnum;
				}
			}
			return null;
		}
	}

	public enum EDDABankStateTypeEnum {
		UN_COMMNIT(0, "未提交"),
		AUTHORIZING(1, "处理中"),
		FAIL(2, "银行处理失败"),
		SUCCESS(3, "银行处理成功"),
		ENTRYFAILURE(4, "入账失败"),
		LOSSOFENTRY(5, "入账成功");

		private int value;
		private String desc;

		EDDABankStateTypeEnum(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public static String getDesc(Integer index) {
			if (index == null) {
				return null;
			}
			for (EDDABankStateTypeEnum c : EDDABankStateTypeEnum.values()) {
				if (c.getValue() == index) {
					return c.getDesc();
				}
			}
			return null;
		}


	}

	/**
	 * edda_state
	 * 0未提交(待审核) 1已审核 2授权中 3授权失败(审核失败) 4已授权 5撤销授权
	 */
	public enum EddaStateEnum {
		UNCONMMIT(0, "未提交(待审核)"),
		CHECK(1, "已审核"),
		AUTHORIZING(2, "授权中"),
		FAIL(3, "授权失败"),
		SUCCESS(4, "授权成功"),
		RELIEVE(5, "撤销授权");

		private EddaStateEnum(int code, String message) {
			this.code = code;
			this.message = message;
		}

		private int code;
		private String message;

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}

	/**
	 * DBS相应状态
	 */
	public enum BankRespStatus {
		ACTC("ACTC"),
		RJCT("RJCT"),
		ACWC("ACWC"),
		ACSP("ACSP"),
		ACCP("ACCP"),
		PDNG("PDNG");


		@Getter
		private String type;

		BankRespStatus(String type) {
			this.type = type;
		}
	}

	/*
	 * 请求状态
	 */
	public enum BankEnqType {
		ACK1("ACK1"),
		ACK2("ACK2"),
		ACK3("ACK3");

		@Getter
		private String type;

		BankEnqType(String type) {
			this.type = type;
		}
	}

	/**
	 * 报表类型 1-取款信息模板文件 2-取款汇总模板文件
	 */
	public enum WithdrawReportTypeEnum {

		ITEM_REPORT(1, "取款明细报表"),
		SUMMARY_REPORT(2, "取款汇总报表"),
		SCB_COMMON_CSV_REPORT(3, "渣打普通报表"),
		SCB_TT_CSV_REPORT(4, "渣打电汇报表"),
		BANK_ITEM_PDF_PRINT(5, "取款明细打印"),
		BANK_SUMMARY_PDF_PRINT(6, "取款汇总打印"),

		SCB_RTGS_CSV_REPORT(7, "渣打特快报表");

		@Getter
		private Integer type;
		@Getter
		private String name;

		WithdrawReportTypeEnum(Integer type, String name) {
			this.type = type;
			this.name = name;
		}

		public static WithdrawReportTypeEnum getEnum(Integer type) {
			for (WithdrawReportTypeEnum typeEnum : WithdrawReportTypeEnum.values()) {
				if (typeEnum.type.equals(type)) {
					return typeEnum;
				}
			}
			return null;
		}
	}

	/**
	 * 导出文件模板定义 1-取款信息模板文件 2-取款汇总模板文件
	 */
	public enum TemplateFileTypeEnum {
		WITHDRAW_ITEM_TEMPLATE(1, "template/excel/withdraw_item_template.xlsx", "取款信息模板文件"),
		WITHDRAW_SUMMARY_TEMPLATE(2, "template/excel/withdraw_summary_template.xlsx", "取款汇总模板文件"),
		WITHDRAW_ITEM_PRINT_TEMPLATE(3, "template/pdf/withdraw_item_print_template.pdf", "明细打印模板文件"),
		BANKINFO_ITEM_TEMPLATE(4, "template/excel/bankinfo_item_template.xlsx", "明细打印模板文件");

		@Getter
		private Integer type;
		@Getter
		private String name;
		@Getter
		private String desc;

		TemplateFileTypeEnum(Integer type, String name, String desc) {
			this.type = type;
			this.name = name;
			this.desc = desc;
		}

		public static TemplateFileTypeEnum getEnum(Integer type) {
			for (TemplateFileTypeEnum typeEnum : TemplateFileTypeEnum.values()) {
				if (typeEnum.type.equals(type)) {
					return typeEnum;
				}
			}
			return null;
		}
	}

	/**
	 * 国际
	 */
	public enum NationalityTypeEnum {
		CHINA("0", "中国大陆"),
		HANGKONG("1", "中国香港"),
		OVERSEAS("2", "海外");

		@Getter
		private final String type;
		@Getter
		private final String name;

		NationalityTypeEnum(String type, String name) {
			this.type = type;
			this.name = name;
		}

		public static String getName(String type) {
			for (NationalityTypeEnum c : NationalityTypeEnum.values()) {
				if (c.type.equals(type)) {
					return c.name;
				}
			}
			return null;
		}

		public static NationalityTypeEnum getEnum(String type) {
			for (NationalityTypeEnum typeEnum : NationalityTypeEnum.values()) {
				if (typeEnum.getType().equals(type)) {
					return typeEnum;
				}
			}
			return null;
		}
	}

	/**
	 * 性别
	 */
	public enum SexTypeEnum {
		BOY("0", "男"),
		GIRL("1", "女"),
		OTHER("2", "其他");

		@Getter
		private final String type;
		@Getter
		private final String name;

		SexTypeEnum(String type, String name) {
			this.type = type;
			this.name = name;
		}

		public static String getName(String type) {
			for (SexTypeEnum c : SexTypeEnum.values()) {
				if (c.type.equals(type)) {
					return c.name;
				}
			}
			return null;
		}

		public static SexTypeEnum getEnum(String type) {
			for (SexTypeEnum typeEnum : SexTypeEnum.values()) {
				if (typeEnum.getType().equals(type)) {
					return typeEnum;
				}
			}
			return null;
		}
	}

	/**
	 * 手续费扣除方式[1-从提取金额中扣除,2-从账户余额中扣除]
	 */
	public enum DeductWay {
		/**
		 * 中国
		 */
		WITHDRAW(1, "从提取金额中扣除"),
		/**
		 * 中国香港
		 */
		BALANCE(2, "从余额中扣除");

		@Getter
		private final Integer type;
		@Getter
		private final String name;

		DeductWay(Integer type, String name) {
			this.type = type;
			this.name = name;
		}

		public static String getName(Integer type) {
			for (DeductWay c : DeductWay.values()) {
				if (c.type.equals(type)) {
					return c.name;
				}
			}
			return null;
		}

		public static DeductWay getEnum(Integer type) {
			for (DeductWay typeEnum : DeductWay.values()) {
				if (typeEnum.getType().equals(type)) {
					return typeEnum;
				}
			}
			return null;
		}
	}


	/**
	 * 期货出入账结果
	 */
	public enum FutCashResultType {

		FAIL("FAIL", "失败"),
		APPLY("APPLY", "申请中需审核"),
		SUCCESS("SUCCESS", "成功"),
		DUPLICATION("DUPLICATION", "重复指令"),
		APPLY_OK_CHECK_FAIL("APPLY_OK_CHECK_FAIL", "申请成功审核失败");

		@Getter
		private final String value;
		@Getter
		private final String name;

		FutCashResultType(String value, String name) {
			this.value = value;
			this.name = name;
		}

	}

	/**
	 * 证券账号类型
	 */
	public enum SecAccountType {

		CASH(1, "88", "CASH", "现金账户"),
		FUT(2, "99", "FET", "期货账号"),
		MARGIN(3, "66", "MARGIN", "保证金账户"),
		INVEST(4, "89", "INVEST", "投资移民账户");

		@Getter
		private Integer index;
		@Getter
		private final String type;
		@Getter
		private final String value;
		@Getter
		private final String desc;

		SecAccountType(Integer index, String type, String value, String desc) {
			this.index = index;
			this.type = type;
			this.value = value;
			this.desc = desc;
		}

		public static SecAccountType getEnum(String type) {
			for (SecAccountType typeEnum : SecAccountType.values()) {
				if (typeEnum.getType().equals(type)) {
					return typeEnum;
				}
			}
			return null;
		}

	}

	/**
	 * 银行账户类型 1-银行卡 2-fps id 3-fps phone number 4-fps mail 5-HKID ]
	 */
	public enum BankAcctType {

		BANK_CARD("1", "B", "bankCard", "银行卡"),
		FPS_ID("2", "S", "FPS ID", "FPS ID"),
		FPS_MOBILE("3", "M", "FPS MObile", "电话号码"),
		FPS_MAIL("4", "E", "FPS Email", "邮箱"),
		FPS_HKID("5", "H", "FPS HKID", "HKID");

		@Getter
		private String code;
		@Getter
		private String proxyType;
		@Getter
		private String name;
		@Getter
		private String desc;

		public Integer getIntCode() {
			return Integer.valueOf(code);
		}

		BankAcctType(String code, String proxyType, String name, String desc) {
			this.code = code;
			this.proxyType = proxyType;
			this.name = name;
			this.desc = desc;
		}

		public static BankAcctType getEnum(String code) {
			for (BankAcctType typeEnum : BankAcctType.values()) {
				if (typeEnum.getCode().equals(code)) {
					return typeEnum;
				}
			}
			return null;
		}
	}

	/**
	 * 入金类型
	 */
	public enum DepositType {
		/**
		 * 香港银行卡
		 */
		HK(0, 1, "香港银行卡", "香港本地银行"),
		/**
		 * 大陆银行卡
		 */
		CN(1, 2, "中国大陆银行", "中国大陆银行"),
		/**
		 * 海外银行卡
		 */
		OTHER(2, 3, "海外银行", "海外银行");
		@Getter
		private final Integer type;
		@Getter
		private final Integer wtmBankType;
		@Getter
		private final String area;
		@Getter
		private final String name;

		DepositType(Integer type, Integer wtmBankType, String name, String area) {
			this.type = type;
			this.wtmBankType = wtmBankType;
			this.name = name;
			this.area = area;
		}

		public static Integer getWtmBankType(Integer type) {
			for (DepositType c : DepositType.values()) {
				if (c.type.equals(type)) {
					return c.wtmBankType;
				}
			}
			return null;
		}

		public static String getName(Integer type) {
			for (BankCardTypeEnum c : BankCardTypeEnum.values()) {
				if (c.type.equals(type)) {
					return c.name;
				}
			}
			return null;
		}

		public static String getArea(Integer type) {
			for (BankCardTypeEnum c : BankCardTypeEnum.values()) {
				if (c.type.equals(type)) {
					return c.area;
				}
			}
			return null;
		}
	}

	/**
	 * DBS FPSID响应状态描述
	 */
	public enum BankRespStatusDesc {
		Matched("Matched"),
		Unmatched("Unmatched");

		@Getter
		private String type;

		BankRespStatusDesc(String type) {
			this.type = type;
		}
	}

	/**
	 * 柜台资金存入类型
	 */
	public enum FundDepositType {
		REFUND_WITHDRAWAL(1, "退还取款金额"),
		REFUND_CHARGE_FEE(2, "退还取款手续费");

		@Getter
		private int type;
		@Getter
		private String desc;

		FundDepositType(int type, String desc) {
			this.type = type;
			this.desc = desc;
		}
	}

	/**
	 * 证件类型
	 */
	public enum IdKindType {
		ID("1", "中国内地居民身份证"),
		HKID("2", "香港永久居民身份证"),
		PASSPORT("3", "护照"),
		OTHER("OTHER", "其他");

		@Getter
		private String type;
		@Getter
		private String desc;

		IdKindType(String type, String desc) {
			this.type = type;
			this.desc = desc;
		}

		public static String getDesc(String type) {
			for (IdKindType kindType : IdKindType.values()) {
				if (kindType.type.equals(type)) {
					return kindType.desc;
				}
			}
			return null;
		}
	}

	public enum FundDepositStatus {
		// 状态 1 凭证处理中 2 流水核对中 3入账中 4 入账成功 5 入账失败 6 拒绝 7取消
		PROOF_AUDIT( 1, "凭证处理中"),
		COLLATE_AUDIT(2, "流水核对中"),
		DEPOSIT_PENDING(3, "入账中"),
		DEPOSIT_SUCCESS(4, "入账成功"),
		DEPOSIT_FAIL(5, "入账失败"),
		REJECT(6, "已拒绝"),
		CANCEL(7, "已取消"),

		;
		@Getter
		private Integer status;
		@Getter
		private String desc;

		public static SystemCommonEnum.FundDepositStatus valueOf(int value) {
			for (SystemCommonEnum.FundDepositStatus status : SystemCommonEnum.FundDepositStatus.values()) {
				if (status.getStatus().equals(value)) {
					return status;
				}
			}
			return null;
		}

		FundDepositStatus(Integer status, String desc) {
			this.status = status;
			this.desc = desc;
		}
	}

}
