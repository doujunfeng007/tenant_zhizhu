package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerFundCapitalAccountEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基金账户信息表 数据传输对象实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerFundCapitalAccountDTO extends CustomerFundCapitalAccountEntity {
	private static final long serialVersionUID = 1L;

}
