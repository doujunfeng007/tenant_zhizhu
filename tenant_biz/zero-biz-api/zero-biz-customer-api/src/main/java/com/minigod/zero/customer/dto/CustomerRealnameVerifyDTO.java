package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerRealnameVerifyEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户实名认证表 数据传输对象实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerRealnameVerifyDTO extends CustomerRealnameVerifyEntity {
	private static final long serialVersionUID = 1L;

}
