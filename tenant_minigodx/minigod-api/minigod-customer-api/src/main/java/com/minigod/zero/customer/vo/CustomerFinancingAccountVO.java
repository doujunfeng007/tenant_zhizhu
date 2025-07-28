package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.CustomerFinancingAccountEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户理财账户表 视图实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerFinancingAccountVO extends CustomerFinancingAccountEntity {
	private static final long serialVersionUID = 1L;

}
