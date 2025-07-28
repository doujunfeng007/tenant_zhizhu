package com.minigod.zero.customer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 货币结单模板
 *
 * @author zxq
 * @date 2024/5/22
 **/
@Getter
@AllArgsConstructor
public enum CustomerStatementAccountFileEnum {
	HLD_BOND_DAILY_STATEMENT_ACCOUNT_TEMPLATE(1, "日结单"),
	HLD_BOND_MONTH_STATEMENT_ACCOUNT_TEMPLATE(2, "月结单"),
	HLD_BOND_DAY_STATEMENT_ACCOUNT_TEMPLATE(1, "日结单"),
	;


	private final Integer code;

	private final String message;

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
