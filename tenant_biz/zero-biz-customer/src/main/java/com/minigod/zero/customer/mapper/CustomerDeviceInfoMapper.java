/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerDeviceInfoEntity;
import com.minigod.zero.customer.vo.CustomerDeviceInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户设备信息 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerDeviceInfoMapper extends BaseMapper<CustomerDeviceInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param customerdeviceInfo
	 * @return
	 */
	List<CustomerDeviceInfoVO> selectCustomerDeviceInfoPage(IPage page, CustomerDeviceInfoVO customerdeviceInfo);

	/**
	 * 将设备置为历史设备
	 *
	 * @param custId
	 * @param deviceCode
	 * @return
	 */
	int updateDeviceStatus(Long custId, String deviceCode);


	/**
	 * 删除设备记录
	 * @param custId
	 * @param deviceCode
	 * @return
	 */
	int removeDeviceInfo(Long custId, String deviceCode);


	CustomerDeviceInfoEntity selectByCustId(Long custId);

	CustomerDeviceInfoEntity getByDeviceCode(String deviceCode);
}
