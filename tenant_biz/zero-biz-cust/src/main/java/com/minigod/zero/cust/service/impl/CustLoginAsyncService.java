package com.minigod.zero.cust.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.biz.common.enums.CustLoginEnum;
import com.minigod.zero.cust.service.IIpAddressService;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.cust.cache.CustInfo;
import com.minigod.zero.cust.entity.CustDeviceEntity;
import com.minigod.zero.cust.entity.CustLoginLogEntity;
import com.minigod.zero.cust.mapper.CustDeviceMapper;
import com.minigod.zero.cust.mapper.CustLoginLogMapper;
import com.minigod.zero.cust.entity.IpAddress;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Zhe.Xiao
 */
@Slf4j
@Service
public class CustLoginAsyncService {

	@Resource
	private CustDeviceMapper custDeviceMapper;

	@Resource
	private CustLoginLogMapper custLoginLogMapper;

	@Resource
	private IIpAddressService ipAddressService;

	@Async("asyncExecutor")
	public void recordLoginInfo(Long custId, CustDeviceEntity custDevice) {
		// 查询当前客户其他有效设备
		CustDeviceEntity deviceEntity = custDeviceMapper.selectOne(Wrappers.<CustDeviceEntity>lambdaQuery()
			.eq(CustDeviceEntity::getCustId, custId)
			.eq(CustDeviceEntity::getDeviceCode, custDevice.getDeviceCode()));
		custDevice.setCustId(custId);
		custDevice.setStatus(1); // 更新为当前设备
		custDevice.setIsDeleted(0);// 可信设备
		custDevice.setUpdateTime(new Date());
		if (deviceEntity == null) {
			// 不存在则新增记录
			custDevice.setCreateTime(new Date());
			custDeviceMapper.insert(custDevice);
		} else {
			// 如果存在，更新设备表
			if (StringUtils.isBlank(custDevice.getOpenCode())) {
				custDevice.setOpenCode(deviceEntity.getOpenCode());
			}
			custDeviceMapper.update(custDevice, Wrappers.<CustDeviceEntity>update().lambda().in(CustDeviceEntity::getId, deviceEntity.getId()));
			// 更新客户其他设备为历史设备
			custDeviceMapper.updateDeviceStatus(custId, custDevice.getDeviceCode());
		}
		// 删除游客绑定记录
		custDeviceMapper.removeDeviceInfo(AuthUtil.GUEST_CUST_ID, custDevice.getDeviceCode());
	}

	@Async("asyncExecutor")
	public void saveLoginLog(Long custId, CustDeviceEntity custDevice, CustInfo custInfo, String sourceType, String ip, Integer loginType) {
		IpAddress nowIpAddress = ipAddressService.findIpAddress(ip);

		CustLoginLogEntity custLoginLog = new CustLoginLogEntity();
		custLoginLog.setCustId(custId);
		custLoginLog.setAction(CustLoginEnum.action.IN.getCode());
		custLoginLog.setType(CustLoginEnum.type.LOGIN.getCode());
		custLoginLog.setIp(ip);
		custLoginLog.setLoginType(loginType);
		custLoginLog.setDeviceCode(custDevice.getDeviceCode());
		custLoginLog.setAppVersion(custDevice.getAppVersion());
		custLoginLog.setDeviceModel(custDevice.getDeviceModel());
		custLoginLog.setOsType(custDevice.getOsType());
		custLoginLog.setSourceType(sourceType);
		custLoginLog.setCreateTime(new Date());
		if (nowIpAddress != null) {
			custLoginLog.setRegionCnName(nowIpAddress.getRegionCnName());
			custLoginLog.setRegionCode(nowIpAddress.getRegionCode());
			custLoginLog.setCountryCnName(nowIpAddress.getCountryCnName());
			custLoginLog.setCountryCode(nowIpAddress.getCountryCode());
		}

		if (custInfo != null) {
			custLoginLog.setPhoneNumber(custInfo.getCellPhone());
			custLoginLog.setAreaCode(custInfo.getAreaCode());
			custLoginLog.setEmail(custInfo.getCellEmail());
			// TODO 真实姓名从开户信息中取
			custLoginLog.setCustName(custInfo.getNickName());
		}

		custLoginLogMapper.insert(custLoginLog);
	}

}
