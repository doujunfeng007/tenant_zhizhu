package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerLoginLogEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登陆日志表 数据传输对象实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerLoginLogDTO extends CustomerLoginLogEntity {
	private static final long serialVersionUID = 1L;

}
