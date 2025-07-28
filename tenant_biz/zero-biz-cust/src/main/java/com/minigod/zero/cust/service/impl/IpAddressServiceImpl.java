package com.minigod.zero.cust.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.biz.common.CommonParams;
import com.minigod.zero.biz.common.constant.OpenApiConstant;
import com.minigod.zero.biz.common.utils.DateUtil;
import com.minigod.zero.biz.common.utils.RestTemplateUtil;
import com.minigod.zero.cust.service.IIpAddressService;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.redis.lock.LockType;
import com.minigod.zero.core.redis.lock.RedisLockClient;
import com.minigod.zero.cust.entity.IpAddress;
import com.minigod.zero.cust.mapper.IpAddressMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class IpAddressServiceImpl extends BaseServiceImpl<IpAddressMapper, IpAddress> implements IIpAddressService {

	/**
	 * 等待时长
	 */
	private static final int WAIT_SECONDS = 10;
	/**
	 * 失效时间
	 */
	private static final int LEASE_SECONDS = 10;

	@Resource
	private ZeroRedis zeroRedis;
	@Resource
	private RedisLockClient redisLockClient;
	@Resource
	private RestTemplateUtil restTemplateUtil;

	@Override
	public IpAddress findIpAddress(String ip) {
		IpAddress ipAddress = null;
		try {
			ipAddress = zeroRedis.protoGet(ip, IpAddress.class);
			if (ipAddress == null) {
				if (redisLockClient.tryLock(CommonParams.IP_CONFIGURATION_SERVER + ip, LockType.FAIR, WAIT_SECONDS, LEASE_SECONDS, TimeUnit.SECONDS)) {
					ipAddress = zeroRedis.protoGet(ip, IpAddress.class);
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
								zeroRedis.protoSet(ip, ipAddress);
							} else {
								log.warn("未获取到客户真实IP地址");
							}
						} else {
							//将数据存入缓存
							zeroRedis.protoSet(ip, ipAddress);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("getIpAddressInfoByIp error", e);
		} finally {
			redisLockClient.unLock(CommonParams.IP_CONFIGURATION_SERVER + ip, LockType.FAIR);
		}

		return ipAddress;
	}

	private IpAddress getLocalAddressInfoByIp(String ip){
		LambdaQueryWrapper<IpAddress> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(IpAddress::getIp, ip).last("limit 1");
		return baseMapper.selectOne(queryWrapper);
	}

	private IpAddress getAddressInfoByIp(String ip){
		HashMap<String, Object> params = new HashMap<>();
		params.put("ip", ip);
		return restTemplateUtil.postSend(OpenApiConstant.GET_IP_ADDRESS, IpAddress.class, params);
	}

	public int insertIpAddress(IpAddress ipAddress) {
		ipAddress.setCreateTime(DateUtil.getNowDate());
		ipAddress.setUpdateUser(CommonParams.ADMIN_ACCOUNT_ID);
		ipAddress.setCreateUser(CommonParams.ADMIN_ACCOUNT_ID);
		return baseMapper.insert(ipAddress);
	}

}
