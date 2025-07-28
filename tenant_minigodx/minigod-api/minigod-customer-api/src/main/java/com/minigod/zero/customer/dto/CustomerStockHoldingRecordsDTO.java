package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerStockHoldingRecordsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户股票持仓表 数据传输对象实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerStockHoldingRecordsDTO extends CustomerStockHoldingRecordsEntity {
	private static final long serialVersionUID = 1L;

}
