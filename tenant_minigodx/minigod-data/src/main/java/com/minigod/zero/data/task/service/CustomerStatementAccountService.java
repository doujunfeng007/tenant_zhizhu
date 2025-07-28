package com.minigod.zero.data.task.service;

import com.minigod.zero.data.dto.CustStatementDTO;
import com.minigod.zero.data.enums.CustomerStatementAccountFileEnum;

import java.util.Date;

/**
 * @ClassName: com.minigod.zero.data.task.service.CustomerStatementAccountService
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/10/31 19:49
 * @Version: 1.0
 */
public interface CustomerStatementAccountService {
	void exportHldBondDailyAccountList2(CustStatementDTO custStatementDTO);

	void exportHldBondMonthAccountList2(CustStatementDTO custStatementDTO);


	/**
	 * 根据枚举类型获取模板路径
	 *
	 * @param tenantId 租户id
	 * @param accountFileEnum 模板
	 * @return String
	 */
	String getTemplatePathByPdfType(String tenantId, CustomerStatementAccountFileEnum accountFileEnum);

	/**
	 * 生成表单输出路径
	 *
	 * @param tenantId 租户id
	 * @return String
	 */
	String makeOutputPath(String tenantId);


	void exportHldBondDailyAccountList3(CustStatementDTO custStatementDTO);

	/**
	 * 获取某个时间的所处某月的最后一个交易日
	 * @param date
	 * @return
	 */
	String getLatestTradeDate(Date date);

	/**
	 * 获取某一时间的最近交易日(自己或者大于该时间的第一个交易日)
	 * @param date
	 * @return
	 */
	String getBaseTradeDate(Date date);
}
