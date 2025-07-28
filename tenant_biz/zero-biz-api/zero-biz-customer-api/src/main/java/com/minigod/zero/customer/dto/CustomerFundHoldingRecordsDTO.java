package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerFundHoldingRecordsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户基金持仓表 数据传输对象实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerFundHoldingRecordsDTO extends CustomerFundHoldingRecordsEntity {
	private static final long serialVersionUID = 1L;

}
