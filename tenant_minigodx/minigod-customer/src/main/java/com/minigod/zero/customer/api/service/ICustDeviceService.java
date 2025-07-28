package com.minigod.zero.customer.api.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.customer.entity.CustomerDeviceInfoEntity;

import java.util.List;

/**
 * 客户设备信息 服务类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
public interface ICustDeviceService extends BaseService<CustomerDeviceInfoEntity> {

	/**
	 * 根据客户id批量获取设备信息
	 * @param custIds
	 */
	List<CustomerDeviceInfoEntity> getDeviceList(List<Long> custIds);

	/**
	 * 获取所有上传设备信息用户id
	 * @return
	 */
	List<Long> getAllUserDeviceIdList();

	/**
	 * 客户邮箱
	 * @param userIds
	 * @return
	 */
	String customerEmailList(List<Long> userIds);

	/**
	 * 上传设备信息
	 * @param deviceInfo
	 * @return
	 */
	boolean custDeviceReport(CustomerDeviceInfoEntity deviceInfo);

	/**
	 * 根据客户id获取设备信息
	 * @param custId
	 * @return
	 */
	CustomerDeviceInfoEntity getCustDevice(Long custId);

	/**
	 * 查询设备信息
	 * @param deviceCode
	 * @return
	 */
	CustomerDeviceInfoEntity getByDeviceCode(String deviceCode);


	CustomerDeviceInfoEntity getByDeviceCodeAndCustId(Long custId,String deviceCode);
	/**
	 * 根据客户id批量获取设备语言
	 * @param custIds
	 * @return
	 */
	List<CustomerDeviceInfoEntity> getAllDeviceLangList(List<Long> custIds);

}
