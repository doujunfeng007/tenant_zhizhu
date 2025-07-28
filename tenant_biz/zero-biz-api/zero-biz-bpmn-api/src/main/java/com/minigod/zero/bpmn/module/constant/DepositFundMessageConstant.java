package com.minigod.zero.bpmn.module.constant;

/**
 * 入金消息常量
 *
 * @author eric
 * @since 2024-09-20 18:25:08
 */
public class DepositFundMessageConstant {
	/**
	 * 获取入金银行配置信息参数错误,银行类型不能为空!
	 */
	public static final String DEPOSIT_FUND_QUERY_CONFIG_BANK_TYPE_NULL_NOTICE = "deposit_fund_query_config_bank_type_null_notice";
	/**
	 * 获取入金银行配置信息参数错误,币种不能为空!
	 */
	public static final String DEPOSIT_FUND_QUERY_CONFIG_CURRENCY_NULL_NOTICE = "deposit_fund_query_config_currency_null_notice";
	/**
	 * 获取入金银行配置信息参数错误,入金类型不能为空!
	 */
	public static final String DEPOSIT_FUND_QUERY_CONFIG_TYPE_NULL_NOTICE = "deposit_fund_query_config_type_null_notice";
	/**
	 * 获取银行列表失败,原因:%s
	 */
	public static final String DEPOSIT_FUND_QUERY_BACK_LIST_FAILED_NOTICE = "deposit_fund_query_back_list_failed_notice";
	/**
	 * FPS入金获取收款账户失败!
	 */
	public static final String DEPOSIT_FUND_FPS_QUERY_RECEIVE_ACCOUNT_FAILED_NOTICE = "deposit_fund_fps_query_receive_account_failed_notice";
	/**
	 * 网银入金获取收款账户失败!
	 */
	public static final String DEPOSIT_FUND_ONLINE_QUERY_RECEIVE_ACCOUNT_FAILED_NOTICE = "deposit_fund_online_query_receive_account_failed_notice";
	/**
	 * 获取入金银行配置信息失败!
	 */
	public static final String DEPOSIT_FUND_QUERY_CONFIG_FAILED_NOTICE = "deposit_fund_query_config_failed_notice";
	/**
	 * 获取入金方式失败!
	 */
	public static final String DEPOSIT_FUND_QUERY_METHOD_FAILED_NOTICE = "deposit_fund_query_method_failed_notice";
	/**
	 * 未配置入金方式!
	 */
	public static final String DEPOSIT_FUND_NO_METHOD_CONFIG_NOTICE = "deposit_fund_no_method_config_notice";
	/**
	 * 手工入金,查询收款账户信息异常!
	 */
	public static final String DEPOSIT_FUND_MANUAL_QUERY_RECEIVE_ACCOUNT_ERROR_NOTICE = "deposit_fund_manual_query_receive_account_error_notice";
	/**
	 * 手工入金,收款账户信息异常!
	 */
	public static final String DEPOSIT_FUND_MANUAL_RECEIVE_ACCOUNT_ERROR_NOTICE = "deposit_fund_manual_receive_account_error_notice";
	/**
	 * 手工入金,查询入金银行信息异常!
	 */
	public static final String DEPOSIT_FUND_MANUAL_QUERY_BANKINFO_ERROR_NOTICE = "deposit_fund_manual_query_bankinfo_error_notice";
	/**
	 * 手工入金,不支持该银行入金!
	 */
	public static final String DEPOSIT_FUND_MANUAL_BANK_NOT_SUPPORT_NOTICE = "deposit_fund_manual_bank_not_support_notice";
	/**
	 * 提交入金申请,账号信息异常!
	 */
	public static final String DEPOSIT_FUND_SUBMIT_ACCOUNT_ERROR_NOTICE = "deposit_fund_submit_account_error_notice";
	/**
	 * 提交入金申请,查询收款账户信息异常!
	 */
	public static final String DEPOSIT_FUND_SUBMIT_QUERY_RECEIVE_ACCOUNT_ERROR_NOTICE = "deposit_fund_submit_query_receive_account_error_notice";
	/**
	 * 提交入金申请,收款账户信息异常!
	 */
	public static final String DEPOSIT_FUND_SUBMIT_RECEIVE_ACCOUNT_ERROR_NOTICE = "deposit_fund_submit_receive_account_error_notice";
	/**
	 * 提交入金申请,查询入金银行信息异常!
	 */
	public static final String DEPOSIT_FUND_SUBMIT_QUERY_BANK_ACCOUNT_ERROR_NOTICE = "deposit_fund_submit_query_bank_account_error_notice";
	/**
	 * 提交入金申请,不支持该银行入金!
	 */
	public static final String DEPOSIT_FUND_SUBMIT_NOT_SUPPORT_ACCOUNT_ERROR_NOTICE = "deposit_fund_submit_not_support_account_error_notice";
	/**
	 * 提交入金申请失败，不是本人账号!
	 */
	public static final String DEPOSIT_FUND_SUBMIT_NOT_YOUR_ACCOUNT_ERROR_NOTICE = "deposit_fund_submit_not_your_account_error_notice";
	/**
	 * 提交入金申请错误,请输入正确金额!
	 */
	public static final String DEPOSIT_FUND_SUBMIT_AMOUNT_ERROR_NOTICE = "deposit_fund_submit_amount_error_notice";
	/**
	 * 提交入金申请错误,两次输入账号不一致!
	 */
	public static final String DEPOSIT_FUND_SUBMIT_TWICE_ERROR_NOTICE = "deposit_fund_submit_twice_error_notice";

	/**
	 * 银行开户证件号码不能为空！
	 */
	public static final String DEPOSIT_FUND_BANK_ID_NO_NOT_NULL_NOTICE = "deposit_fund_bank_id_no_not_null_notice";
	/**
	 * 银行证件类型不能为空！
	 */
	public static final String DEPOSIT_FUND_BANK_ID_KIND_NOT_NULL_NOTICE = "deposit_fund_bank_id_kind_not_null_notice";
	/**
	 * 账户类型不能为空！
	 */
	public static final String DEPOSIT_FUND_DEPOSIT_ACCOUNT_TYPE_NOT_NULL_NOTICE = "deposit_fund_deposit_account_type_not_null_notice";
	/**
	 * 银行账户账号不能为空！
	 */
	public static final String DEPOSIT_FUND_DEPOSIT_ACCOUNT_NOT_NULL_NOTICE = "deposit_fund_deposit_account_not_null_notice";
}
