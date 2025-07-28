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
public class StatementListDTO {
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;

	/**
	 * 结单类型  1日结单 2月结单
	 */
	private Integer statementType;

	/**
	 * 关键字
	 */
	private String keyword;
	/**
	 * 结单状态 0未发送 1已发送 2发送失败
	 */
	private Integer statementStatus;
}
