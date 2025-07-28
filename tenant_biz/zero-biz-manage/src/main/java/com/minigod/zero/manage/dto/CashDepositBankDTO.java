package com.minigod.zero.manage.dto;

import com.minigod.zero.manage.entity.CashDepositBankEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 入金银行信息维护表 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CashDepositBankDTO extends CashDepositBankEntity {
	private static final long serialVersionUID = 1L;

	private Integer bankType;
	private String startDate;
	private String endDate;
	private Integer isShow = -1;

}
