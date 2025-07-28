package com.minigod.zero.bpmn.module.account.enums;

import lombok.Getter;

/**
 * @ClassName: AccountPdfEnum
 * @Description:
 * @Author chenyu
 * @Date 2022/8/18
 * @Version 1.0
 */
@Getter
public enum AccountPdfEnum {
	ACCOUNT_OPEN_REPORT_USER_FORM_REPORT(1, "开户文件"), // 开户报表
	ACCOUNT_OPEN_REPORT_USER_W8_REPORT(2, "W8"),   // W8报表
	ACCOUNT_OPEN_REPORT_USER_SELF_PROVE_REPORT(3, "自我证明报表"), // 自我证明报表
	ACCOUNT_OPEN_REPORT_USER_SELF_CERTIFICATION_ON_US_REPORT(4, "美国公民身份证明报表"), // 美国公民身份证明报表
	ACCOUNT_OPEN_REPORT_STANDING_AUTHORITY(5, "常设授权表"), // 常设授权表
	ACCOUNT_OPEN_REPORT_CLIENTS_SIGNATURE_CARD(6, "客户印鉴卡"), // 客户印鉴卡
	ACCOUNT_OPEN_REPORT_FUTURES_UNP_USER_SELF_PROVE_REPORT(7, "(期貨)非專業用戶自我認證"), // (期貨)非專業用戶自我認證
	ACCOUNT_OPEN_REPORT_FUTURES_AGREEMENT(8, "(期貨)訂閱協議"), // (期貨)訂閱協議
	ACCOUNT_OPEN_REPORT_PERSONAL_DATA_COLLECTION_STATEMENT(9, "个人资料收集声明"), // 个人资料收集声明
	ACCOUNT_OPEN_REPORT_MARGIN_TRADING_AGREEMENT(10, "保证金交易协议书"), // 保证金交易协议书
	ACCOUNT_OPEN_REPORT_SECURITIES_TRADING_AGREEMENT(11, "证券交易协议书"), // 证券交易协议书
	HONG_KONG_STOCK_FEE_SCHEDULE(12, "收费表"), // 香港股票收费表
	EXECUTE_TRADING_DISCLOSURE_DOCUMENTS_ON_OPTIMAL_TERMS(13, "以最佳条件执行交易披露文件"), // 以最佳条件执行交易披露文件
	ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT(14, "DigiFIN开户文件"), // DigiFIN开户报表
	;

	private static final int ACCOUNT_OPEN_REPORT_USER_FORM_REPORT_VALUE = 1;
	private static final int ACCOUNT_OPEN_REPORT_USER_W8_REPORT_VALUE = 2;
	private static final int ACCOUNT_OPEN_REPORT_USER_SELF_PROVE_REPORT_VALUE = 3;
	private static final int ACCOUNT_OPEN_REPORT_USER_SELF_CERTIFICATION_ON_US_REPORT_VALUE = 4;
	private static final int ACCOUNT_OPEN_REPORT_STANDING_AUTHORITY_VALUE = 5;
	private static final int ACCOUNT_OPEN_REPORT_CLIENTS_SIGNATURE_CARD_VALUE = 6;
	private static final int ACCOUNT_OPEN_REPORT_FUTURES_UNP_USER_SELF_PROVE_REPORT_VALUE = 7;
	private static final int ACCOUNT_OPEN_REPORT_FUTURES_AGREEMENT_VALUE = 8;
	private static final int ACCOUNT_OPEN_REPORT_PERSONAL_DATA_COLLECTION_STATEMENT_VALUE = 9;
	private static final int ACCOUNT_OPEN_REPORT_MARGIN_TRADING_AGREEMENT_VALUE = 10;
	private static final int ACCOUNT_OPEN_REPORT_SECURITIES_TRADING_AGREEMENT_VALUE = 11;
	private static final int HONG_KONG_STOCK_FEE_SCHEDULE_VALUE = 12;
	private static final int EXECUTE_TRADING_DISCLOSURE_DOCUMENTS_ON_OPTIMAL_TERMS_VALUE = 13;
	private static final int ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT_VALUE = 14;

	public AccountPdfEnum valueOf(int value) {
		switch (value) {
			case 1:
				return ACCOUNT_OPEN_REPORT_USER_FORM_REPORT;
			case 2:
				return ACCOUNT_OPEN_REPORT_USER_W8_REPORT;
			case 3:
				return ACCOUNT_OPEN_REPORT_USER_SELF_PROVE_REPORT;
			case 4:
				return ACCOUNT_OPEN_REPORT_USER_SELF_CERTIFICATION_ON_US_REPORT;
			case 5:
				return ACCOUNT_OPEN_REPORT_STANDING_AUTHORITY;
			case 6:
				return ACCOUNT_OPEN_REPORT_CLIENTS_SIGNATURE_CARD;
			case 7:
				return ACCOUNT_OPEN_REPORT_FUTURES_UNP_USER_SELF_PROVE_REPORT;
			case 8:
				return ACCOUNT_OPEN_REPORT_FUTURES_AGREEMENT;
			case 9:
				return ACCOUNT_OPEN_REPORT_PERSONAL_DATA_COLLECTION_STATEMENT;
			case 10:
				return ACCOUNT_OPEN_REPORT_MARGIN_TRADING_AGREEMENT;
			case 11:
				return ACCOUNT_OPEN_REPORT_SECURITIES_TRADING_AGREEMENT;
			case 12:
				return HONG_KONG_STOCK_FEE_SCHEDULE;
			case 13:
				return EXECUTE_TRADING_DISCLOSURE_DOCUMENTS_ON_OPTIMAL_TERMS;
			case 14:
				return ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT;
			default:
				return null;
		}
	}

	public static AccountPdfEnum find(int value) {
		for (AccountPdfEnum enumd : values()) {
			if (enumd.getValue() == (value)) {
				return enumd;
			}
		}
		return null;
	}

	private final int value;
	private final String name;

	private AccountPdfEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}


}
