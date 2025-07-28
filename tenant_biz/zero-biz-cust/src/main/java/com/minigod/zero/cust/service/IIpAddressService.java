package com.minigod.zero.cust.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.cust.entity.IpAddress;

/**
 * ip的真实地址信息Service接口
 *
 * @author bpmx
 * @date 2021-12-24
 */
public interface IIpAddressService extends BaseService<IpAddress> {

	/**
	 * 查询地址
	 * @param ip
	 * @return
	 */
	public IpAddress findIpAddress(String ip);
}
