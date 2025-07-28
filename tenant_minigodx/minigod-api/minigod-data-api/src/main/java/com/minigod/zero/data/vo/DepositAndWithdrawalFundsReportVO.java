package com.minigod.zero.data.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Title: 出入金资汇总表VO
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/23 10:58
 * @description：
 */
@Data
public class DepositAndWithdrawalFundsReportVO implements Serializable {
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
	 * 类型
	 */
	private Integer supportType;
	/**
	 * 类型名称
	 */
	private String supportTypeName;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 操作人id
	 */
	private Long operator;
	/**
	 * 操作人名称
	 */
	private String operatorName;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 状态名称
	 */
	private String statusName;
	/**
	 * 类型1存入，2提现
	 */
	private Integer type;
	/**
	 * 类型名称
	 */
	private String typeName;
	/**
	 * 申请记录id
	 */
	private String applicationId;

	/**
	 * 交收时间
	 */
	private String deliveryTime;
}
