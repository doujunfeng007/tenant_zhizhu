package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.CustomerStockAssetRecordsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户资产流水汇总表 视图实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerStockAssetRecordsVO extends CustomerStockAssetRecordsEntity {
	private static final long serialVersionUID = 1L;

}
