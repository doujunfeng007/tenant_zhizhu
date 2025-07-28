package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerFundHisHoldingRecordsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户基金历史持仓表 数据传输对象实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerFundHisHoldingRecordsDTO extends CustomerFundHisHoldingRecordsEntity {
	private static final long serialVersionUID = 1L;

}
