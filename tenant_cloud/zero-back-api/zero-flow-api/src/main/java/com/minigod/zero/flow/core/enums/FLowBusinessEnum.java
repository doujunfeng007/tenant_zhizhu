package com.minigod.zero.flow.core.enums;

import com.minigod.zero.flow.core.constant.ProcessConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName: FLowBusinessEnum
 * @Description: 添加流程业务对应的表和字典 key
 * @Author chenyu
 * @Date 2024/3/4
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum FLowBusinessEnum {
	ONLINE_ACCOUNT(ProcessConstant.OPEN_ACCOUNT_KEY, "customer_account_open_application", "customer_open_online_flow"),
	DEPOSIT(ProcessConstant.DEPOSIT_KEY, "client_fund_deposit_application", "customer_deposit_online_flow"),
	WITHDRAW(ProcessConstant.WITHDRAW_KEY, "client_fund_withdraw_application", "customer_withdraw_online_flow"),
	MARGIN_CREDIT(ProcessConstant.MARGIN_CREDIT, "increase_credit_limit_application", "increase_credit_limit_flow"),
	BANK_CARD(ProcessConstant.BANK_CARD_KEY, "client_bank_card_application", "customer_bank_card_flow"),
	REFUND(ProcessConstant.REFUND_KEY, "client_fund_refund_application", "customer_refund_flow"),
	CURRENCY_EXCHANGE(ProcessConstant.CURRENCY_EXCHANGE, "customer_currency_exchange_application", "customer_currency_exchange_flow"),
	PROFESSIONAL_INVESTOR_PI(ProcessConstant.PROFESSIONAL_INVESTOR_PI_KEY, "professional_investor_pi_application", "professional_investor_pi_flow"),
	FUND_TRANS(ProcessConstant.FUND_TRANS_KEY, "client_fund_trans_application", "fund_trans_flow"),
	OPEN_STOCK_ACCOUNT(ProcessConstant.OPEN_STOCK_ACCOUNT_KEY, "customer_account_stock_application", "open_stock_account_flow"),
	;
	private String businessKey;
	private String table;
	private String dictKey;
}
