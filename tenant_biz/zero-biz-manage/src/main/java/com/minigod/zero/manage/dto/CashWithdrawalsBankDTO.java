package com.minigod.zero.manage.dto;

import com.minigod.zero.manage.entity.CashWithdrawalsBankEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 出金银行卡配置表 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CashWithdrawalsBankDTO extends CashWithdrawalsBankEntity {
	private static final long serialVersionUID = 1L;

}
