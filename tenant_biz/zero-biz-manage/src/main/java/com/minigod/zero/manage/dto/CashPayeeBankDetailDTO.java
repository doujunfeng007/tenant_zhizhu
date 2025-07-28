package com.minigod.zero.manage.dto;

import com.minigod.zero.manage.entity.CashPayeeBankDetailEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 付款账户信息 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CashPayeeBankDetailDTO extends CashPayeeBankDetailEntity {
	private static final long serialVersionUID = 1L;

}
