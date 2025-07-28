package com.minigod.zero.platform.constants;

public class Constants {

	/**
	 * 全站用户的ID
	 */
	public static long USERID_ALL_USER = 0L;

	public static final String ENGINE_TYPE_FREEMARKER = "freemarker";

	public static final String ENGINE_TYPE_VELOCITY = "velocity";

	public static final String SMS_CHANNEL_CACHE_KEY = "SMS_CHANNEL";

	public static class SysMsgContants {
		public static final String SYS_MSG_GROUP_PERNSON = "P";//个人类型
		public static final String SYS_MSG_GROUP_ALL = "A";//全站类型
		public static final Long SYS_MSG_TARGETID_ALL = 0L;//目标用户为所有用户
		public static final boolean SYS_MSG_STATUS_TRUE = true;
		public static final boolean SYS_MSG_STATUS_FALSE = false;
		public static final String SYS_MSG_VERSION = "sys_msg_version";//更新版本号
		public static final Integer SYS_MSG_PUSH_STRONG = 0;//强推送类型
		public static final Integer SYS_MSG_PUSH_WEAK = 1;//弱推送类型
		public static final Integer SYS_MSG_SENDWAY_NOW = 0;//即时推送
		public static final Integer SYS_MSG_SENDWAY_GIVINGTIME = 1;//定时

		public static final String SYS_MSG_SENDSTATUS_NOTSNED = "0";//未发送
		public static final String SYS_MSG_SENDSTATUS_SNED = "1";//已发送
		public static final String SYS_MSG_SENDSTATUS_FAIL = "2";//发送失败

		public static final Integer SYS_MSG_CLIENT_TYPE_ALL = 0;//全部终端
		public static final Integer SYS_MSG_CLIENT_TYPE_AOS = 1;//Android
		public static final Integer SYS_MSG_CLIENT_TYPE_IOS = 2;//IOS

		public static final Integer PROP_FLAG_YES = 1;//是
		public static final Integer PROP_FLAG_NO = 0;//否
	}

	/**
	 * 基金开户接口
	 */
	public static String FUND_OPEN_ACCOUNT_INTERFACE_URL = "/v1/fund/account";
	/**
	 * 基金子账户余额查询接口
	 */
	public static String FUND_CASH_BALANCE ="/v1/fund/subaccounts/#sub/cashbalance";

	public static String REPLACE_SUB_ACCOUNT="#sub";
	/**
	 * 查询汇率接口
	 */
	public static String EXCHANGE_RATE = "/v1/fund/exchrate";
	/**
	 * 查理财账号总市值
	 */
	public static String MARKET_VALUE = "/v1/user/total/holding?extacctid=";
	/**
	 * 查询各币种利息
	 */
	public static String ACCUMULATED_INTEREST="/v1/account/income";
	/**
	 * 查询子账户金额
	 */
	public static String ACCOUNT_CAPITAL = "/v1/account/capital";
	/**
	 * 资产信息
	 */
	public static String ACCOUNT_ASSET_ALL = "/v1/account/asset/all";
	/**
	 * 持仓列表
	 */
	public static String CUSTOMER_POSITION_LIST = "/v1/customer/holding?extacctid=%s&start=%s&count=%s&order=%s&orderType=%s";
	/**
	 * 订单信息
	 */
	public static String  CUSTOMER_ORDER = "/v1/customer/order";
	/**
	 * 历史订单
	 */
	public static String  CUSTOMER_HISTORY_ORDER = "/v1/customer/trade/orders?extacctid=%s&start=%s&count=%s&busiType=%s&";
	/**
	 * 派息记录
	 */
	public static String DIVIDEND_LIST ="/v1/dailyinterest/pagebycondition";
	/**
	 * 查询订单交易确认书
	 */
	public static String CONFIRMLETTER = "/v1/order/confirmletter?orderId=%s";
	/**
	 * 下载确认交易书
	 */
	public static String CONFIRMLETTER_DOWNLOAD = "/v1/oss/confirmLetter/download/%s";

	/**
	 * 订单信息
	 */
	public static String  USER_ORDER = "/v1/user/order";
}
