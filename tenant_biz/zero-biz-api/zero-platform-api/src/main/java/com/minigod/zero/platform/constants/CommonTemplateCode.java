package com.minigod.zero.platform.constants;

import java.io.FileInputStream;

/**
 * 消息中心模板code
 * @author Zhe.Xiao
 */
public class CommonTemplateCode {

	/**
	 * 邮件模板code
	 */
	public static class Email {
		//开户邮箱修改
		public static final int OPEN_EMAIL_MODIFY = 2018;
		//邮箱方式重置密码
		public static final int RESET_PASSWORD_BY_EMAIL = 2373;
		//交易解锁提醒
		public static final int TRANSACTION_UNLOCKING_REMINDER = 3005;
		//绑定邮箱验证码
		public static final int VERIFICATION_CODE_OF_EMAIL_BINDING = 3006;
		//智珠持股找回密码
		public static final int ESOP_RETRIEVE_PASSWORD = 3008;
		//智珠持股重置交易密码
		public static final int ESOP_RESET_TRADING_PASSWORD = 3009;
		//智珠持股管理员初始密码
		public static final int ESOP_ADMIN_INITIAL_PASSWORD = 3010;
		//成交推送
		public static final int TRADE_COMPLETE = 3043;
		//下单
		public static final int NEW_ORDER = 3051;
		//下单(条件单)
		public static final int NEW_ORDER_CONDITIONAL = 3052;
		// 新股中签
		public static final int IPO_ZQ_SUCCESS = 1087;
		// 新股未中签
		public static final int IPO_WZQ_SUCCESS = 1088;
		//入金受理
		public static final int DEPOSIT_INSTRUCTION_RECEIVED = 3077;
		//出金受理
		public static final int WITHDRAWAL_INSTRUCTION_RECEIVED = 3073;

		//出金银行卡绑定ops审核通知
		public static final int WITHDRAWAL_BANK_CARD_REVIEW = 21001;
		//edda银行卡授权绑定ops审核通知
		public static final int EDDA_BANK_CARD_REVIEW = 21002;
		//入金申请 ops审核通知
		public static final int DEPOSIT_REVIEW = 21003;
		//出金申请 ops审核通知
		public static final int WITHDRAW_REVIEW = 21004;



	}

	/**
	 * 短信模板code
	 */
	public static class Sms {		//交易密码修改
		public static final int TRADE_PASSWORD_MODIFY = 3047;
		//交易密码重置
		public static final int TRADE_PASSWORD_RESET = 3048;
		//非受信设备登录提醒
		public static final int UNTRUSTED_DEVICE_LOGIN = 3049;
		//双重认证确认
		public static final int ADD_TRUSTED_DEVICE = 3050;
		//修改登录密码
		public static final int UPDATE_LOGIN_PWD = 3060;
		//重置登录密码
		public static final int RESET_LOGIN_PWD = 3063;
		//修改邮箱
		public static final int UPDATE_EMAIL = 3064;
	}

	/**
	 * 推送模板code
	 */
	public static class Push {

		//成交推送
		public static final int TRADE_PUSH = 1054;

		//开户成功
		public static final int OPEN_ACCOUNT_PUSH = 3079;
		//基金开户成功
		public static final int OPEN_FUND_ACCOUNT_PUSH = 3080;
		//活利得开户成功
		public static final int OPEN_HLD_ACCOUNT_PUSH = 3081;
		//债券易开户成功
		public static final int OPEN_BOND_ACCOUNT_PUSH = 3082;
		//交易密码修改
		public static final int TRADE_PASSWORD_MODIFY = 3044;
		//交易密码重置
		public static final int TRADE_PASSWORD_RESET = 3045;
		//换汇提醒
		public static final int CURRENCY_EXCHANGE = 3046;
		//修改登录密码
		public static final int UPDATE_LOGIN_PWD = 3061;
		//重置登录密码
		public static final int RESET_LOGIN_PWD = 3062;
		//修改邮箱
		public static final int UPDATE_EMAIL = 3065;
		//入金受理
		public static final int DEPOSIT_INSTRUCTION_RECEIVED = 1021;
		//出金受理
		public static final int WITHDRAWAL_INSTRUCTION_RECEIVED = 1016;

		/*------------------------*/

		//开户成功
		public static final int OPEN_ACCOUNT_SUCCEED_PUSH = 13005;

		//开户申请资料审核未通过
		public static final int OPEN_ACCOUNT_DATA_REVIEW_NOT_PUSH = 13006;

		//开户手机号修改
		//public static final int UPDATE_PHONE_PUSH = 13007;

		//开户邮箱修改
		//public static final int OPEN_ACCOUNT_PUSH = 13008;

		//修改资料成功 资料变更申请
		//public static final int OPEN_ACCOUNT_DATA_UPDATE_PUSH = 13009;

		//线上修改资料失败
		//public static final int OPEN_ACCOUNT_DATA_UPDATE_FAIL_PUSH = 13010;

		//入金受理
		public static final int DEPOSIT_ACCEPTANCE_PUSH = 13024;

		//入金成功
		public static final int DEPOSIT_SUCCEED_PUSH = 13025;

		//入金凭证审核未通过
		public static final int DEPOSIT_CERTIFICATE_FAIL_PUSH = 13026;

		//入金失败
		public static final int DEPOSIT_FAIL_PUSH = 13027;

		//eDDA授权成功
		public static final int EDDA_AUTH_SUCCEED_PUSH = 13028;

		//eDDA授权失败
		public static final int EDDA_AUTH_FAIL_PUSH = 13029;

		//快捷入金失败
		public static final int EDDA_DEPOSIT_FAIL_PUSH = 13030;

		//出金受理
		public static final int WITHDRAW_ACCEPTANCE_PUSH = 13031;

		//出金失败
		public static final int WITHDRAW_FAIL_PUSH = 13032;

		//出金完成
		public static final int WITHDRAW_SUCCEED_PUSH = 13033;

		//出金批处理申请成功通知
		public static final int WITHDRAW_BATCH_APPLY_SUCCEED_PUSH = 13034;

		//出金批处理审批成功通知
		public static final int WITHDRAW_BATCH_APPROVER_SUCCEED_PUSH = 13035;

		//出金批处理拒绝出金通知
		//public static final int WITHDRAW_BATCH_APPROVER_FAIL_PUSH = 13036;

		//转出受理
		//public static final int OPEN_ACCOUNT_PUSH = 13037;

		//转出成功
		//public static final int OPEN_ACCOUNT_PUSH = 13038;

		//转入成功
		//public static final int OPEN_ACCOUNT_PUSH = 13039;

		//日结单通知
		public static final int STATEMENT_DAILY_PUSH = 13040;

		//月结单通知
		public static final int STATEMENT_MONTH_PUSH = 13041;

		//货币兑换
		public static final int CURRENCY_EXCHANGE_SUCCEED_PUSH = 13042;

		//派息消息
		public static final int DIVIDEND_PUSH = 13043;


		//风险测评
		public static final int RISK_SUCCEED_PUSH = 13045;

		//PI认证
		public static final int PI_SUCCEED_PUSH = 13046;

		//入金激活理财账户
		public static final int DEPOSIT_ACTIVATE_ACCOUNT_PUSH = 13046;
		//购买成功(活利得)
		public static final int HLD_ORDER_BUY_SUCCESS_PUSH = 13044;
		//购买成功(债券易)
		public static final int BOND_ORDER_BUY_SUCCESS_PUSH = 13048;
		//卖出成功(活利得)
		public static final int HLD_ORDER_REDEEM_SUCCESS_PUSH = 13049;
		//卖出成功(债券易)
		public static final int BOND_ORDER_REDEEM_SUCCESS_PUSH = 13050;
		//密码状态通知
		public static final int PWD_UPD_STATUS_SUCCESS_PUSH = 13051;


	}
	public static final String AREA_CODE = "86";

}
