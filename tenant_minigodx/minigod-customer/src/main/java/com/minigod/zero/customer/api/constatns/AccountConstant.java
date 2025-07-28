package com.minigod.zero.customer.api.constatns;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/17 15:37
 * @description：账户相关常量池
 */
public class AccountConstant {
	public static String DEFAULT_OK = "Y";
	/**
	 * 冻结金额锁
	 */
	public static String FREEZE_AMOUNT_LOCK = "fund:fund:freeze_amount";
	/**
	 * 解冻金额锁
	 */
	public static String THAWING_AMOUNT_LOCK = "lock:fund:lock:thawing_amount";
	/**
	 * 入金金额锁
	 */
	public static String GOLD_DEPOSIT_LOCK = "lock:fund:gold_deposit";
	/**
	 * redis 缓存交易密码校验
	 */
	public static String CHECK_TRADE_PWD_NUM = "check_trade_pwd:";

	/**
	 * pc 理财账号登录
	 */
	public static String USER_NAME_TYPE = "account";


}
