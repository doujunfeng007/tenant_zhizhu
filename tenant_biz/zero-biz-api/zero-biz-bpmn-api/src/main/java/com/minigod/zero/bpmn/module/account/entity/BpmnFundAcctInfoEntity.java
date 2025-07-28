package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基金账户信息表 实体类
 *
 * @author eric
 * @since 2024-08-20 15:27:05
 */
@Data
@TableName("bpm_fund_acct_info")
@ApiModel(value = "BpmnFundAcctInfo对象", description = "基金账户信息表")
public class BpmnFundAcctInfoEntity extends TenantEntity {
	/**
	 * 交易账户
	 */
	@ApiModelProperty(value = "交易账户")
	private String tradeAccount;
	/**
	 * 基金主账户
	 */
	@ApiModelProperty(value = "基金主账户")
	private String fundAccountMain;
	/**
	 * 基金子账户
	 */
	@ApiModelProperty(value = "基金子账户")
	private String fundAccount;
	/**
	 * 基金账户类型[0-个人户 1-机构户]
	 */
	@ApiModelProperty(value = "基金账户类型[0-个人户 1-机构户]")
	private Integer fundAccountType;
	/**
	 * 基金账户权限：0-不可申购和赎回 1-可申购可赎回 2-可申购不可赎回 3-不可申购可赎回
	 */
	@ApiModelProperty(value = "基金账户权限：0-不可申购和赎回 1-可申购可赎回 2-可申购不可赎回 3-不可申购可赎回")
	private Integer fundOperType;
	/**
	 * 基金账户类型 0:正常
	 */
	@ApiModelProperty(value = "基金账户类型 0:正常")
	private Integer accountStatus;
}
