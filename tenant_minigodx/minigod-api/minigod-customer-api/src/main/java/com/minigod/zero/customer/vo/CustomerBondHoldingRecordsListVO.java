package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.CustomerBondHoldingRecords;
import lombok.Data;
/**
 * 债券持仓记录返回对象
 *
 * @author zxq
 * @date  2024/5/13
 **/
@Data
public class CustomerBondHoldingRecordsListVO extends CustomerBondHoldingRecords {
	/**
	 * 交易账户
	 */
	private String tradeAccount;

	/**
	 * 交易账户
	 */
	private String fundAccount;
}
