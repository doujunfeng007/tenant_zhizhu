/**
 * @program: sunline
 * @description: 货币兑换枚举
 * @author: LEN
 * @create: 2021-09-06 16:21
 **/
package com.minigod.zero.bpmn.module.exchange.enums;

public class CurrencyExcEnum {

	// 账户类型
	public enum fundAccountType {
		FUND_ACCOUNT_TYPE_CASH(1, "现金账户"),
		FUND_ACCOUNT_TYPE_MARGIN(2, "孖展账户"),
		;
		private final int index;
		private final String value;

		public static final int FUND_ACCOUNT_TYPE_CASH_VALUE = 1;
		public static final int FUND_ACCOUNT_TYPE_MARGIN_VALUE = 2;

		public int getIndex() {
			return index;
		}

		public String getValue() {
			return value;
		}

		private fundAccountType(int index, String value) {
			this.index = index;
			this.value = value;
		}
	}

	/**
	 * 货币兑换审核状态
	 * 字典码：customer_currency_exchange_app_status
	 */
	public enum AppExchangeStatus {
		SUBMIT(0, "提交"),
		PENDING(1, "审核中"),
		PASS(2, "完成"),
		REJECT(3, "拒绝"),
		CANCEL(4, "已撤销"),
		FAIL(5, "失败"),
		HANG_UP(6, "挂起");
		private final int index;
		private final String value;

		public int getIndex() {
			return index;
		}

		public String getValue() {
			return value;
		}

		private AppExchangeStatus(int index, String value) {
			this.index = index;
			this.value = value;
		}

		public static AppExchangeStatus valueOf(int model) {
			switch (model) {
				case 0:
					return SUBMIT;
				case 1:
					return PENDING;
				case 2:
					return PASS;
				case 3:
					return REJECT;
				case 4:
					return CANCEL;
				case 5:
					return FAIL;
				case 6:
					return HANG_UP;
				default:
					return null;
			}
		}
	}

	/**
	 * 兑换方式[0-未知 1-线上兑换 2-手工兑换]
	 */
	public enum ExchangeMode {
		EXCHANGE_MODE_UNKNOWN(0, "未知"),
		EXCHANGE_MODE_ONLINE(1, "线上兑换"),
		EXCHANGE_MODE_MANUAL(2, "手工兑换");

		private final int index;
		private final String value;

		public int getIndex() {
			return index;
		}

		public String getValue() {
			return value;
		}

		private ExchangeMode(int index, String value) {
			this.index = index;
			this.value = value;
		}

		public static ExchangeMode valueOf(int model) {
			switch (model) {
				case 0:
					return EXCHANGE_MODE_UNKNOWN;
				case 1:
					return EXCHANGE_MODE_ONLINE;
				case 2:
					return EXCHANGE_MODE_MANUAL;
				default:
					return null;
			}
		}
	}

	/**
	 * 货币兑换申请状态[0-提交 1-待初审 2-待复审 3-兑换中 4-入账中 5-兑换失败 6-已撤销 7-入账成功 8-撤销失败 9-退审 10-挂起]
	 * 字典码：customer_currency_exchange_application_status
	 */
	public enum ExchangeApplicationStatus {
		// 0-提交 1-待初审 2-待复审 3-兑换中 4-入账中 5-兑换失败 6-已撤销 7-入账成功 8-撤销失败 9-退审 10-挂起
		EXCHANGE_APPLICATION_STATUS_SUBMIT(0, "提交"),
		EXCHANGE_APPLICATION_STATUS_FIRST_AUDIT(1, "待初审"),
		EXCHANGE_APPLICATION_STATUS_SECOND_AUDIT(2, "待复审"),
		EXCHANGE_APPLICATION_STATUS_EXCHANGEING(3, "兑换中"),
		EXCHANGE_APPLICATION_STATUS_INPUTING(4, "入账中"),
		EXCHANGE_APPLICATION_STATUS_EXCHANGE_FAIL(5, "兑换失败"),
		EXCHANGE_APPLICATION_STATUS_CANCEL(6, "已撤销"),
		EXCHANGE_APPLICATION_STATUS_INPUT_SUCCESS(7, "入账成功"),
		EXCHANGE_APPLICATION_STATUS_CANCEL_FAIL(8, "撤销失败"),
		EXCHANGE_APPLICATION_STATUS_REFUSE(9, "退审"),
		EXCHANGE_APPLICATION_STATUS_HANG_UP(10, "挂起");

		private final int index;
		private final String value;

		public int getIndex() {
			return index;
		}

		public String getValue() {
			return value;
		}

		private ExchangeApplicationStatus(int index, String value) {
			this.index = index;
			this.value = value;
		}

		public static ExchangeApplicationStatus valueOf(int model) {
			switch (model) {
				case 0:
					return EXCHANGE_APPLICATION_STATUS_SUBMIT;
				case 1:
					return EXCHANGE_APPLICATION_STATUS_FIRST_AUDIT;
				case 2:
					return EXCHANGE_APPLICATION_STATUS_SECOND_AUDIT;
				case 3:
					return EXCHANGE_APPLICATION_STATUS_EXCHANGEING;
				case 4:
					return EXCHANGE_APPLICATION_STATUS_INPUTING;
				case 5:
					return EXCHANGE_APPLICATION_STATUS_EXCHANGE_FAIL;
				case 6:
					return EXCHANGE_APPLICATION_STATUS_CANCEL;
				case 7:
					return EXCHANGE_APPLICATION_STATUS_INPUT_SUCCESS;
				case 8:
					return EXCHANGE_APPLICATION_STATUS_CANCEL_FAIL;
				case 9:
					return EXCHANGE_APPLICATION_STATUS_REFUSE;
				case 10:
					return EXCHANGE_APPLICATION_STATUS_HANG_UP;
				default:
					return null;
			}
		}
	}

	/**
	 * 货币兑换柜台处理状态[0-冻结失败 1-扣款失败 2-解冻失败 3-入账失败 4-成功]
	 * 字典码：exchange_counter_status
	 */
	public enum ExchangeProcessStatus {
		//0-冻结失败 1-扣款失败 2-解冻失败 3-入账失败 4-成功
		EXCHANGE_PROCESS_STATUS_FREEZE_FAIL(0, "冻结失败"),
		EXCHANGE_PROCESS_STATUS_DEDUCTION_FAIL(1, "扣款失败"),
		EXCHANGE_PROCESS_STATUS_UNFREEZE_FAIL(2, "解冻失败"),
		EXCHANGE_PROCESS_STATUS_INPUT_FAIL(3, "入账失败"),
		EXCHANGE_PROCESS_STATUS_SUCCESS(4, "成功");

		private final int index;
		private final String value;

		public int getIndex() {
			return index;
		}

		public String getValue() {
			return value;
		}

		private ExchangeProcessStatus(int index, String value) {
			this.index = index;
			this.value = value;
		}

		public static ExchangeProcessStatus valueOf(int model) {
			switch (model) {
				case 0:
					return EXCHANGE_PROCESS_STATUS_FREEZE_FAIL;
				case 1:
					return EXCHANGE_PROCESS_STATUS_DEDUCTION_FAIL;
				case 2:
					return EXCHANGE_PROCESS_STATUS_UNFREEZE_FAIL;
				case 3:
					return EXCHANGE_PROCESS_STATUS_INPUT_FAIL;
				case 4:
					return EXCHANGE_PROCESS_STATUS_SUCCESS;
				default:
					return null;
			}
		}
	}

	/**
	 * 币种兑换方向[1-港币兑换美元 2-美元兑换港币 3-人民币兑换美元 4-美元兑换人民币 5-港币兑换人民币 6-人民币兑换港币]
	 * 字典码：exchange_direction
	 */
	public enum ExchangeType {

		//[1-港币兑换美元 2-美元兑换港币 3-人民币兑换美元 4-美元兑换人民币 5-港币兑换人民币 6-人民币兑换港币]
		EXCHANGE_TYPE_HKD_TO_USD(1, 2, 1, "HKD to USD", "港币兑美元"),
		EXCHANGE_TYPE_USD_TO_HKD(2, 1, 2, "USD to HKD", "美元兑港币"),
		EXCHANGE_TYPE_CNY_TO_USD(3, 2, 3, "CNY to USD", "人民币兑美元"),
		EXCHANGE_TYPE_USD_TO_CNY(2, 3, 4, "USD to CNY", "美元兑人民币"),
		EXCHANGE_TYPE_HKD_TO_CNY(1, 3, 5, "HKD to CNY", "港币兑人民币"),
		EXCHANGE_TYPE_CNY_TO_HKD(3, 1, 6, "CNY to HKD", "人民币兑港币"),
		;
		private Integer ccySource;
		private Integer ccyTarget;
		private Integer exchangeDirection;
		private String descEn;
		private String descCn;

		public Integer getCcySource() {
			return ccySource;
		}

		public Integer getCcyTarget() {
			return ccyTarget;
		}

		public Integer getExchangeDirection() {
			return exchangeDirection;
		}

		public String getDescEn() {
			return descEn;
		}

		public String getDescCn() {
			return descCn;
		}

		ExchangeType(Integer ccySource, Integer ccyTarget, Integer exchangeDirection, String descEn, String descCn) {
			this.ccySource = ccySource;
			this.ccyTarget = ccyTarget;
			this.exchangeDirection = exchangeDirection;
			this.descEn = descEn;
			this.descCn = descCn;
		}

		public static ExchangeType valueOf(int exchangeDirection) {
			switch (exchangeDirection) {
				case 1:
					return EXCHANGE_TYPE_HKD_TO_USD;
				case 2:
					return EXCHANGE_TYPE_USD_TO_HKD;
				case 3:
					return EXCHANGE_TYPE_CNY_TO_USD;
				case 4:
					return EXCHANGE_TYPE_USD_TO_CNY;
				case 5:
					return EXCHANGE_TYPE_HKD_TO_CNY;
				case 6:
					return EXCHANGE_TYPE_CNY_TO_HKD;
				default:
					return null;
			}
		}

		public static ExchangeType valueOf(int ccySource, int ccyTarget) {
			for (ExchangeType e : ExchangeType.values()) {
				if (e.getCcySource() == ccySource && e.getCcyTarget() == ccyTarget) {
					return e;
				}
			}
			return null;
		}
	}

	// 币种代码（中文）
	public enum CurrencyType {

		SEC_MONEY_TYPE_EN_CNY("CNY", 3),
		SEC_MONEY_TYPE_EN_USD("USD", 2),
		SEC_MONEY_TYPE_EN_HKD("HKD", 1);

		private final String name;
		private final Integer index;

		CurrencyType(String name, Integer index) {
			this.index = index;
			this.name = name;
		}

		public static String getName(Integer index) {
			for (CurrencyType c : CurrencyType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		public static Integer getIndex(String name) {
			for (CurrencyType c : CurrencyType.values()) {
				if (c.getName().equals(name)) {
					return c.index;
				}
			}
			return null;
		}

		public Integer getIndex() {
			return index;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * 货币兑换方向[1-港币兑换美元 2-美元兑换港币 3-人民币兑换美元 4-美元兑换人民币 5-港币兑换人民币 6-人民币兑换港币]
	 */
	public enum CurrencyConversion {
		HKD_TO_USD(1, "HKD", "USD"),
		USD_TO_HKD(2, "USD", "HKD"),
		CNY_TO_USD(3, "CNY", "USD"),
		USD_TO_CNY(4, "USD", "CNY"),
		HKD_TO_CNY(5, "HKD", "CNY"),
		CNY_TO_HKD(6, "CNY", "HKD");

		private final Integer value;
		private final String fromCurrency;
		private final String toCurrency;

		// 私有构造函数，确保只能在枚举定义中创建实例
		CurrencyConversion(Integer value, String fromCurrency, String toCurrency) {
			this.value = value; // 注意：这个value在实际应用中可能并不适用，因为它对于枚举是固定的
			this.fromCurrency = fromCurrency;
			this.toCurrency = toCurrency;
		}

		// Getter方法
		public double getValue() {
			// 注意：这个值对于每个枚举实例都是固定的，可能并不符合实际需求
			return value;
		}

		public String getFromCurrency() {
			return fromCurrency;
		}

		public String getToCurrency() {
			return toCurrency;
		}

		public static String getFromCurrency(Integer value) {
			for (CurrencyConversion c : CurrencyConversion.values()) {
				if (c.getValue() == value) {
					return c.fromCurrency;
				}
			}
			return null;
		}

		public static String getToCurrency(Integer value) {
			for (CurrencyConversion c : CurrencyConversion.values()) {
				if (c.getValue() == value) {
					return c.toCurrency;
				}
			}
			return null;
		}

		public static CurrencyConversion valueOf(Integer value) {
			for (CurrencyConversion e : CurrencyConversion.values()) {
				if (e.getValue() == value) {
					return e;
				}
			}
			return null;
		}
	}
}

