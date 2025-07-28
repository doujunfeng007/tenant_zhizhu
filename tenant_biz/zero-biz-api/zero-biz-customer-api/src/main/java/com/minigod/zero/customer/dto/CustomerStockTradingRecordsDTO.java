package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerStockTradingRecordsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户交易流水汇总表 数据传输对象实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerStockTradingRecordsDTO extends CustomerStockTradingRecordsEntity {
	private static final long serialVersionUID = 1L;

}
