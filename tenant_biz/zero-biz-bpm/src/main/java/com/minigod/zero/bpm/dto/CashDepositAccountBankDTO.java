package com.minigod.zero.bpm.dto;

import com.minigod.zero.bpm.entity.CashDepositAccountBankEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户银行卡记录 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CashDepositAccountBankDTO extends CashDepositAccountBankEntity {
	private static final long serialVersionUID = 1L;

}
