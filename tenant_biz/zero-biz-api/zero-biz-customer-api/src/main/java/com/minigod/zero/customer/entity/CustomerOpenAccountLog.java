package com.minigod.zero.customer.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户开户日志表
 * @TableName customer_open_account_log
 */
@Data
public class CustomerOpenAccountLog implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 用户id
     */
    private Long custId;

	/**
	 * 客户姓名
	 */
	private String name;

    /**
     * 理财账号
     */
    private String accountId;

    /**
     *
     */
    private Integer riskLevel;

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

    /**
     * 债券易账号风险等级
     */
    private Integer bondRiskLevel;

    /**
     * 当前所在步骤
     */
    private String stepTitle;

    /**
     * 请求参数
     */
    private String parameter;

    /**
     * 异常信息
     */
    private String exception;

    /**
     * 返回参数
     */
    private String returnValue;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;


}
