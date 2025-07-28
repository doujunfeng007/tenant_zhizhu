package com.minigod.zero.customer.vo;

import lombok.Data;

/**
 * @ClassName: com.minigod.zero.customer.vo.StatementTabulateStatisticsVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/8/27 10:47
 * @Version: 1.0
 */
@Data
public class StatementTabulateStatisticsVO {
	/**
	 * 客户数
	 */
	private Integer custSize = 0;

	/**
	 * 结单数
	 */
	private Integer statementSize = 0;
}
