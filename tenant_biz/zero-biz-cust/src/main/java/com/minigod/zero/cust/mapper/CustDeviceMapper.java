package com.minigod.zero.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.cust.entity.CustDeviceEntity;

/**
 * 客户设备信息 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
public interface CustDeviceMapper extends BaseMapper<CustDeviceEntity> {


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

}
