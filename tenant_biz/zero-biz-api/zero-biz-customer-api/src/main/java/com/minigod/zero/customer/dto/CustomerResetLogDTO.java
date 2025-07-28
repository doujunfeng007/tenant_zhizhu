package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerResetLogEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户密码重置日志 数据传输对象实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerResetLogDTO extends CustomerResetLogEntity {
	private static final long serialVersionUID = 1L;

}
