package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.CustomerStockHoldingRecordsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户股票持仓表 视图实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerStockHoldingRecordsVO extends CustomerStockHoldingRecordsEntity {
	private static final long serialVersionUID = 1L;

}
