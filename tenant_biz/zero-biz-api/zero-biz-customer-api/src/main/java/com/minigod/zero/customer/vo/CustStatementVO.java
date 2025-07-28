package com.minigod.zero.customer.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

/**
 * @ClassName: com.minigod.zero.customer.vo.CustStatementVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/24 2:22
 * @Version: 1.0
 */
@Data
public class CustStatementVO {
	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 公司logo地址
	 */
	private String companyLogoUrl;

	/**
	 * 结单日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date bondDailyAccountDate;

	/**
	 * 客户名称
	 */
	private String custName;

	/**
	 * 账户号码
	 */
	private String accountNumber;

	/**
	 * 账户币种
	 */
	private String accountCurrency;

	/**
	 * 联系地址
	 */
	private String accountContactAddress;
	/**
	 * 子账户
	 */
	private String subAccountId;
	/**
	 * 理财账号
	 */
	private String accountId;
}
