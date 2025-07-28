
package com.minigod.zero.biz.common.cache;

/**
 * 缓存名
 *
 * @author Chill
 */
public interface CacheNames {

	/**
	 * 返回拼接后的key
	 *
	 * @param cacheKey      缓存key
	 * @param cacheKeyValue 缓存key值
	 * @return tenantKey
	 */
	static String cacheKey(String cacheKey, String cacheKeyValue) {
		return cacheKey.concat(cacheKeyValue);
	}

	/**
	 * 返回租户格式的key
	 *
	 * @param tenantId      租户编号
	 * @param cacheKey      缓存key
	 * @param cacheKeyValue 缓存key值
	 * @return tenantKey
	 */
	static String tenantKey(String tenantId, String cacheKey, String cacheKeyValue) {
		return tenantId.concat(":").concat(cacheKey).concat(cacheKeyValue);
	}

	/**
	 * 验证码key
	 */
	String CAPTCHA_KEY = "zero:auth::zero:captcha:";

	/**
	 * 登录失败key
	 */
	String USER_FAIL_KEY = "zero:user::zero:fail:";

	/**
	 * 客户信息Key
	 */
	String CUST_INFO_KEY = "zero:cust:info::";

	/**
	 * 修改开户邮箱key
	 */
	String UPDATE_OPEN_EMAIL = "update:open:email:";

	/**
	 * 重置交易密码证件验证key
	 */
	String TRADE_RESET_TRADE_PWD = "reset:trade:pwd:";

	/**
	 * 证券孖展比例
	 */
	String TRADE_STOCK_MARGIN_RATIO = "trade:stock:margin:ratio:";

	/**
	 * 证券孖展比例过期时间 6 小时
	 */
	Long TRADE_STOCK_MARGIN_RATIO_EXPIRE_TIME = 60L * 60L * 6L;

	/**
	 * 今日委托订单
	 */
	String TRADE_JOURNAL_ORDERS = "trade:journal:orders:";

	/**
	 * 历史委托订单
	 */
	String TRADE_HISTORY_ORDERS = "trade:history:orders:";

	/**
	 * 历史委托订单 过期时间 5 小时
	 */
	Long TRADE_HISTORY_ORDERS_EXPIRE_TIME = 60L * 60L * 5L;

	/**
	 * 市场资产信息
	 */
	String TRADE_MARKET_ASSET = "trade:market:asset:";

	/**
	 * 市场资产信息 过期时间 5 小时
	 */
	Long TRADE_MARKET_ASSET_EXPIRE_TIME = 60L * 60L * 5L;

	/**
	 * 基金资产信息
	 */
	String TRADE_FUND_ASSET = "trade:fund:asset:";

	/**
	 * 基金资产信息 过期时间 5 小时
	 */
	Long TRADE_FUND_ASSET_EXPIRE_TIME = 60L * 60L * 5L;

	/**
	 * 当日交易信息
	 */
	String TRADE_TODAY_TRADE = "trade:today:trade:";

	/**
	 * 当日交易信息 过期时间 5 小时
	 */
	Long TRADE_TODAY_TRADE_EXPIRE_TIME = 60L * 60L * 5L;

	/**
	 * 总资产信息 过期时间 10 秒
	 */
	Long TRADE_TOTAL_ASSET_EXPIRE_TIME = 10L;

	/**
	 * 总资产信息
	 */
	String TRADE_TOTAL_ASSET = "trade:total:asset:";

	/**
	 * 交易持仓详情
	 */
	String TRADE_POSITION_DETAILS = "trade:position:details:";

	/**
	 * 交易持仓详情 过期时间 1 小时
	 */
	Long TRADE_POSITION_DETAILS_EXPIRE_TIME = 60L * 60L;

	/**
	 * 交易持仓卖空
	 */
	String TRADE_POSITION_SELL = "trade:position:sell:";

	/**
	 * 交易持仓卖空 过期时间 5 天
	 */
	Long TRADE_POSITION_SELL_EXPIRE_TIME = 60L * 60L * 24 * 5;

	/**
	 * 交易确认，一次确认下单一次，防止重复
	 */
	String TRADE_PLACE_ORDER_ACK = "trade:place:order:ack:";

	/**
	 * 交易确认，一次确认下单一次，防止重复 过期时间 1 天
	 */
	Long TRADE_PLACE_ORDER_ACK_EXPIRE_TIME = 60L * 60L * 24;

	/**
	 * 交易持仓统计
	 */
	String TRADE_POSITION_ASSET_COUNT = "trade:position:asset:count:";

	/**
	 * 交易持仓统计 过期时间 1 小时
	 */
	Long TRADE_POSITION_ASSET_COUNT_EXPIRE_TIME = 60L * 60L;

	/**
	 * 交易佣金
	 */
	String TRADE_COMMISSION = "trade:commission::";

	/**
	 * 交易佣金 过期时间 24 小时
	 */
	Long TRADE_COMMISSION_EXPIRE_TIME = 60L * 60L * 24L;

	/**
	 * 交易费用
	 */
	String TRADE_FEE = "trade:fee::";

	/**
	 * 交易费用 过期时间 24 小时
	 */
	Long TRADE_FEE_EXPIRE_TIME = 60L * 60L * 24L;

	/**
	 * 交易费用列表
	 */
	String TRADE_FEE_LIST = "trade:fee:list:";

	/**
	 * 交易费用列表 过期时间 24 小时
	 */
	Long TRADE_FEE_LIST_EXPIRE_TIME = 60L * 60L * 24L;

	/**
	 * 清算标识
	 */
	String TRADE_SWITCH = "srade:switch::";

	/**
	 * 客户账户资料信息
	 */
	String CUST_ACCOUNT_INFO = "cust:account:info::";

	/**
	 * 汇率
	 */
	String TRADE_EXCHANGE_RATE = "trade:exchange:rate:";

	/**
	 * 汇率 过期时间 3 小时
	 */
	Long TRADE_EXCHANGE_RATE_EXPIRE_TIME = 60L * 60L * 3L;

	/**
	 * 重置开户邮箱校验成功缓存有效期1天
	 */
	Long UPDATE_OPEN_EMAIL_EXPIRE_TIME = 60L*60L*24L;

	/**
	 * 重置交易密码校验证件号成功缓存有效期1天
	 */
	Long RESET_TRADE_PWD_EXPIRE_TIME = 60L*60L*24L;

	/**
	 * 交易解锁2fa手机缓存
	 */
	String TOW_FA_TRADE_UNLOCK_PHONE = "tow:fa:trade:unlock:phone:";

	/**
	 * 其他2fa手机缓存
	 */
	String TOW_FA_OTHER_PHONE = "tow:fa:other::phone:";

	/**
	 * 其他2fa邮件缓存
	 */
	String TOW_FA_OTHER_EMAIL = "tow:fa:other::email:";

	/**
	 * 条件单
	 */
	String STRATEGY_ORDER = "strategy:order:";

	/**
	 * 交易柜台
	 */
	String TRADE_TOKEN = "trade-counter-token";

	String TRADE_KEY ="trade-key-tenant";

	/**
	 * 条件单锁
	 */
	String TRADE_TRIGGER_STRATEGY_ORDER = "trade:trigger:strategy:order:";

	/**
	 * 条件单锁 过期时间 24 小时
	 */
	Long TRADE_TRIGGER_STRATEGY_ORDER_EXPIRE_TIME = 60L * 60L * 24L;

	/**
	 * 工银汇率信息
	 */
	String ICBCA_MONEY_RATE = "icbca:money:rate::";

	/**
	 * 工银汇率信息 过期时间 10 分钟
	 */
	Long ICBCA_MONEY_RATE_EXPIRE_TIME = 60L * 10L;

	/**
	 * 非交易日推送的行情不处理
	 */
	String TRADE_QUO_SKIP = "trade:quo:skip:";

	/**
	 * 非交易日推送的行情不处理 过期时间 10 分钟
	 */
	Long TRADE_QUO_SKIP_EXPIRE_TIME = 60L * 10L;

	/**
	 * 交易推送锁
	 */
	String TRADE_PUSH = "trade:push:";

	/**
	 * 交易推送锁 过期时间 24 小时
	 */
	Long TRADE_PUSH_EXPIRE_TIME = 60L * 60L * 24L;

	/**
	 * 手机号+验证码登陆绑定开户手机号
	 */
	String CUST_PHONE_BIND_OPEN = "cust:phone:bind:open:";

	/**
	 * 手机号+验证码登陆绑定开户手机号 过期时间 1 小时
	 */
	Long CUST_PHONE_BIND_OPEN_EXPIRE_TIME = 60L * 60L;

	/**
	 * 交易账号/用户号/邮箱登陆绑定注册手机号
	 */
	String CUST_PHONE_BIND_LOGIN = "cust:phone:bind:login:";

	/**
	 * 交易账号/用户号/邮箱登陆绑定注册手机号 过期时间 1 小时
	 */
	Long CUST_PHONE_BIND_LOGIN_EXPIRE_TIME = 60L * 60L;

	/**
	 * 交易登录token
	 */
	String TRADE_LOGIN_TOKEN = "trade:login:token:";

}
