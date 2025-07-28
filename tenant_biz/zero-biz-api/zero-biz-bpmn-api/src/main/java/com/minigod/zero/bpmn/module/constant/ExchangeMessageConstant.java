package com.minigod.zero.bpmn.module.constant;

/**
 * @ClassName: com.minigod.zero.bpmn.module.constant.ExchangeMessageConstant
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/11/12 10:05
 * @Version: 1.0
 */
public class ExchangeMessageConstant {
	/**
	 * 客户可提余额不足
	 */
	public static final String EXCHANGE_INSUFFICIENT_AVAILABLE_BALANCE_NOTICE = "exchange_insufficient_available_balance_notice";
	/**
	 * 客户申请兑出金额为空或可提余额为空
	 */
	public static final String EXCHANGE_AMOUNT_OR_AVAILABLE_BALANCE_EMPTY_NOTICE = "exchange_amount_or_available_balance_empty_notice";
	/**
	 * 获取余额失败
	 */
	public static final String EXCHANGE_BALANCE_RETRIEVAL_FAILED_NOTICE = "exchange_balance_retrieval_failed_notice";

}
