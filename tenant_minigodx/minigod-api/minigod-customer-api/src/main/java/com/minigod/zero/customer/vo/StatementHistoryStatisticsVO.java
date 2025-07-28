package com.minigod.zero.customer.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName: com.minigod.zero.customer.vo.StatementTabulateStatisticsVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/8/27 10:47
 * @Version: 1.0
 */
@Data
public class StatementHistoryStatisticsVO {
	/**
	 * 客户数
	 */
	private Integer custSize = 0;

	/**
	 * 结单总数
	 */
	private Integer statementSize = 0;

	/**
	 * 失败数
	 */
	private Integer statementFalseSize = 0;
	/**
	 * 未发送数
	 */
	private Integer statementUnSetSize = 0;
	/**
	 * 成功数
	 */
	private Integer statementSuccessSize = 0;

	/**
	 * 结单类型  1日结单 2月结单
	 */
	private Integer statementType;


	/**
	 * 区间开始时间
	 */
	private String startSectionTime;
	/**
	 * 区间结束时间
	 */
	private String endSectionTime;
}
