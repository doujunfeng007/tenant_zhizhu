package com.minigod.zero.bpmn.module.constant;

/**
 * 线上支付消息提示常量
 *
 * @author eric
 * @since 2024-09-20 18:08:08
 */
public class PayCategoryMessageConstant {
	/**
	 * 商品名称不能为空
	 */
	public static final String PAY_CATEGORY_PRODUCT_NAME_CANNOT_BE_EMPTY = "pay_category_product_name_cannot_be_empty";
	/**
	 * 商品代码不能为空
	 */
	public static final String PAY_CATEGORY_PRODUCT_CODE_CANNOT_BE_EMPTY = "pay_category_product_code_cannot_be_empty";
	/**
	 * 卖出金额不能为空
	 */
	public static final String PAY_CATEGORY_SELL_MONEY_CANNOT_BE_EMPTY = "pay_category_sell_money_cannot_be_empty";

	/**
	 * 可用现金不能为空
	 */
	public static final String PAY_CATEGORY_AVAILABLE_CASH_CANNOT_BE_EMPTY = "pay_category_available_cash_cannot_be_empty";

	/**
	 * 提交失败
	 */
	public static final String PAY_CATEGORY_SUBMIT_FAIL = "pay_category_submit_fail";

	/**
	 * 支付订单不存在
	 */
	public static final String PAY_CATEGORY_PAY_ORDER_NOT_EXIST = "pay_category_pay_order_not_exist";

	/**
	 * 该订单已支付
	 */
	public static final String PAY_CATEGORY_ORDER_PAID = "pay_category_order_paid";

	/**
	 * 证券订单用户未提交,不可支付
	 */
	public static final String PAY_CATEGORY_SECURITY_ORDER_USER_UNCOMMITTED = "pay_category_security_order_user_uncommitted";

	/**
	 * 该订单未全部成交,不可支付
	 */
	public static final String PAY_CATEGORY_ORDER_NOT_ALL_COMMITTED = "pay_category_order_not_all_committed";

	/**
	 * 基金订单详情获取失败 msg:
	 */
	public static final String PAY_CATEGORY_FUND_ORDER_DETAIL_GET_FAIL = "pay_category_fund_order_detail_get_fail";

	/**
	 * 支付失败
	 */
	public static final String PAY_CATEGORY_PAY_FAIL = "pay_category_pay_fail";

	/**
	 * 该订单已有流水号
	 */
	public static final String PAY_CATEGORY_ORDER_HAS_SERIAL_NUMBER = "pay_category_order_has_serial_number";

	/**
	 * 修改失败
	 */
	public static final String PAY_CATEGORY_UPDATE_FAIL = "pay_category_update_fail";
}
