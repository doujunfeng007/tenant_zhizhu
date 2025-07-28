package com.minigod.zero.cust.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.cust.cache.CustInfo;
import com.minigod.zero.cust.entity.CustDeviceEntity;
import com.minigod.zero.cust.entity.CustInfoEntity;

/**
 * @author 李长春
 * @email cloud@bdie.com.cn
 * @WeChat 138264386257
 * @date 2023/3/28
 */
public interface ICustLoginService extends IService<CustInfoEntity> {

	/**
	 * 用户信息
	 * @param custId
	 * @param custDevice 设备信息
	 * @return
	 */
	CustInfo getCustInfo(Long custId, CustDeviceEntity custDevice, String sourceType, Integer loginType);

}
