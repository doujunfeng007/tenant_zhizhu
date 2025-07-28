package com.minigod.zero.platform.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/14 16:35
 * @description：邮件模版
 */
public enum EmailTemplate {
	WITHDRAWAL_REQUEST_NOTIFICATION(3000, "出金申请通知"),
	DEPOSIT_REQUEST_NOTIFICATION(3001, "入金申请通知"),
	EDDA_AUTHORIZATION_REQUEST_APPROVAL_NOTIFICATION(3002, "eDDA授权申请审核通知"),
	ACCOUNT_OPENING_SUBMISSION_APPROVAL_REMINDER(3003, "开户提交审批提醒"),
	PI_CERTIFICATION_APPROVAL_REMINDER(3004, "PI认证审批提醒"),
	BANK_CARD_BINDING_NOTIFICATION(3005, "银行卡绑定通知"),
	OPEN_WEALTH_MANAGEMENT_ACCOUNT(3006, "开通理财账户"),
	RESET_TRANSACTION_PASSWORD(3007,"重置交易密码验证码"),
	LOGIN_SUCCESS(3008,"登录成功"),
	DAILY_STATEMENT(3009,"日结单通知邮件"),
	MONTHLY_STATEMENT(3010,"月结单通知邮件"),
	INTEGRATED_MANAGEMENT_BACKEND(3011,"綜合管理後台登录验证码"),
	PI_PASS_APPROVAL_REMINDER(3012,"PI认证审批通过"),
	PI_NO_APPROVAL_REMINDER(3013,"PI认证审批不通过"),
	OPEN_SUB_ACCOUNT(3014,"开通子账号"),
	PI_CANCEL_APPROVAL_REMINDER(3015,"PI取消认证审批提醒");
	private final int code;
	private final String desc;

	// 枚举的构造函数默认为private
	EmailTemplate(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	// Getter方法
	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	// 可选：添加一个根据code获取枚举的静态方法
	public static EmailTemplate fromCode(int code) {
		for (EmailTemplate operation : EmailTemplate.values()) {
			if (operation.getCode() == code) {
				return operation;
			}
		}
		throw new IllegalArgumentException("No constant with code " + code + " found");
	}
}
