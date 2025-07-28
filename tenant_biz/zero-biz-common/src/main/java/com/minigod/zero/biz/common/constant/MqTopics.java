package com.minigod.zero.biz.common.constant;

public class MqTopics {
	/**
	 * 短信消息分发
	 */
	public static final String MOBILE_MSG = "mobile-msg";

	/**
	 * 邮件消息分发
	 */
	public static final String EMAIL_MSG = "email-msg";

	/**
	 * 推送消息分发
	 */
	public static final String NOTIFY_MSG = "notify-msg";

	/**
	 * 行情日志上报
	 */
	public static final String CLIENT_QUOT_LOG = "client-quot-log-message";
	//public static final String MARKET_REPORT = "market-report-message";

	/**
	 * LV2权限过期提醒
	 */
	public static final String LV2_AUTH_EXPIRED = "market-lv2-auth-expired";

	/**
	 * 游客刷新权限
	 */
	public static final String TOURIST_REFRESH_PERMISSIONS = "tourist-refresh-permissions";

	/**
	 * 点击报价计数
	 */
	public static final String CLICK_QUOT_MSG = "click-quot-msg";

	/**
	 * 实时行情
	 */
	public static final String HK_TRADE_STREAMING = "hk-trade-streaming-message";
	public static final String US_TRADE_STREAMING = "us-trade-streaming-message";
	public static final String ML_TRADE_STREAMING = "a-trade-streaming-message";

	/**
	 * 延时行情
	 */
	public static final String HK_TRADE_STEAMING_DELAY="hk-trade-streaming-delay-message";
	public static final String US_TRADE_STEAMING_DELAY="us-trade-streaming-delay-message";
	public static final String ML_TRADE_STEAMING_DELAY="a-trade-streaming-delay-message";

	/**
	 * 盘前盘后
	 */
	public static final String US_AFTER_TRADE_STREAMING = "us-after-trade-streaming-message";
	public static final String US_BEFORE_TRADE_STREAMING = "us-before-trade-streaming-message";

	/**
	 * 股价提醒
	 */
	public static final String PRICE_REMINDER = "price-reminder";

	/**
	 * 数据同步
	 */
	public static final String DATA_SYNC = "DATA_SYNC";

/**
	 * IPO认购生成订单
	 */
	public static final String IPO_APPLY = "IPO_APPLY";

	/**
	 * IPO垫资认购
	 */
	public static final String IPO_LOAN_APPLY = "IPO_LOAN_APPLY";

	/**
	 * BPM-TOPIC
	 */
	public static final String BPM_OPEN_ACCOUNT_TOPIC = "bpm-open-account-message";

	/**
	 * 信用额度调整-TOPIC
	 */
	public static final String MARGIN_CREDIT_APPLY_TOPIC = "margin-credit-apply-message";


	/**
	 * BPM将生效的公司关联人推送给CUST
	 */
	public static final String BPM_COMPANY_CONNECTER_INFO = "bpm-company-connecter-info";


	/**
	 * CUST将新注册的授权人推送给BPM
	 */
	public static final String BPM_COMPANY_AUTHOR_CUST_INFO = "bpm-company-author-cust-info";

	/**
	 * 出金申请推给BPM
	 */
	public static final String WITHDRAWAL_APPLY_TOPIC = "withdrawal-apply-message";

	/**
	 * 入金申请推给BPM
	 */
	public static final String DEPOSIT_APPLY_TOPIC = "deposit-apply-message";

	/**
	 * 用户操作日志记录
	 */
	public static final String CUST_OPERATION_LOG_MESSAGE = "cust-operation-log-message";

	/**
	 * 刷新行情品种
	 */
	public static final String MKT_QUOtES_TYPE_MESSAGE = "mkt-quotes-type-message";

	/**
	 * 回调邮件同步记录
	 */
	public static final String SENDCLOUD_EMAIL_SYNC_MESSAGE = "sendcloud-email-sync-message";

}
