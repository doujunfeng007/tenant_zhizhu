package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.CustomerCardInfoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户银行卡记录 视图实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerCardInfoVO extends CustomerCardInfoEntity {
	private static final long serialVersionUID = 1L;

}
