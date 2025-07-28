package com.minigod.zero.cust.dto;

import com.minigod.zero.cust.entity.CustDeviceEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户设备信息 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustDeviceDTO extends CustDeviceEntity {
	private static final long serialVersionUID = 1L;

}
