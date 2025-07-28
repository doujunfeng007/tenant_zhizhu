package com.minigod.zero.platform.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/13 18:33
 * @description：推送模板
 */
public enum PushTemplate {
	LOGIN_PASSWORD_MODIFY				(2000, "登录密码修改"),
	LOGIN_PASSWORD_RESET				(2001, "登录密码重置"),
	TRADE_PASSWORD_MODIFY				(2002, "交易密码修改"),
	TRADE_PASSWORD_RESET				(2003, "交易密码重置"),
	ACCOUNT_OPEN_SUCCESS				(2004, "开户成功"),
	ACCOUNT_OPEN_FAIL					(2005, "开户申请资料审核未通过"),
	ACCOUNT_MOBILE_MODIFY				(2006, "开户手机号修改"),
	ACCOUNT_EMAIL_MODIFY				(2007,"开户邮箱修改"),
	MODIFY_INFO_SUCCESS					(2008, "修改资料成功"),
	MODIFY_INFO_FAIL					(2009, "线上修改资料失败"),
	DEPOSIT_RECEIVED					(2010, "入金受理"),
	DEPOSIT_SUCCESS						(2011, "入金成功"),
	DEPOSIT_DOCUMENT_FAIL				(2012, "入金凭证审核未通过"),
	DEPOSIT_FAIL						(2013, "入金失败"),
	EDDA_AUTH_SUCCESS					(2014, "eDDA授权成功"),
	EDDA_AUTH_FAIL						(2015, "eDDA授权失败"),
	QUICK_DEPOSIT_FAIL					(2016, "快捷入金失败"),
	WITHDRAWAL_RECEIVED					(2017, "出金受理"),
	WITHDRAWAL_FAIL						(2018, "出金失败"),
	WITHDRAWAL_COMPLETE					(2019, "出金完成"),
	BATCH_WITHDRAWAL_REQUEST_SUCCESS	(2020, "出金批处理申请成功通知"),
	BATCH_WITHDRAWAL_APPROVAL_SUCCESS	(2021, "出金批处理审批成功通知"),
	BATCH_WITHDRAWAL_REJECT				(2022, "出金批处理拒绝出金通知"),
	DAILY_STATEMENT						(2023, "日结单通知"),
	MONTHLY_STATEMENT					(2024, "月结单通知"),
	CURRENCY_EXCHANGE					(2025, "货币兑换"),
	DIVIDEND_NOTIFICATION				(2026, "派息消息"),
	RISK_ASSESSMENT						(2027, "风险测评"),
	PI_CERTIFICATION					(2028, "PI认证"),
	FUND_IN_ACTIVATE_ACCOUNT			(2029, "入金激活理财账户"),
	PURCHASE_SUCCESS_HUOLIDE			(2030, "购买成功(活利得)"),
	PURCHASE_SUCCESS_ZHAIQUANYI			(2031, "购买成功(债券易)"),
	SELL_SUCCESS_HUOLIDE				(2032, "卖出成功(活利得)"),
	SELL_SUCCESS_ZHAIQUANYI				(2033, "卖出成功(债券易)"),
	PASSWORD_NOTIFICATION				(2034, "密码通知"),
	HUOLIDE_ACCOUNT_OPEN				(2035, "活力得账户开通成功"),
	FUND_ACCOUNT_OPEN					(2036, "基金账户开通成功"),
	BONDEASE_ACCOUNT_OPEN				(2037, "债券易账户开通成功"),
	LOGGED_IN_ON_ANOTHER_DEVICE			(2038,"账号在别的设备登录"),
	W8BEN_EXPIRED_NOTIFICATION			(2039,"W-8BEN认证已到期，请重新确认"),
	SELF_CERTIFICATION_EXPIRED_NOTIFICATION			(2040,"自我证明信息已到期，请重新确认");

	// 枚举属性
	private final int code;
	private final String desc;

	// 枚举构造函数
	PushTemplate(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	// Getter 方法
	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	// 根据code查找枚举的静态方法
	public static PushTemplate fromCode(int code) {
		for (PushTemplate action : PushTemplate.values()) {
			if (action.getCode() == code) {
				return action;
			}
		}
		throw new IllegalArgumentException("No constant with code " + code + " found");
	}

}
