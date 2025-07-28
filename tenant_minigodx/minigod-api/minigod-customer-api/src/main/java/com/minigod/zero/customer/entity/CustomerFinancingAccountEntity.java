package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;

/**
 * 客户理财账户表 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("customer_financing_account")
@ApiModel(value = "CustomerFinancingAccount对象", description = "客户理财账户表")
@EqualsAndHashCode(callSuper = true)
public class CustomerFinancingAccountEntity extends TenantEntity {

	/**
	 * 客户号（个人/授权人）
	 */
	private Long custId;

	/**
	 * 租户id
	 */
	private String tenantId;

	/**
	 * 理财账户id
	 */
	private String accountId;

	/**
	 * 理财账户交易密码
	 */
	private String password;
	/**
	 * 修改密码时间
	 */
	private Date updatePwdTime;

	/**
	 * 账户状态
	 * {@link com.minigod.zero.customer.enums.FinancingAccountStatus}
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 在途金额
	 */
	private BigDecimal transitedAmount = BigDecimal.ZERO;

	/**
	 *
	 */
	private Integer isDeleted;

	/**
	 * 账户类型，0个人户，1机构户
	 */
	private Integer accountType;

	/**
	 * 激活时间
	 */
	private Date activationTime;


	private static final long serialVersionUID = 1L;


}
