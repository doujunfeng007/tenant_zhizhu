package com.minigod.zero.customer.common;


public class CustomerClientConstants {
	/**
	 * 根据登录手机号获取客户信息
	 */
	public final static String LOAD_CUSTOMER_BY_USERNAME = "/customer/detail";
	/**
	 * 理财账号登录获取用户信息
	 */
	public final static String LOAD_CUSTOMER_BY_ACCOUNT = "/customer/account/detail";
	/**
	 * 子账号登录获取用户信息
	 */
	public final static String LOG_CUSTOMER_BY_SUB_ACCOUNT = "/customer/sub_account/detail";

	/**
	 * 根据理财账号获取客户信息
	 */
	public final static String LOAD_CUSTOMER_BY_ACCOUNT_ID = "/customer/detailByAccountId";
	/**
	 * 根据客户id查询用户信息
	 */
	public final static String LOAD_CUSTOMER_BY_CUST_ID = "/customer/detailByCustId";

	/**
	 * 静默注册接口
	 */
	public final static String CUSTOMER_DEFAULT_REGISTER = "/customer/register";
	/**
	 * 客户开户
	 */
	public final static String CUSTOMER_OPEN_ACCOUNT = "/customer/open_account";

	public final static String CUSTOMER_OPEN_ACCOUNT_NEW = "/customer/open_account_new";
	/**
	 * 修改客户pi等级
	 */
	public final static String UPDATE_PI_LEVEL = "/customer/update_pi_level";
	/**
	 * 入金接口
	 */
	public final static String GOLD_DEPOSIT = "/customer/fund/gold_deposit";
	/**
	 * 冻结金额
	 */
	public final static String FREEZE_AMOUNT = "/customer/fund/freeze_amount";
	/**
	 * 解冻金额
	 */
	public final static String THAWING_AMOUNT = "/customer/fund/thawing_amount";
	/**
	 * 出金
	 */
	public final static String SCRATCH_BUTTON = "/customer/fund/scratch_button";

	/**
	 * 归档用户资产
	 */
	public final static String ARCHIVE_USER_ASSETS = "/customer/archive_user_assets";
	/**
	 * 分页查找 日月结单列表
	 */
	public final static String STATEMENT_LIST = "/statement/reports";


	/**
	 * 机构开户
	 */
	public final static String ORGANIZATION_OPEN_ACCOUNT = "/customer/organization/open_account";

	/**
	 * 客户设备信息
	 */
	public final static String CUSTOMER_DEVICE_LIST = "/customer/device_list";

	public final static String ALL_DEVICE_USER_ID_LIST = "/customer/all_device_user_id_list";

	/**
	 * 客户邮箱
	 */
	public final static String CUSTOMER_EMAIL_LIST = "/customer/email_list";

	/**
	 * 客户设备信息详情
	 */
	public final static String CUSTOMER_DEVICE_LIST_DETAIL = "/customer/device/detail";
	/**
	 * 查询账户金额
	 */
	public final static String ACCOUNT_AMOUNT = "/customer/account_amount";

	/**
	 * 查询理财账
	 */
	public final static String SELECT_ACCOUNT_ID ="/customer/select_account_id";
	/**
	 * 导出日结单
	 */
	public final static String EXPORT_STATEMENT_DAILY = "/customer/statement/exportStatementDaily";
	/**
	 * 导出月结单
	 */
	public final static String EXPORT_STATEMENT_MONTH = "/customer/statement/exportStatementMonth";
	/**
	 * 更新用户衍生品调查
	 */
	public final static String UPDATE_CUSTOMER_DERIVATIVE = "/customer/derivative";
	/**
	 * 修改开户信息
	 */
	public final static String UPDATE_CUSTOMER_OPEN_ACCOUNT_INFO = "/customer/open_account_info";


	public final static String SELECT_PACKAGE_BY_NUMBER = "/customer/selectPackageByNumber";

	/**
	 * 查询用户信息
	 */
	public final static String LOAD_CUSTOMER_BY_TIME = "/customer/selCustomerByTime";

	public final static String UPDATE_CUSTOMER_TENANT_INFO = "/customer/updateTenantCustId";

	/**
	 * 根据理财账号获取用户所有账号信息
	 */
	public final static String QUERY_ALL_ACCOUNT_INFO = "/customer/allAccountByAccountId";

	/**
	 * 根据用户号获取用户
	 */
	public final static String QUERY_CUST_OPEN_ACCOUNT_INFO = "/customer/custOpenAccountInfo";

	public final static String QUERY_CUST_OPEN_ACCOUNT_INFO_BY_PHONE = "/customer/custOpenAccountInfoByPhone";
}
