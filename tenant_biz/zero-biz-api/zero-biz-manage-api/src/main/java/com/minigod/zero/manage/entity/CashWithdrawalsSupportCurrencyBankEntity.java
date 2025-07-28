package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 出金银行、付款方式、币种、付款银行关联表 实体类
 *
 * @author eric
 * @since 2024-10-18 17:42:05
 */
@Data
@TableName("cash_withdrawals_support_currency_bank")
@ApiModel(value = "CashWithdrawalsSupportCurrencyBank对象", description = "出金银行、付款方式、币种、付款银行关联表")
public class CashWithdrawalsSupportCurrencyBankEntity extends TenantEntity {
	/**
	 * 出金银行ID【cash_withdrawals_bank】表主键ID
	 */
	@ApiModelProperty(value = "出金银行ID【cash_withdrawals_bank】表主键ID")
	private Long withdrawalsId;
	/**
	 * 账户类型 1大账户 2子账户
	 */
	@ApiModelProperty(value = "账户类型 1大账户 2子账户")
	private Integer accountType;
	/**
	 * 币种 HKD USD CNY
	 */
	@ApiModelProperty(value = "币种 HKD USD CNY")
	private String currency;
	/**
	 * 收款银行ID
	 */
	@ApiModelProperty(value = "付款银行ID【cash_payee_bank】表主键ID")
	private Long paymentBankId;
	/**
	 * 账户详情ID
	 */
	@ApiModelProperty(value = "账户详情ID【cash_payee_bank_detail】表主键ID")
	private Long paymentBankDetailId;
	/**
	 * 付款方式 1-香港银行普通转账 2-香港银行本地转账 3-电汇 4-FPS ID
	 */
	@ApiModelProperty(value = "付款方式 1-香港银行普通转账 2-香港银行本地转账 3-电汇 4-FPS ID")
	private String supportType;
	/**
	 * 降序排序
	 */
	@ApiModelProperty(value = "降序排序")
	private Integer sortOrder;
	/**
	 * 是否默认
	 */
	@ApiModelProperty(value = "是否默认")
	private Integer isDefault;
}
