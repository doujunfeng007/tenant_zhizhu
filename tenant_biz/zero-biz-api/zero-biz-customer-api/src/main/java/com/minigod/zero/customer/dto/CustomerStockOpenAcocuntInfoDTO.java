package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerStockOpenAccountInfoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户股票开户资料 数据传输对象实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerStockOpenAcocuntInfoDTO extends CustomerStockOpenAccountInfoEntity {
	private static final long serialVersionUID = 1L;

}
