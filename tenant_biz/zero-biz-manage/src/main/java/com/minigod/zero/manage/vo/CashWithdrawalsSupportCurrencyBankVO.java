package com.minigod.zero.manage.vo;

import com.minigod.zero.manage.entity.CashWithdrawalsSupportCurrencyBankEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 出金银行、付款方式、币种、付款银行关联表 视图实体类
 *
 * @author eric
 * @since 2024-10-21 10:45:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CashWithdrawalsSupportCurrencyBankVO extends CashWithdrawalsSupportCurrencyBankEntity {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "币种名称")
	private String currencyName;
	@ApiModelProperty(value = "付款方式名称")
	private String supportTypeName;
	@ApiModelProperty(value = "账户类型名称")
	private String accountTypeName;
	@ApiModelProperty(value = "出金银行名称")
	private String withdrawalsBankName;
	@ApiModelProperty(value = "付款银行名称")
	private String paymentBankName;
}
