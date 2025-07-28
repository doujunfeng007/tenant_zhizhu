package com.minigod.zero.customer.back.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.dto.CustStatementDTO;
import com.minigod.zero.customer.dto.StatementListDTO;
import com.minigod.zero.customer.enums.CustomerStatementAccountFileEnum;

import java.util.Date;

/**
 * 货币日、月结单服务类
 *
 * @author zxq
 * @date  2024/5/22
 **/

public interface CustomerStatementAccountService {


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


	void exportHldBondDailyAccountList2(CustStatementDTO custStatementDTO);

	void exportHldBondMonthAccountList2(CustStatementDTO custStatementDTO);

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
