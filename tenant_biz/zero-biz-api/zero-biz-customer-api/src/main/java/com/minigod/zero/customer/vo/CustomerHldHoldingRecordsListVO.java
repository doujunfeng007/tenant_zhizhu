package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.CustomerHldHoldingRecords;
import lombok.Data;
/**
 * 活利得持仓记录返回对象
 *
 * @author zxq
 * @date  2024/5/13
 **/
@Data
public class CustomerHldHoldingRecordsListVO extends CustomerHldHoldingRecords {
	/**
	 * 交易账户
	 */
	private String tradeAccount;

	/**
	 * 交易账户
	 */
	private String fundAccount;
}
