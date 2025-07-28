package com.minigod.zero.bpm.dto;

import com.minigod.zero.bpm.entity.CashDepositFundsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 存入资金表 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CashDepositFundsDTO extends CashDepositFundsEntity {
	private static final long serialVersionUID = 1L;

}
