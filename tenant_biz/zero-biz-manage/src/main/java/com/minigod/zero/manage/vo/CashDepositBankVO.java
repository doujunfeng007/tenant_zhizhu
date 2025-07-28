package com.minigod.zero.manage.vo;

import com.minigod.zero.manage.entity.CashDepositBankEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 入金银行信息维护表 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CashDepositBankVO extends CashDepositBankEntity {
	private static final long serialVersionUID = 1L;

	private Map<String, Object> supportMap;

}
