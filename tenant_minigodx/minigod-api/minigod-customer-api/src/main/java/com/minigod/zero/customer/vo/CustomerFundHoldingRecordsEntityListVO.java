package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.CustomerFundHoldingRecordsEntity;
import lombok.Data;

@Data
public class CustomerFundHoldingRecordsEntityListVO extends CustomerFundHoldingRecordsEntity {
	/**
	 * 交易账户
	 */
	private String tradeAccount;

	/**
	 * 交易账户
	 */
	private String fundAccount;
}
