package com.minigod.zero.bpmn.module.account.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/17 13:51
 * @description：
 */
@Data
public class CustomerOpenAccountVO {
	/**
	 * 用户id
	 */
	private Long custId;
	/**
	 * 客户中文姓名
	 */
	private String name;
	/**
	 * 英文名
	 */
	private String enName;
	/**
	 * 登录手机号
	 */
	private String phone;
	/**
	 * 开户手机
	 */
	private String openPhone;
	/**
	 * 开户邮箱
	 */
	private String openEmail;
	/**
	 * 理财账号
	 */
	private String accountId;
	/**
	 * 风险等级
	 */
	private Integer riskLevel;

	private String riskLevelName;
	/**
	 * 基金交易账号
	 */
	private String fundTradeAccount;
	/**
	 * pi等级
	 */
	private Integer piLevel;

	/**
	 * 活利得交易账号
	 */
	private String hldTradeAccount;

	/**
	 * 债券易交易账号
	 */
	private String bondTradeAccount;

	/**
	 * 活利得账号风险等级
	 */
	private Integer hldRiskLevel;


	private String hldRiskLevelName;

	/**
	 * 债券易账号风险等级
	 */
	private Integer bondRiskLevel;

	private String bondRiskLevelName;
	/**
	 * 开户时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 状态说明
	 */
	private String statusName;
	/**
	 * 日结单
	 */
	private String dailyStatement;
	/**
	 * 月结单
	 */
	private String monthlyStatement;

	/**
	 *结单日期
	 */
	private String statementTime;

	/**
	 * 1未开户，2已开户，3已入金
	 */
	private String openStatus;

	private Date applicationTime;
	private Date openAccountTime;
}
