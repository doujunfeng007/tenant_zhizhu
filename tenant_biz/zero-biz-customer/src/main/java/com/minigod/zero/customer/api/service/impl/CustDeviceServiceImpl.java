package com.minigod.zero.customer.api.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.minigod.zero.biz.common.constant.AccountMessageConstant;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.ZeroUser;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.customer.api.service.ICustDeviceService;
import com.minigod.zero.customer.api.service.IIpAddressService;
import com.minigod.zero.customer.dto.DeviceInfoDTO;
import com.minigod.zero.customer.entity.CustomerDeviceInfoEntity;
import com.minigod.zero.customer.entity.CustomerInfoEntity;
import com.minigod.zero.customer.entity.CustomerIpAddress;
import com.minigod.zero.customer.entity.CustomerLoginLogEntity;
import com.minigod.zero.customer.enums.CustLoginEnum;
import com.minigod.zero.customer.enums.StatusEnum;
import com.minigod.zero.customer.mapper.CustomerBasicInfoMapper;
import com.minigod.zero.customer.mapper.CustomerDeviceInfoMapper;
import com.minigod.zero.customer.mapper.CustomerInfoMapper;
import com.minigod.zero.customer.mapper.CustomerLoginLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户设备信息 服务实现类
 * @since 2023-02-08
 */
@Service
public class CustDeviceServiceImpl extends BaseServiceImpl<CustomerDeviceInfoMapper, CustomerDeviceInfoEntity> implements ICustDeviceService {

	@Resource
	private ZeroRedis miniGodRedis;
	@Autowired
	private CustomerInfoMapper customerInfoMapper;

	@Autowired
	private CustomerBasicInfoMapper customerBasicInfoMapper;

	@Autowired
	private CustomerLoginLogMapper customerLoginLogMapper;

	@Autowired
	private IIpAddressService ipAddressService;
	/**
	 * 等待时长
	 */
	private static final int WAIT_SECONDS = 10;
	/**
	 * 失效时间
	 */
	private static final int LEASE_SECONDS = 10;

	@Override
	public List<CustomerDeviceInfoEntity> getDeviceList(List<Long> userIds) {
		return new LambdaQueryChainWrapper<>(baseMapper)
			.in(CustomerDeviceInfoEntity::getCustId, userIds)
			.eq(CustomerDeviceInfoEntity::getStatus, 1)
			.eq(CustomerDeviceInfoEntity::getIsDeleted, 0)
			.list();
	}

	@Override
	public List<Long> getAllUserDeviceIdList() {
		List<CustomerDeviceInfoEntity> list = new LambdaQueryChainWrapper<>(baseMapper)
			.eq(CustomerDeviceInfoEntity::getStatus, 1)
			.eq(CustomerDeviceInfoEntity::getIsDeleted, 0)
			.list();
		return list.stream().map(devoceInfo->{
			return devoceInfo.getCustId();
		}).collect(Collectors.toList());
	}

	@Override
	public String customerEmailList(List<Long> userIds) {
		if (CollectionUtil.isEmpty(userIds)){
			throw new ZeroException(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_GET_EMAIL_FAIL_PARAM_USER_ID_NULL_NOTICE));
		}
		List<String> list = customerBasicInfoMapper.getUserEmails(userIds);
		if (CollectionUtil.isEmpty(list)){
			return "";
		}
		return list.stream().collect(Collectors.joining(","));
	}

	@Override
	public boolean custDeviceReport(CustomerDeviceInfoEntity customerDeviceInfoEntity) {
		// 未登录将默认为游客号
		ZeroUser user = AuthUtil.getTenantUser();
		Long custId = 1000000L;
		String tenantId ="000000";
		if (user != null){
			custId = user.getUserId();
			tenantId = user.getTenantId();
		}
		if (StringUtils.isBlank(customerDeviceInfoEntity.getDeviceCode())){
			return false;
		}

		String uniKey = custId + customerDeviceInfoEntity.getDeviceCode();
		try {
			if (miniGodRedis.incrBy(uniKey,1) == 1) {
				customerDeviceInfoEntity.setLang(WebUtil.getHeader("Accept-Language"));// 当前设备语言
				// 查询设备记录
				CustomerDeviceInfoEntity custDevice = baseMapper.selectOne(Wrappers.<CustomerDeviceInfoEntity>lambdaQuery()
					.eq(CustomerDeviceInfoEntity::getCustId, custId)
					.eq(CustomerDeviceInfoEntity::getDeviceCode, customerDeviceInfoEntity.getDeviceCode()));
				// 无记录则新增记录客户号、设备信息、极光ID、语言
				if (custDevice == null) {
					customerDeviceInfoEntity.setCustId(custId);
					customerDeviceInfoEntity.setStatus(1);
					customerDeviceInfoEntity.setIsDeleted(0);
					customerDeviceInfoEntity.setCreateTime(new Date());
					customerDeviceInfoEntity.setUpdateTime(new Date());
					customerDeviceInfoEntity.setTenantId(tenantId);
					baseMapper.insert(customerDeviceInfoEntity);
					return true;
				}
				if (StringUtils.isBlank(custDevice.getOpenCode())) {
					custDevice.setOpenCode(custDevice.getOpenCode());
				}
				// 非游客
				if (user != null) {
					customerDeviceInfoEntity.setStatus(1);// 更新为当前设备
					customerDeviceInfoEntity.setIsDeleted(0);// 加入可信设备列表
					customerDeviceInfoEntity.setUpdateTime(new Date());
					baseMapper.update(customerDeviceInfoEntity, Wrappers.<CustomerDeviceInfoEntity>update().lambda().in(CustomerDeviceInfoEntity::getId, custDevice.getId()));
					// 更新客户其他设备为历史设备
					baseMapper.updateDeviceStatus(custId, customerDeviceInfoEntity.getDeviceCode());

					CustomerLoginLogEntity lastestCustLoginLog = this.findLastestCustLoginLog(custId, Arrays.asList(CustLoginEnum.type.LOGIN.getCode(), CustLoginEnum.type.IP_OFFSITE.getCode()), 1);
					String nowIp = WebUtil.getIP();
					if (lastestCustLoginLog != null && !StringUtils.isEmpty(lastestCustLoginLog.getIp()) && !lastestCustLoginLog.getIp().equals(nowIp)) {
						String clientId = AuthUtil.getClientId();
						String deviceCode = AuthUtil.getDeviceCode();
						String osType = WebUtil.getHeader(CommonConstant.OS_TYPE);
						String deviceModel = customerDeviceInfoEntity.getDeviceModel();
						String appVersion = customerDeviceInfoEntity.getAppVersion();
						String sourceType = WebUtil.getHeader(TokenConstant.SOURCE_TYPE);
						this.handelSwitchQuotAndCustLoginLog(nowIp, lastestCustLoginLog, custId, clientId, deviceCode, osType, deviceModel, appVersion, sourceType);
					}
				} else {
					// 游客或当前设备，更新下设备的极光ID、时间即可
					customerDeviceInfoEntity.setUpdateTime(new Date());
					baseMapper.update(customerDeviceInfoEntity, Wrappers.<CustomerDeviceInfoEntity>update().lambda().in(CustomerDeviceInfoEntity::getId, custDevice.getId()));
				}
				return true;
			}
		} catch (Exception e) {
			log.error("设备上报异常", e);
		} finally {
			miniGodRedis.del(uniKey);
		}
		return false;
	}

	private CustomerLoginLogEntity findLastestCustLoginLog(Long custId, List<Integer> typeList, Integer action) {
		LambdaQueryWrapper<CustomerLoginLogEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CustomerLoginLogEntity::getCustId, custId);
		queryWrapper.in(CustomerLoginLogEntity::getType, typeList);
		queryWrapper.eq(CustomerLoginLogEntity::getAction, action);
		queryWrapper.orderByDesc(CustomerLoginLogEntity::getCreateTime);
		queryWrapper.last("limit 1");
		return customerLoginLogMapper.selectOne(queryWrapper);
	}

	@Override
	public CustomerDeviceInfoEntity getCustDevice(Long custId) {
		List<CustomerDeviceInfoEntity> deviceList = baseMapper.selectList(Wrappers.<CustomerDeviceInfoEntity>lambdaQuery()
			.eq(CustomerDeviceInfoEntity::getCustId, custId).eq(CustomerDeviceInfoEntity::getStatus, 1)
			.eq(CustomerDeviceInfoEntity::getIsDeleted, 0).orderByDesc(CustomerDeviceInfoEntity::getUpdateTime));
		if (CollectionUtils.isNotEmpty(deviceList)) {
			return deviceList.get(0);
		}
		return new CustomerDeviceInfoEntity();
	}

	@Override
	public CustomerDeviceInfoEntity getByDeviceCode(String deviceCode) {
		return baseMapper.selectOne(Wrappers.<CustomerDeviceInfoEntity>lambdaQuery()
			.eq(CustomerDeviceInfoEntity::getDeviceCode, deviceCode)
			.eq(BaseEntity::getIsDeleted, 0)
			.orderByDesc(BaseEntity::getCreateTime)
			.last("limit 1"));
	}

	@Override
	public CustomerDeviceInfoEntity getByDeviceCodeAndCustId(Long custId,String deviceCode) {
		return baseMapper.selectOne(Wrappers.<CustomerDeviceInfoEntity>lambdaQuery()
			.eq(CustomerDeviceInfoEntity::getDeviceCode, deviceCode)
			.eq(BaseEntity::getIsDeleted, 0)
			.eq(CustomerDeviceInfoEntity::getCustId, custId)
			.orderByDesc(BaseEntity::getCreateTime)
			.last("limit 1"));
	}

	private CustomerDeviceInfoEntity toUserDevice(DeviceInfoDTO deviceInfo, Long userId, String deviceCode, CustomerDeviceInfoEntity userDevice, boolean isGuest) {
		userDevice.setCustId(userId);
		userDevice.setDeviceType(deviceInfo.getDeviceType());
		userDevice.setDeviceModel(deviceInfo.getDeviceModel());
		userDevice.setOsType(deviceInfo.getOsType());
		userDevice.setOsVersion(deviceInfo.getOsVersion());
		userDevice.setAppVersion(deviceInfo.getAppVersion());
		userDevice.setDeviceName(deviceInfo.getDeviceName());
		userDevice.setDeviceCode(deviceCode);
		userDevice.setChannel(deviceInfo.getChannel());
		userDevice.setStatus(StatusEnum.YES.getCode());
		if (isGuest) {
			userDevice.setGuestFlag(0);
		} else {
			userDevice.setGuestFlag(1);
		}
		String strOpenCode = deviceInfo.getOpenCode();
		if (StringUtils.isNotBlank(strOpenCode)) {
			userDevice.setOpenCode(strOpenCode);
		}
		return userDevice;
	}

	@Override
	public List<CustomerDeviceInfoEntity> getAllDeviceLangList(List<Long> custIds) {
		LambdaQueryWrapper<CustomerDeviceInfoEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.select(CustomerDeviceInfoEntity::getLang, CustomerDeviceInfoEntity::getCustId);
		queryWrapper.eq(CustomerDeviceInfoEntity::getStatus,StatusEnum.YES.getCode());
		queryWrapper.eq(CustomerDeviceInfoEntity::getIsDeleted,StatusEnum.NO.getCode());

		if (CollectionUtil.isNotEmpty(custIds)) {
			queryWrapper.in(CustomerDeviceInfoEntity::getCustId, custIds);
		}
		return getBaseMapper().selectList(queryWrapper);
	}

	public void handelSwitchQuotAndCustLoginLog(String nowIp, CustomerLoginLogEntity lastestCustLoginLog, Long custId, String clientId, String deviceCode, String osType, String deviceModel, String appVersion, String sourceType) {
		new Thread(() -> {
			CustomerIpAddress nowIpAddress = ipAddressService.findIpAddress(nowIp);
			if (nowIpAddress != null && org.apache.commons.lang3.StringUtils.isNotBlank(lastestCustLoginLog.getRegionCode())
				&& !nowIpAddress.getRegionCode().equals(lastestCustLoginLog.getRegionCode())) {

				// 客户基本信息
				CustomerInfoEntity custInfo = customerInfoMapper.selectByCustId(custId);

				CustomerLoginLogEntity custLoginLog = BeanUtil.copy(lastestCustLoginLog, CustomerLoginLogEntity.class);
				custLoginLog.setId(null);
				custLoginLog.setCustId(custId);
				custLoginLog.setAction(CustLoginEnum.action.IN.getCode());
				custLoginLog.setType(CustLoginEnum.type.IP_OFFSITE.getCode());
				custLoginLog.setIp(nowIp);
				custLoginLog.setDeviceCode(deviceCode);
				custLoginLog.setAppVersion(appVersion);
				custLoginLog.setDeviceModel(deviceModel);
				custLoginLog.setOsType(Integer.parseInt(osType));
				custLoginLog.setSourceType(sourceType);
				custLoginLog.setEmail(custInfo.getCellEmail());
				custLoginLog.setCustName(custInfo.getNickName());
				custLoginLog.setCreateTime(new Date());

				if (org.apache.commons.lang3.StringUtils.isNotBlank(custInfo.getCellPhone())) {
					custLoginLog.setPhoneNumber(custInfo.getCellPhone());
					custLoginLog.setAreaCode(custInfo.getAreaCode());
				}

				if (nowIpAddress != null) {
					custLoginLog.setRegionCnName(nowIpAddress.getRegionCnName());
					custLoginLog.setRegionCode(nowIpAddress.getRegionCode());
					custLoginLog.setCountryCnName(nowIpAddress.getCountryCnName());
					custLoginLog.setCountryCode(nowIpAddress.getCountryCode());
				}
				customerLoginLogMapper.insert(custLoginLog);
			}
		}).start();
	}
}
