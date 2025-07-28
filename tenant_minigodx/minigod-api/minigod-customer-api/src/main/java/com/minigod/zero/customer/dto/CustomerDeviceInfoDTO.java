package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerDeviceInfoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户设备信息 数据传输对象实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerDeviceInfoDTO extends CustomerDeviceInfoEntity {
	private static final long serialVersionUID = 1L;

}
