package com.minigod.zero.manage.dto;

import com.minigod.zero.manage.entity.CashSupportCurrencyBankEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 入金银行 付款方式 币种 收款银行关联表 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CashSupportCurrencyBankDTO extends CashSupportCurrencyBankEntity {
	private static final long serialVersionUID = 1L;

}
