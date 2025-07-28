package com.minigod.zero.cust.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.cust.entity.CustDeviceEntity;

import java.util.List;

/**
 * 客户设备信息 服务类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
public interface ICustDeviceService extends BaseService<CustDeviceEntity> {

	/**
	 * 根据客户id批量获取设备信息
	 * @param custIds
	 */
	List<CustDeviceEntity> getDeviceList(List<Long> custIds);

	/**
	 * 上传设备信息
	 * @param deviceInfo
	 * @return
	 */
	boolean custDeviceReport(CustDeviceEntity deviceInfo);

	/**
	 * 根据客户id获取设备信息
	 * @param custId
	 * @return
	 */
    CustDeviceEntity getCustDevice(Long custId);

	/**
	 * 查询设备信息
	 * @param deviceCode
	 * @return
	 */
	CustDeviceEntity getByDeviceCode(String deviceCode);

	/**
	 * 根据客户id批量获取设备语言
	 * @param custIds
	 * @return
	 */
	List<CustDeviceEntity> getAllDeviceLangList(List<Long> custIds);

}
