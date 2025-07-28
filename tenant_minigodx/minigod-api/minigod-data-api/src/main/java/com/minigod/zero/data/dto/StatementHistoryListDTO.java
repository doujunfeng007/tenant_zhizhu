package com.minigod.zero.data.dto;

import lombok.Data;

/**
 * @ClassName: com.minigod.zero.customer.dto.StatementListDTO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/8/27 10:23
 * @Version: 1.0
 */
@Data
public class StatementHistoryListDTO {
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 发送次数
	 */
	private Integer sendNum;
	/**
	 * 关键字
	 */
	private String keyword;
	/**
	 * 结单状态
	 */
	private Integer statementStatus;
}
