
package com.minigod.zero.flow.core.constant;

/**
 * 流程常量.
 *
 * @author Chill
 */
public interface ProcessConstant {

	/**
	 * 请假流程标识
	 */
	String LEAVE_KEY = "Leave";
	/**
	 * 开户流程标识
	 */
	String OPEN_ACCOUNT_KEY = "OPEN_ACCOUNT";
	/**
	 * 入金流程标识
	 */
	String DEPOSIT_KEY = "DEPOSIT";
	/**
	 * 出金流程标识
	 */
	String WITHDRAW_KEY = "WITHDRAW";

	/**
	 * 信用额度
	 */
	String MARGIN_CREDIT = "MARGIN_CREDIT";

	/**
	 * 货币兑换
	 */
	String CURRENCY_EXCHANGE = "CURRENCY_EXCHANGE";

	/**
	 * 银行卡流程标识
	 */
	String BANK_CARD_KEY = "BANK_CARD";

	/**
	 * 退款流程标识
	 */
	String REFUND_KEY = "REFUND";

	/**
	 * 报销流程标识
	 */
	String EXPENSE_KEY = "Expense";

	/**
	 * 同意标识
	 */
	String PASS_KEY = "pass";

	/**
	 * 同意代号
	 */
	String PASS_ALIAS = "ok";

	/**
	 * 同意默认批复
	 */
	String PASS_COMMENT = "同意";

	/**
	 * 驳回默认批复
	 */
	String NOT_PASS_COMMENT = "驳回";

	/**
	 * 创建人变量名
	 */
	String TASK_VARIABLE_CREATE_USER = "createUser";
	/**
	 * 专业投资人PI流程标识
	 */
	String PROFESSIONAL_INVESTOR_PI_KEY = "PROFESSIONAL_INVESTOR_PI";

	/**
	 * 资金调拨流程标识
	 */
	String FUND_TRANS_KEY = "FUND_TRANS";

	/**
	 * 增开股票流程标识
	 */
	String OPEN_STOCK_ACCOUNT_KEY = "OPEN_STOCK_ACCOUNT";

}
