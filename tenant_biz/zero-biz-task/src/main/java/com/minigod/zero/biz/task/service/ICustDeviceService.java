package com.minigod.zero.biz.task.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.cust.entity.CustDeviceEntity;


/**
 * 客户设备信息 服务类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
public interface ICustDeviceService extends BaseService<CustDeviceEntity> {



	/**
	 * 根据客户id获取设备信息
	 * @param custId
	 * @return
	 */
    CustDeviceEntity getCustDevice(Long custId);


}
