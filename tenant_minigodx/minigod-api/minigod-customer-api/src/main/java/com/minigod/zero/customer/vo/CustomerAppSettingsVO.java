package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.CustomerAppSettingsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户app设置信息表 视图实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerAppSettingsVO extends CustomerAppSettingsEntity {
	private static final long serialVersionUID = 1L;

}
