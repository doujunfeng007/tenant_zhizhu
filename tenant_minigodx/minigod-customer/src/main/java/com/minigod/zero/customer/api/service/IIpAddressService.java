package com.minigod.zero.customer.api.service;

import com.minigod.zero.customer.entity.CustomerIpAddress;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/21 12:00
 * @description：
 */
public interface IIpAddressService {
	CustomerIpAddress findIpAddress(String ip);
}
