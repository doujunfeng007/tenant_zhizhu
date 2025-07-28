package com.minigod.zero.biz.common.constant;

/**
 * 开户相关类的常量
 *
 * @author eric
 * @since 2024-11-12 14:44:08
 */
public class AccountMessageConstant {
	/**
	 * 开户失败，未开通理财账户!
	 */
	public static final String ACCOUNT_OPEN_FAIL_NOT_OPEN_FINANCING = "account_open_fail_not_open_financing_notice";

	/**
	 * 用户:%s基金开户失败,失败原因:%s
	 */
	public static final String ACCOUNT_OPEN_FUND_OPEN_FAIL_NOTICE = "account_open_fund_open_fail_notice";

	/**
	 * 用户:%s基金开户失败，没有子账号!
	 */
	public static final String ACCOUNT_OPEN_FUND_OPEN_FAIL_NO_SUB_ACCOUNT_NOTICE = "account_open_fund_open_fail_no_sub_account_notice";

	/**
	 * 开户失败,失败原因:%s !
	 */
	public static final String ACCOUNT_OPEN_FAIL_NOTICE = "account_open_fail_notice";

	/**
	 * 请输入正确手机号!
	 */
	public static final String ACCOUNT_OPEN_FAIL_PHONE_ERROR_NOTICE = "account_open_fail_phone_error_notice";

	/**
	 * 默认交易账号切换成功!
	 */
	public static final String ACCOUNT_OPEN_DEFAULT_TRADE_ACCOUNT_SUCCESS_NOTICE = "account_open_default_trade_account_success_notice";

	/**
	 * 更新活利得风险等级失败,用户基础数据缺失!
	 */
	public static final String ACCOUNT_OPEN_UPDATE_HLD_RISK_LEVEL_FAIL_NOTICE = "account_open_update_hld_risk_level_fail_notice";

	/**
	 * 更新活利得风险等级失败,未开通债券易账户!
	 */
	public static final String ACCOUNT_OPEN_UPDATE_HLD_RISK_LEVEL_FAIL_NOT_OPEN_BOND_NOTICE = "account_open_update_hld_risk_level_fail_not_open_bond_notice";

	/**
	 * 未查询到客户信息!
	 */
	public static final String ACCOUNT_OPEN_NOT_FIND_CUSTOMER_INFO_NOTICE = "account_open_not_find_customer_info_notice";

	/**
	 * 修改账户资料失败,CustId不能为空!
	 */
	public static final String ACCOUNT_OPEN_UPDATE_CUSTOMER_INFO_FAIL_CUST_ID_NULL_NOTICE = "account_open_update_customer_info_fail_cust_id_null_notice";

	/**
	 * 修改账户资料失败，开户资料不存在!
	 */
	public static final String ACCOUNT_OPEN_UPDATE_CUSTOMER_INFO_FAIL_NOT_EXIST_NOTICE = "account_open_update_customer_info_fail_not_exist_notice";

	/**
	 * 查询账户资料失败，custId不能为空！
	 */
	public static final String ACCOUNT_OPEN_SELECT_CUSTOMER_INFO_FAIL_CUST_ID_NULL_NOTICE = "account_open_select_customer_info_fail_cust_id_null_notice";

	/**
	 * 查询账户资料失败，用户不存在！
	 */
	public static final String ACCOUNT_OPEN_SELECT_CUSTOMER_INFO_FAIL_NOT_EXIST_NOTICE = "account_open_select_customer_info_fail_not_exist_notice";

	/**
	 * 理财账号不存在！
	 */
	public static final String ACCOUNT_OPEN_FINANCING_ACCOUNT_NOT_EXIST_NOTICE = "account_open_financing_account_not_exist_notice";

	/**
	 * 获取邮箱失败，参数用户ID为空!
	 */
	public static final String ACCOUNT_OPEN_GET_EMAIL_FAIL_PARAM_USER_ID_NULL_NOTICE = "account_open_get_email_fail_param_user_id_null_notice";

	/**
	 * 交易token已过期,重新认证交易密码!
	 */
	public static final String ACCOUNT_OPEN_TRADE_TOKEN_EXPIRED_NOTICE = "account_open_trade_token_expired_notice";

	/**
	 * 账号已在其他设备登录！
	 */
	public static final String ACCOUNT_OPEN_ACCOUNT_LOGIN_OTHER_DEVICE_NOTICE = "account_open_account_login_other_device_notice";

	/**
	 * 用户生物特征Token无效
	 */
	public static final String ACCOUNT_OPEN_BIOMETRIC_TOKEN_INVALID_NOTICE = "account_open_biometric_token_invalid_notice";

}
