package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.CustomerFundTradingRecordsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户交易流水汇总表 视图实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerFundTradingRecordsVO extends CustomerFundTradingRecordsEntity {
	private static final long serialVersionUID = 1L;

}
