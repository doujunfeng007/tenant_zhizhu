package com.minigod.common.constant;

/**
 * 出金消息常量
 *
 * @author eric
 * @since 2024-11-12 16:33:05
 */
public class PayMessageConstant {
	/**
	 * 冻结失败，金额必须大于0！
	 */
	public static final String PAY_FREEZE_AMOUNT_ERROR_NOTICE = "pay_freeze_amount_error_notice";

	/**
	 * 冻结失败，币种不能为空！
	 */
	public static final String PAY_FREEZE_CURRENCY_ERROR_NOTICE = "pay_freeze_currency_error_notice";

	/**
	 * 冻结失败，理财账户可用余额小于冻结金额！
	 */
	public static final String PAY_FREEZE_AMOUNT_NOT_ENOUGH_NOTICE = "pay_freeze_amount_not_enough_notice";

	/**
	 * 解冻失败，金额必须大于0！
	 */
	public static final String PAY_UNFREEZING_FAILED_AMOUNT_ERROR_NOTICE = "pay_unfreezing_failed_amount_error_notice";

	/**
	 * 解冻失败，币种不能为空！
	 */
	public static final String PAY_UNFREEZING_FAILED_CURRENCY_ERROR_NOTICE = "pay_unfreezing_failed_currency_error_notice";

	/**
	 * 解冻失败，操作类型不能为空!
	 */
	public static final String PAY_UNFREEZING_FAILED_OPERATION_TYPE_ERROR_NOTICE = "pay_unfreezing_failed_operation_type_error_notice";

	/**
	 * 解冻失败，理财账户冻结金额小于需解冻金额！
	 */
	public static final String PAY_UNFREEZING_FAILED_AMOUNT_NOT_ENOUGH_NOTICE = "pay_unfreezing_failed_amount_not_enough_notice";

	/**
	 * 入金失败，金额必须大于0！
	 */
	public static final String PAY_DEPOSIT_AMOUNT_ERROR_NOTICE = "pay_deposit_amount_error_notice";

	/**
	 * 入金失败，币种不能为空！
	 */
	public static final String PAY_DEPOSIT_CURRENCY_ERROR_NOTICE = "pay_deposit_currency_error_notice";

	/**
	 * 入金失败，统一账号不能为空！
	 */
	public static final String PAY_DEPOSIT_UNIFIED_ACCOUNT_ERROR_NOTICE = "pay_deposit_unified_account_error_notice";

	/**
	 * 划扣失败，理财账号不存在！
	 */
	public static final String PAY_DEDUCTION_FAILED_ACCOUNT_NOT_EXIST_NOTICE = "pay_deduction_failed_account_not_exist_notice";

	/**
	 * 划扣失败，理财账户余额不足！
	 */
	public static final String PAY_DEDUCTION_FAILED_AMOUNT_NOT_ENOUGH_NOTICE = "pay_deduction_failed_amount_not_enough_notice";

	/**
	 * 确认支付操作失败,业务提供的BusinessId不存在!
	 */
	public static final String PAY_CONFIRM_PAYMENT_FAILED_BUSINESS_ID_NOT_EXIST_NOTICE = "pay_confirm_payment_failed_business_id_not_exist_notice";

	/**
	 * 确认支付操作失败,businessId = %s 的记录已处理!
	 */
	public static final String PAY_CONFIRM_PAYMENT_FAILED_BUSINESS_ID_HANDLED_NOTICE = "pay_confirm_payment_failed_business_id_handled_notice";

	/**
	 * 确认支付操作失败,businessId = %s 扣减币种与冻结时币种不一致!
	 */
	public static final String PAY_CONFIRM_PAYMENT_FAILED_CURRENCY_NOT_EQUAL_NOTICE = "pay_confirm_payment_failed_currency_not_equal_notice";

	/**
	 * 确认支付操作失败,金额异常,不能超过订单原先总金额!
	 */
	public static final String PAY_CONFIRM_PAYMENT_FAILED_AMOUNT_ERROR_NOTICE = "pay_confirm_payment_failed_amount_error_notice";

	/**
	 * 在途转可用操作失败,业务提供的businessId不存在!
	 */
	public static final String PAY_TRANSITED_TO_AVAILABLE_FAILED_BUSINESS_ID_NOT_EXIST_NOTICE = "pay_transited_to_available_failed_business_id_not_exist_notice";

	/**
	 * 在途转可用操作失败,businessId = %s 的记录已处理!
	 */
	public static final String PAY_TRANSITED_TO_AVAILABLE_FAILED_BUSINESS_ID_HANDLED_NOTICE = "pay_transited_to_available_failed_business_id_handled_notice";

	/**
	 * 在途转可用操作失败,businessId = %s 扣减币种与冻结时币种不一致!
	 */
	public static final String PAY_TRANSITED_TO_AVAILABLE_FAILED_CURRENCY_NOT_EQUAL_NOTICE = "pay_transited_to_available_failed_currency_not_equal_notice";

	/**
	 * 在途转可用操作失败,金额异常,不能超过订单原先总金额!
	 */
	public static final String PAY_TRANSITED_TO_AVAILABLE_FAILED_AMOUNT_ERROR_NOTICE = "pay_transited_to_available_failed_amount_error_notice";

	/**
	 * 操作失败，理财账号不存在！
	 */
	public static final String PAY_OPERATION_FAILED_ACCOUNT_NOT_EXIST_NOTICE = "pay_operation_failed_account_not_exist_notice";

	/**
	 * 理财账号不能为空！
	 */
	public static final String PAY_OPERATION_ACCOUNT_ID_ERROR_NOTICE = "pay_operation_account_id_error_notice";

	/**
	 * 业务id不能为空！
	 */
	public static final String PAY_OPERATION_BUSINESS_ID_ERROR_NOTICE = "pay_operation_business_id_error_notice";

	/**
	 * 付款明细不能为空!
	 */
	public static final String PAY_OPERATION_PAY_DETAILS_ERROR_NOTICE = "pay_operation_pay_details_error_notice";

	/**
	 * 币种不能为空！
	 */
	public static final String PAY_OPERATION_CURRENCY_ERROR_NOTICE = "pay_operation_currency_error_notice";

	/**
	 * 来源不能为空！
	 */
	public static final String PAY_OPERATION_SOURCE_ERROR_NOTICE = "pay_operation_source_error_notice";

	/**
	 * 金额不正确！
	 */
	public static final String PAY_OPERATION_AMOUNT_ERROR_NOTICE = "pay_operation_amount_error_notice";

	/**
	 * 业务描述类型不能为空！
	 */
	public static final String PAY_OPERATION_BUSINESS_TYPE_ERROR_NOTICE = "pay_operation_business_type_error_notice";

}
