package com.minigod.zero.bpmn.module.constant;

/**
 * 出金消息常量
 *
 * @author eric
 * @since 2024-10-22 13:58:04
 */
public class WithdrawalsFundMessageConstant {
	/**
	 * 提交出金申请失败,根据币种:%s和转账方式:%s,查询付款账户信息异常,不支持该转账方式或币种!
	 */
	public static final String WITHDRAWALS_FUND_SUBMIT_QUERY_RECEIVE_ACCOUNT_ERROR_NOTICE = "withdrawals_fund_submit_query_receive_account_error_notice";
	/**
	 * 提交出金申请失败,根据币种:%s和转账方式:%s,未查询到该转账方式和币种的付款账户信息配置!
	 */
	public static final String WITHDRAWALS_FUND_SUBMIT_RECEIVE_ACCOUNT_ERROR_NOTICE = "withdrawals_fund_submit_receive_account_error_notice";

	/**
	 * 出金银行bankCode和bankId不能为空!
	 */
	public static final String WITHDRAWALS_FUND_SUBMIT_BANK_CODE_ID_EMPTY_NOTICE = "withdrawals_fund_submit_bank_code_id_empty_notice";

	/**
	 * 查询出金银行信息失败，绑定的银行卡失效，请重新绑定!
	 */
	public static final String WITHDRAWALS_FUND_QUERY_WITHDRAWAL_BANK_FAILED_NOTICE = "withdrawals_fund_query_Withdrawal_bank_failed_notice";

	/**
	 * 查询出金银行信息失败,请确认出金银行信息配置是否存在,bankCode:%s,bankId:%s!!
	 */
	public static final String WITHDRAWALS_FUND_QUERY_WITHDRAWAL_BANK_CONFIG_FAILED_NOTICE = "withdrawals_fund_query_Withdrawal_bank_config_failed_notice";

	/**
	 * 提交出金申请失败！
	 */
	public static final String WITHDRAWALS_FUND_SUBMIT_FAILED_NOTICE = "withdrawals_fund_submit_failed_notice";

	/**
	 * 资金账号获取失败！
	 */
	public static final String WITHDRAWALS_FUND_QUERY_FUND_ACCOUNT_FAILED_NOTICE = "withdrawals_fund_query_fund_account_failed_notice";

	/**
	 * 提交绑定/解绑/修改收款银行申请失败！
	 */
	public static final String WITHDRAWALS_FUND_SUBMIT_BANK_BIND_UNBIND_MODIFY_FAILED_NOTICE = "withdrawals_fund_submit_bank_bind_unbind_modify_failed_notice";

	/**
	 * 撤销成功
	 */
	public static final String WITHDRAWALS_FUND_REVOCATION_SUCCESSFUL_NOTICE = "withdrawals_fund_revocation_successful_notice";

	/**
	 * 非法图片类型,只支持jpg,jpeg,png,gif,heic格式!
	 */
	public static final String WITHDRAWALS_FUND_UPLOAD_IMAGE_TYPE_ERROR_NOTICE = "withdrawals_fund_upload_image_type_error_notice";

	/**
	 * 上传图片大小不能超过%sM和小于%sK!
	 */
	public static final String WITHDRAWALS_FUND_UPLOAD_IMAGE_SIZE_ERROR_NOTICE = "withdrawals_fund_upload_image_size_error_notice";

	/**
	 * 上传凭证失败!
	 */
	public static final String WITHDRAWALS_FUND_UPLOAD_VOUCHER_FAILED_NOTICE = "withdrawals_fund_upload_voucher_failed_notice";

	/**
	 * 请确认您提交的汇款账号是您本人的汇款账号！
	 */
	public static final String WITHDRAWALS_FUND_CONFIRM_SUBMIT_ACCOUNT_NOTICE = "withdrawals_fund_confirm_submit_account_notice";

	/**
	 * 银行卡信息不存在！
	 */
	public static final String WITHDRAWALS_FUND_BANK_CARD_NOT_EXIST_NOTICE = "withdrawals_fund_bank_card_not_exist_notice";

	/**
	 * 该银行卡已经存在审批流程!
	 */
	public static final String WITHDRAWALS_FUND_CARD_ALREADY_HAS_APPLICATION_NOTICE = "withdrawals_fund_card_already_has_application_notice";

	/**
	 * 该银行卡存在解绑审批流程！
	 */
	public static final String WITHDRAWALS_FUND_CARD_ALREADY_HAS_UNBINDING_APPLICATION_NOTICE = "withdrawals_fund_card_already_has_unbinding_application_notice";

	/**
	 * 解绑的银行卡不存在
	 */
	public static final String WITHDRAWALS_FUND_UNBOUND_BANK_CARD_NOT_EXIST_NOTICE = "withdrawals_fund_unbound_bank_card_not_exist_notice";

	/**
	 * 该银行卡已解绑！
	 */
	public static final String WITHDRAWALS_FUND_BANK_CARD_BEEN_UNBOUND_NOTICE = "withdrawals_fund_bank_card_been_unbound_notice";

	/**
	 * 该银行卡存在修改审批流程！
	 */
	public static final String WITHDRAWALS_FUND_BANK_CARD_HAS_MODIFICATION_PROCESS_NOTICE = "withdrawals_fund_bank_card_has_modification_process_notice";

	/**
	 * 修改的银行卡不存在!
	 */
	public static final String WITHDRAWALS_FUND_MODIFIED_BANK_CARD_NOT_EXIST_NOTICE = "withdrawals_fund_modified_bank_card_not_exist_notice";

	/**
	 * 已绑定过该银行卡！
	 */
	public static final String WITHDRAWALS_FUND_BANK_CARD_BEEN_BOUND_NOTICE = "withdrawals_fund_bank_card_been_bound_notice";

	/**
	 * 提交银行卡审批申请失败!
	 */
	public static final String WITHDRAWALS_FUND_SUBMIT_APPLICATION_FAILED_NOTICE = "withdrawals_fund_submit_application_failed_notice";

	/**
	 * %s的银行卡ID不能为空！
	 */
	public static final String WITHDRAWALS_FUND_BANK_CARD_ID_EMPTY_NOTICE = "withdrawals_fund_bank_card_id_empty_notice";

	/**
	 * 取款金额必须大于手续费!
	 */
	public static final String WITHDRAWALS_FUND_AMOUNT_ERROR_NOTICE = "withdrawals_fund_amount_error_notice";

	/**
	 * 查询账户可提金额出错!
	 */
	public static final String WITHDRAWALS_FUND_CAN_BE_AMOUNT_ERROR_NOTICE = "withdrawals_fund_can_be_amount_error_notice";

	/**
	 * 可提取余額不足!
	 */
	public static final String WITHDRAWALS_FUND_INSUFFICIENT_AMOUNT_ERROR_NOTICE = "withdrawals_fund_insufficient_amount_error_notice";

	/**
	 * 取款币种: %s 不正确，不支持该币种!
	 */
	public static final String WITHDRAWALS_FUND_CURRENCY_TYPE_ERROR_NOTICE = "withdrawals_fund_currency_type_error_notice";

	/**
	 * 取款申请处理失败!
	 */
	public static final String WITHDRAWALS_FUND_APPLICATION_PROCESSING_ERROR_NOTICE = "withdrawals_fund_application_processing_error_notice";

	/**
	 * 取款冻结金额失败!
	 */
	public static final String WITHDRAWALS_FUND_WITHDRAWAL_FROZEN_AMOUNT_FAILED_NOTICE = "withdrawals_fund_withdrawal_frozen_amount_failed_notice";

	/**
	 * 出金申请信息提交失败，日期解析异常!
	 */
	public static final String WITHDRAWALS_FUND_WITHDRAWAL_DATE_EXCEPTION_NOTICE = "withdrawals_fund_withdrawal_date_exception_notice";

	/**
	 * 客户ID不能为空
	 */
	public static final String WITHDRAWALS_FUND_WITHDRAWAL_CUSTOMER_ID_ERROR_NOTICE = "withdrawals_fund_withdrawal_customer_id_error_notice";

	/**
	 * 理财账号不能为空!
	 */
	public static final String WITHDRAWALS_FUND_MANAGE_ACCOUNT_EMPTY_NOTICE = "withdrawals_fund_manage_account_empty_notice";

	/**
	 * 客户名称不能为空!
	 */
	public static final String WITHDRAWALS_FUND_CUSTOMER_NAME_EMPTY_NOTICE = "withdrawals_fund_customer_name_empty_notice";

	/**
	 * 客户英文名不能为空！
	 */
	public static final String WITHDRAWALS_FUND_CUSTOMER_EN_NAME_EMPTY_NOTICE = "withdrawals_fund_customer_en_name_empty_notice";

	/**
	 * 手机号不能为空!
	 */
	public static final String WITHDRAWALS_FUND_CUSTOMER_MOBILE_EMPTY_NOTICE = "withdrawals_fund_customer_mobile_empty_notice";

	/**
	 * 请选择币种!
	 */
	public static final String WITHDRAWALS_FUND_CURRENCY_EMPTY_NOTICE = "withdrawals_fund_currency_empty_notice";

	/**
	 * 请选择委托时间!
	 */
	public static final String WITHDRAWALS_FUND_COMMISSION_TIME_EMPTY_NOTICE = "withdrawals_fund_commission_time_empty_notice";

	/**
	 * 请选择取款方式!
	 */
	public static final String WITHDRAWALS_FUND_WITHDRAWAL_METHOD_EMPTY_NOTICE = "withdrawals_fund_withdrawal_method_empty_notice";

	/**
	 * 请输入取款金额!
	 */
	public static final String WITHDRAWALS_FUND_WITHDRAWAL_AMOUNT_EMPTY_NOTICE = "withdrawals_fund_withdrawal_amount_empty_notice";
	/**
	 * 请选择收款银行!
	 */
	public static final String WITHDRAWALS_FUND_RECEIVING_BANK_EMPTY_NOTICE = "withdrawals_fund_receiving_bank_empty_notice";

	/**
	 * 请选择收款银行账号!
	 */
	public static final String WITHDRAWALS_FUND_RECEIVING_BANK_ACCOUNT_EMPTY_NOTICE = "withdrawals_fund_receiving_bank_account_empty_notice";

	/**
	 * 银行代码不能为空！
	 */
	public static final String WITHDRAWALS_FUND_BANK_CODE_EMPTY_NOTICE = "withdrawals_fund_bank_code_empty_notice";

	/**
	 * 银行代码格式错误, 限数字!
	 */
	public static final String WITHDRAWALS_FUND_BANK_CODE_FORMAT_NOTICE = "withdrawals_fund_bank_code_format_notice";

	/**
	 * 与第三方收款人关系不能为空!
	 */
	public static final String WITHDRAWALS_FUND_RELATIONSHIP_THIRD_PARTY_NOTICE = "withdrawals_fund_relationship_third_party_notice";

	/**
	 * 第三方收款原因不能为空!
	 */
	public static final String WITHDRAWALS_FUND_REASON_THIRD_PARTY_NOTICE = "withdrawals_fund_reason_third_party_notice";

	/**
	 * 查询DBS付款银行不存在!
	 */
	public static final String WITHDRAWALS_FUND_DBS_BANK_NOT_EXIST_NOTICE = "withdrawals_fund_dbs_bank_not_exist_notice";

	/**
	 * 不支持的银行账户类型, 资金帐号:%s 银行账号类型:%s!
	 */
	public static final String WITHDRAWALS_FUND_UNSUPPORTED_BANK_ACCOUNT_TYPE_NOTICE = "withdrawals_fund_unsupported_bank_account_type_notice";

	/**
	 * FPS ID不支持美金!
	 */
	public static final String WITHDRAWALS_FUND_FPS_NOT_SUPPORT_USD_NOTICE = "withdrawals_fund_fps_not_support_usd_notice";

	/**
	 * FPS Id 验证无效，请重新输入!
	 */
	public static final String WITHDRAWALS_FUND_FPS_ID_INVALID_NOTICE = "withdrawals_fund_fps_id_invalid_notice";

	/**
	 * 取款金额超限，必须小于 %s !
	 */
	public static final String WITHDRAWALS_FUND_AMOUNT_LIMIT_NOTICE = "withdrawals_fund_amount_limit_notice";

}
