package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerFundTradingAccountEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基金交易账户信息表 数据传输对象实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerFundTradingAccountDTO extends CustomerFundTradingAccountEntity {
	private static final long serialVersionUID = 1L;

}
