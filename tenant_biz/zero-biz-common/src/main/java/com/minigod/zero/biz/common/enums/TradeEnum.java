package com.minigod.zero.biz.common.enums;

/**
 * @author:yanghu.luo
 * @create: 2023-07-14 10:44
 * @Description:
 */
public class TradeEnum {

	/**
	 * HK:
	 * e：enhancedLimit(增强限价交易)
	 * j：specialLimit(特别限价交易)
	 * d：bidGap(竞价交易)
	 * g：bidGapLimit(竞价限价交易)
	 * w：marketValue(市价交易)
	 * t：conditionLimit(条件单)
	 * u：oddLots(碎股交易)
	 *
	 * US:
	 * 0：limit(限价交易)
	 * w：marketValue(市价交易)
	 * t：conditionLimit(条件单)
	 *
	 * 其他：
	 * 0：limit(限价交易)
	 * t：conditionLimit(条件单)
	 */
	public enum EntrustProp {
		HKE("e", "增强限价交易"),
		HKJ("j", "特别限价交易"),
		HKD("d", "竞价交易"),
		HKG("g", "竞价限价交易"),
		HKW("w", "市价交易"),
		HKT("t", "条件单"),
		HKU("u", "碎股交易"),
		US0("0", "限价交易"),
		USW("w", "市价交易"),
		UST("t", "条件单"),
		ML0("0", "限价交易"),
		MLT("t", "条件单")
		;
		String code;
		String info;
		EntrustProp(String code, String info) {
			this.code = code;
			this.info = info;
		}
		public String getCode() {
			return code;
		}
		public String getInfo() {
			return info;
		}
	}

	/**
	 * 有效期 1-当日有效；2-撤单前有效；3-2天；4-3天；5-2周；6-30天
	 */
	public enum DeadlineDate {
		TODAY("1", "当日有效"),
		BEFORE_REVOCATION("2", "撤单前有效"),
		TWO_DAYS("3", "2天"),
		THREE_DAYS("4", "3天"),
		TWO_WEEKS("5", "2周"),
		THIRTY_DAYS("6", "30天")
		;
		String code;
		String info;
		DeadlineDate(String code, String info) {
			this.code = code;
			this.info = info;
		}
		public String getCode() {
			return code;
		}
		public String getInfo() {
			return info;
		}
	}

	/**
	 * 0-普通交易，1-盘前，2-暗盘交易，3-盘后
	 */
	public enum SessionType {
		NORMAL("0", "普通交易"),
		BEFORE("1", "盘前"),
		PRIVATE("2", "暗盘交易"),
		AFTER("3", "盘后")
		;
		String code;
		String info;
		SessionType(String code, String info) {
			this.code = code;
			this.info = info;
		}
		public String getCode() {
			return code;
		}
		public String getInfo() {
			return info;
		}
	}
}
