package com.minigod.zero.bpmn.module.deposit.dto;

import com.minigod.zero.bpmn.module.deposit.entity.SecDepositAccountBankEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户银行卡记录 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SecDepositAccountBankDTO extends SecDepositAccountBankEntity {
	private static final long serialVersionUID = 1L;

}
