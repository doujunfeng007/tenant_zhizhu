package com.minigod.zero.bpmn.module.constant;

/**
 * EDDA入金消息常量
 */
public class EDDAMessageConstant {
	/**
	 * 入金申请记录详情信息查询失败,数据不存在!
	 */
	public static final String EDDA_FUND_APPLICATION_DETAIL_QUERY_FAILED_NOTICE = "edda_fund_application_detail_query_failed_notice";

	/**
	 * EDDA 授权信息不存在
	 */
	public static final String EDDA_AUTH_INFO_NOT_EXIST_NOTICE = "edda_auth_info_not_exist_notice";

	/**
	 * 账户: %s 数据异常!
	 */
	public static final String EDDA_ACCOUNT_DATA_EXCEPTION_NOTICE = "edda_account_data_exception_notice";

	/**
	 * 账户: %s 还未通过EDDA授权!
	 */
	public static final String EDDA_ACCOUNT_NOT_AUTH_NOTICE = "edda_account_not_auth_notice";

	/**
	 * 根据入金银行和汇款方式、币种查询收款银行信息失败!
	 */
	public static final String EDDA_RECEIVING_BANK_QUERY_FAILED_NOTICE = "edda_receiving_bank_query_failed_notice";

	/**
	 * 未配置该入金银行和汇款方式、币种的收款信息!
	 */
	public static final String EDDA_RECEIVING_BANK_NOT_CONFIG_NOTICE = "edda_receiving_bank_not_config_notice";

	/**
	 * 获取收款银行列表失败!
	 */
	public static final String EDDA_RECEIVING_BANK_LIST_QUERY_FAILED_NOTICE = "edda_receiving_bank_list_query_failed_notice";

	/**
	 * 收款银行列表数据为空!
	 */
	public static final String EDDA_RECEIVING_BANK_LIST_EMPTY_NOTICE = "edda_receiving_bank_list_empty_notice";

	/**
	 * 当前账号已授权
	 */
	public static final String EDDA_CURRENT_ACCOUNT_AUTHORIZED = "edda_current_account_authorized";

	/**
	 * 当前账号正在授权中
	 */
	public static final String EDDA_CURRENT_ACCOUNT_UNDER_AUTHORIZATION = "edda_current_account_under_authorization";

	/**
	 * 入金银行信息查询失败
	 */
	public static final String EDDA_DEPOSIT_FUNDS_BANK_QUERY_FAIL = "edda_deposit_funds_bank_query_fail";

	/**
	 * 入金银行信息异常
	 */
	public static final String EDDA_DEPOSIT_FUNDS_BANK_QUERY_ABNORMAL = "edda_deposit_funds_bank_query_abnormal";

	/**
	 * 获取客户信息失败 msg:
	 */
	public static final String EDDA_OBTAIN_CUSTOMER_INFO_FAIL = "edda_obtain_customer_info_fail";

	/**
	 * 数据不存在
	 */
	public static final String EDDA_DATA_NOT_EXIST = "edda_data_not_exist";

	/**
	 * 不可撤销
	 */
	public static final String EDDA_NO_REVOKE = "edda_no_revoke";

	/**
	 * 删除失败
	 */
	public static final String EDDA_DELETE_FAIL = "edda_delete_fail";

	/**
	 * 获取交易账号失败
	 */
	public static final String EDDA_OBTAIN_TRADE_ACCOUNT_FAIL = "edda_obtain_trade_account_fail";

	/**
	 * 获取交易账号信息异常
	 */
	public static final String EDDA_OBTAIN_TRADE_ACCOUNT_ABNORMAL = "edda_obtain_trade_account_abnormal";

	/**
	 * edda 授权银行卡id不能为空
	 */
	public static final String EDDA_GRANT_BANK_CARD_ID_NOT_EMPTY = "edda_grant_bank_card_id_not_empty";

	/**
	 * 入金币种不能为空
	 */
	public static final String EDDA_DEPOSIT_FUNDS_CURRENCY_NOT_EMPTY = "edda_deposit_funds_currency_not_empty";

	/**
	 * 请输入正确金额
	 */
	public static final String EDDA_INPUT_CORRECT_MONEY = "edda_input_current_money";
}
