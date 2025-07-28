package com.minigod.zero.customer.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName customer_financing_sub_account
 */
@Builder
@Data
public class CustomerFinancingSubAccount implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 理财账号
     */
    private String accountId;

    /**
     * 子账号
     */
    private String subAccount;

    /**
     * 账号类型
     */
    private Integer roleId;

    /**
     * 0正常，1删除
     */
    private Integer status;

    /**
     * 创建人id
     */
    private String creatorId;

    /**
     *
     */
    private Date createTime;

    /**
     * 创建人名字
     */
    private String creatorName;

    /**
     *
     */
    private Date updateTime;

	/**
	 * 密码
	 */
	private String password;

    private static final long serialVersionUID = 1L;
}
