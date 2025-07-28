package com.minigod.zero.customer.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.customer.api.constatns.OpenApiConstant;
import com.minigod.zero.customer.api.service.IIpAddressService;
import com.minigod.zero.customer.entity.CustomerIpAddress;
import com.minigod.zero.customer.mapper.CustomerIpAddressMapper;
import com.minigod.zero.customer.utils.RestTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Slf4j
@Service
public class IpAddressServiceImpl implements IIpAddressService {

	public static final String IP_CONFIGURATION_SERVER = "IP_CONFIGURATION:SERVER:";

	@Resource
	private ZeroRedis miniGodRedis;

	@Resource
	private CustomerIpAddressMapper customerIpAddressMapper;
	@Resource
	private RestTemplateUtil restTemplateUtil;

	@Override
	public CustomerIpAddress findIpAddress(String ip) {
		if (ip == null) {
			log.warn("解析用户IP地址失败,IP为空!");
			return null;
		}
		CustomerIpAddress ipAddress = null;
		try {
			ipAddress = miniGodRedis.protoGet(ip, CustomerIpAddress.class);
			if (ipAddress == null) {
				if (miniGodRedis.incrBy(IP_CONFIGURATION_SERVER + ip, 1) == 1) {
					ipAddress = miniGodRedis.protoGet(ip, CustomerIpAddress.class);
					if (ipAddress == null) {
						ipAddress = this.getLocalAddressInfoByIp(ip);
						if (ipAddress == null) {
							//没有则获取ip的实际地址
							ipAddress = getAddressInfoByIp(ip);
							if (ipAddress != null) {
								//将数据存入数据库
								if (null == this.getLocalAddressInfoByIp(ip)) {
									insertIpAddress(ipAddress);
								}
								//将数据存入缓存
								miniGodRedis.protoSet(ip, ipAddress);
							} else {
								log.warn("未获取到客户真实IP地址");
							}
						} else {
							//将数据存入缓存
							miniGodRedis.protoSet(ip, ipAddress);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("getIpAddressInfoByIp error", e);
		} finally {
			miniGodRedis.del(IP_CONFIGURATION_SERVER + ip);
		}

		return ipAddress;
	}

	private CustomerIpAddress getLocalAddressInfoByIp(String ip) {
		LambdaQueryWrapper<CustomerIpAddress> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CustomerIpAddress::getIp, ip).last("limit 1");
		return customerIpAddressMapper.selectOne(queryWrapper);
	}

	private CustomerIpAddress getAddressInfoByIp(String ip) {
		return new CustomerIpAddress();
	}

	public int insertIpAddress(CustomerIpAddress ipAddress) {
		ipAddress.setCreateTime(DateUtil.now());
		return customerIpAddressMapper.insert(ipAddress);
	}

}
