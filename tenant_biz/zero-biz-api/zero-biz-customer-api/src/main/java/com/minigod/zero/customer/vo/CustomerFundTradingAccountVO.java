package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.CustomerFundTradingAccountEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基金交易账户信息表 视图实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerFundTradingAccountVO extends CustomerFundTradingAccountEntity {
	private static final long serialVersionUID = 1L;

}
