package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.time.LocalDateTime;


/**
 *  实体类
 *
 * @author Chill
 */
@Data
@TableName("open_account_deposit_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountDepositInfo对象", description = "")
public class AccountDepositInfoEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 入金银行
	 */
	@ApiModelProperty(value = "入金银行")
	private String outBank;
	/**
	 * 入金银行code
	 */
	@ApiModelProperty(value = "入金银行code")
	private String bankCode;
	/**
	 * 入金银行账户
	 */
	@ApiModelProperty(value = "入金银行账户")
	private String bankAccountNumber;
	/**
	 * 持有人名称
	 */
	@ApiModelProperty(value = "持有人名称")
	private String accountHolderName;
	/**
	 * 账户货币类型
	 */
	@ApiModelProperty(value = "账户货币类型")
	private Integer accountCurrencyType;
	/**
	 * 存入货币类型
	 */
	@ApiModelProperty(value = "存入货币类型")
	private Integer depositCurrencyType;
	/**
	 * 存入金额
	 */
	@ApiModelProperty(value = "存入金额")
	private BigDecimal depositMoney;
	/**
	 * 存入时间
	 */
	@ApiModelProperty(value = "存入时间")
	private String depositDate;
	/**
	 * 流水号
	 */
	@ApiModelProperty(value = "流水号")
	private String applicationId;
	/**
	 * 审核意见
	 */
	@ApiModelProperty(value = "审核意见")
	private String auditOpinion;
	/**
	 * 存入银行账户
	 */
	@ApiModelProperty(value = "存入银行账户")
	private String receiveBank;
	/**
	 * 入金银行id
	 */
	private Long depositBankId;

}
