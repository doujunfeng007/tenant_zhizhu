package com.minigod.zero.manage.vo;

import com.minigod.zero.manage.entity.CashSupportCurrencyBankEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 入金银行 付款方式 币种 收款银行关联表 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CashSupportCurrencyBankVO extends CashSupportCurrencyBankEntity {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "币种名称")
	private String currencyName;
	@ApiModelProperty(value = "付款方式名称")
	private String supportTypeName;
	@ApiModelProperty(value = "账户类型名称")
	private String accountTypeName;
	@ApiModelProperty(value = "汇款银行名称")
	private String remitBankName;
	@ApiModelProperty(value = "收款银行名称")
	private String depositBankName;

}
