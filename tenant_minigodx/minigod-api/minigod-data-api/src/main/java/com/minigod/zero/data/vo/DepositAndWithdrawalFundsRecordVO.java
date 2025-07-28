package com.minigod.zero.data.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Title: 出入金资汇总表记录
 * @Author： eric
 * @Date：2024/10/30 13:31:58
 * @description：出入金资汇总表记录
 */
@Data
public class DepositAndWithdrawalFundsRecordVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 时间
	 */
	private String createTime;
	/**
	 * 理财账号
	 */
	private String clientId;
	/**
	 * 客户名称
	 */
	private String clientName;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 类型1存入，2提现
	 */
	private Integer type;
	/**
	 * 类型名称
	 */
	private String typeName;
}

