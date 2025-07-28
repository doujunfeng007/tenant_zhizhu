package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerStockTradingAccountEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 交易账户信息表 数据传输对象实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerStockTradingAccountDTO extends CustomerStockTradingAccountEntity {
	private static final long serialVersionUID = 1L;

}
