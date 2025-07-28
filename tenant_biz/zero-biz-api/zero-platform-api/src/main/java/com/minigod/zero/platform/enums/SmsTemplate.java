package com.minigod.zero.platform.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/13 18:07
 * @description： 短信模板
 */
public enum SmsTemplate {
	LOGIN_VERIFICATION_CODE					(1000,	"登录验证码"),
	LOGIN_PASSWORD_CHANGE					(1001,"登录密码修改"),
	LOGIN_PASSWORD_RESET					(1002,"登录密码重置"),
	CHANGE_TRANSACTION_PASSWORD				(1003,"交易密码修改"),
	RESET_TRANSACTION_PASSWORD				(1004,"交易密码重置"),
	OPEN_ACCOUNT_SUCCESS					(1005,"开户成功"),
	OPEN_ACCOUNT_AUDIT_FAILED				(1006,"开户申请资料审核未通过"),
	CHANGE_ACCOUNT_OPENING_PHONE_NUMBER		(1007,"开户手机号修改"),
	CHANGE_ACCOUNT_OPENING_EMAIL			(1008,"开户邮箱修改"),
	DATA_MODIFICATION_SUCCESSFUL			(1009,"修改资料成功"),
	DATA_MODIFICATION_FAILED				(1010,"线上修改资料失败"),
	NEW_STOCK_REMINDER						(1011,"新股提醒"),
	TRANSACTION_REMINDER					(1012,"成交提醒"),
	NEW_STOCK_SUBSCRIPTION_SUCCESSFUL		(1013,"新股申购成功"),
	NEW_STOCK_NOT_ACCEPTEDA					(1014,"新股未中签"),
	NEW_STOCK_ACCEPTEDA						(1015,"新股中签"),
	RISE									(1016,"上涨提醒"),
	INCREASE_IN_PRICE						(1017,"涨幅提醒"),
	FALL									(1018,"下跌提醒"),
	DECLINE									(1019,"跌幅提醒"),
	HK_STOCK_MARKET_RELEASE					(1020,"港股LEVEL2行情发放"),
	HK_STOCK_MARKET_EXPIRATION				(1021,"港股LEVEL2行情到期"),
	US_STOCK_MARKET_RELEASE					(1022,"美股LEVEL1行情发放"),
	US_STOCK_MARKET_EXPIRATION				(1023,"美股LEVEL1行情到期"),
	DEPOSIT_ACCEPTANCE						(1024,"入金受理"),
	DEPOSIT_SUCCESSFUL						(1025,"入金成功"),
	DEPOSIT_AUDIT_FAILED					(1026,"入金凭证审核未通过"),
	DEPOSIT_FAILED							(1027,"入金失败"),
	EDDA_AUTHORIZATION_SUCCESSFUL			(1028,"EDDA授权成功"),
	EDDA_AUTHORIZATION_FAILED				(1029,"EDDA授权失败"),
	FPS_DEPOSIT_FAILED						(1030,"快捷入金失败"),
	WITHDRAWAL_ACCEPTANCE					(1031,"出金受理"),
	WITHDRAWAL_FAILED						(1032,"出金失败"),
	WITHDRAWAL_SUCCESSFUL					(1033,"出金完成"),
	BATCH_WITHDRAWAL_APPLICATION_SUCCESSFUL	(1034,"出金批处理申请成功通知"),
	BATCH_WITHDRAWAL_AUDIT_SUCCESSFUL		(1035,"出金批处理审批成功通知"),
	BATCH_WITHDRAWAL_AUDIT_FAILED			(1036,"出金批处理拒绝出金通知"),
	UNLOCK_TRANSACTION_PASSWORD				(1037,"交易密码解锁验证码"),
	ACCOUNT_OPENING_PROHIBITED				(1038,"禁止开户通知短信");

	private Integer code;
	private String desc;

	SmsTemplate(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}
}
