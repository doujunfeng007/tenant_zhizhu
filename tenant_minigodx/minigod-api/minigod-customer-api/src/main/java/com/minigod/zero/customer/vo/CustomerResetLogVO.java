package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.CustomerResetLogEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户密码重置日志 视图实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerResetLogVO extends CustomerResetLogEntity {
	private static final long serialVersionUID = 1L;

}
