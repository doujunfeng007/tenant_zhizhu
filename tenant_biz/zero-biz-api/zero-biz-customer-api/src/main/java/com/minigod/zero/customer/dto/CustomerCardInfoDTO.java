package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerCardInfoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户银行卡记录 数据传输对象实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerCardInfoDTO extends CustomerCardInfoEntity {
	private static final long serialVersionUID = 1L;

}
