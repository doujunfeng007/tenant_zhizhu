package com.minigod.zero.cust.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.biz.common.constant.OpenApiConstant;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.biz.common.enums.CustLoginEnum;
import com.minigod.zero.biz.common.enums.StockCommon;
import com.minigod.zero.biz.common.utils.RestTemplateUtil;
import com.minigod.zero.cust.service.ICustDeviceService;
import com.minigod.zero.cust.service.ICustLoginLogService;
import com.minigod.zero.cust.service.IIpAddressService;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.redis.lock.LockType;
import com.minigod.zero.core.redis.lock.RedisLockClient;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.apivo.params.DeviceInfo;
import com.minigod.zero.cust.cache.CustInfo;
import com.minigod.zero.cust.entity.CustDeviceEntity;
import com.minigod.zero.cust.entity.CustLoginLogEntity;
import com.minigod.zero.cust.entity.IpAddress;
import com.minigod.zero.cust.mapper.CustDeviceMapper;
import com.minigod.zero.cust.mapper.CustInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 客户设备信息 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Service
public class CustDeviceServiceImpl extends BaseServiceImpl<CustDeviceMapper, CustDeviceEntity> implements ICustDeviceService {

	@Resource
	private ICustLoginLogService custLoginLogService;

	@Resource
	private RestTemplateUtil restTemplateUtil;

	@Resource
	private CustInfoMapper custInfoMapper;

	@Resource
	private IIpAddressService assetInfoService;

	@Resource
	private RedisLockClient redisLockClient;

	@Value("${zhizhu.oauth.tenantId}")
	public String tenantId; //租户ID

	/**
	 * 等待时长
	 */
	private static final int WAIT_SECONDS = 10;
	/**
	 * 失效时间
	 */
	private static final int LEASE_SECONDS = 10;

	@Override
	public List<CustDeviceEntity> getDeviceList(List<Long> userIds) {
		return new LambdaQueryChainWrapper<>(baseMapper)
			.in(CustDeviceEntity::getCustId, userIds)
			.eq(CustDeviceEntity::getStatus, 1)
			.eq(CustDeviceEntity::getIsDeleted, 0)
			.list();
	}

	@Override
	public boolean custDeviceReport(CustDeviceEntity custDeviceEntity) {
		// 设备信息
		Long custId = AuthUtil.getCustId();// 未登录将默认为游客号

		if (StringUtils.isBlank(custDeviceEntity.getDeviceCode())){
			return false;
		}

		String uniKey = custId + custDeviceEntity.getDeviceCode();
		try {
			if (redisLockClient.tryLock(uniKey, LockType.FAIR, WAIT_SECONDS, LEASE_SECONDS, TimeUnit.SECONDS)) {
				custDeviceEntity.setLang(WebUtil.getHeader(TokenConstant.LANGUAGE));// 当前设备语言
				// 查询设备记录
				CustDeviceEntity custDevice = baseMapper.selectOne(Wrappers.<CustDeviceEntity>lambdaQuery()
					.eq(CustDeviceEntity::getCustId, custId)
					.eq(CustDeviceEntity::getDeviceCode, custDeviceEntity.getDeviceCode()));
				// 无记录则新增记录客户号、设备信息、极光ID、语言
				if (custDevice == null) {
					custDeviceEntity.setCustId(custId);
					custDeviceEntity.setStatus(1);
					custDeviceEntity.setIsDeleted(0);
					custDeviceEntity.setCreateTime(new Date());
					custDeviceEntity.setUpdateTime(new Date());
					baseMapper.insert(custDeviceEntity);
					return true;
				}
				if (StringUtils.isBlank(custDevice.getOpenCode())) {
					custDevice.setOpenCode(custDevice.getOpenCode());
				}
				// 非游客
				if (!custId.equals(AuthUtil.GUEST_CUST_ID)) {
					custDeviceEntity.setStatus(1);// 更新为当前设备
					custDeviceEntity.setIsDeleted(0);// 加入可信设备列表
					custDeviceEntity.setUpdateTime(new Date());
					baseMapper.update(custDeviceEntity, Wrappers.<CustDeviceEntity>update().lambda().in(CustDeviceEntity::getId, custDevice.getId()));
					// 更新客户其他设备为历史设备
					baseMapper.updateDeviceStatus(custId, custDeviceEntity.getDeviceCode());

					// 最新的客户登录或异地访问的登入类型的地区变化，做行情权限刷新，和记录登录IP变化
					CustLoginLogEntity lastestCustLoginLog = custLoginLogService.findLastestCustLoginLog(custId, List.of(CustLoginEnum.type.LOGIN.getCode(), CustLoginEnum.type.IP_OFFSITE.getCode()), 1);
					String lastestIp = lastestCustLoginLog.getIp();
					String nowIp = WebUtil.getIP();
					if (lastestCustLoginLog != null && StringUtils.isNotBlank(lastestIp) && !lastestCustLoginLog.getIp().equals(nowIp)) {
						String clientId = AuthUtil.getClientId();
						String deviceCode = AuthUtil.getDeviceCode();
						String osType = WebUtil.getHeader(CommonConstant.OS_TYPE);
						String deviceModel = custDeviceEntity.getDeviceModel();
						String appVersion = custDeviceEntity.getAppVersion();
						String sourceType = WebUtil.getHeader(TokenConstant.SOURCE_TYPE);
						this.handelSwitchQuotAndCustLoginLog(nowIp, lastestCustLoginLog, custId, clientId, deviceCode, osType, deviceModel, appVersion, sourceType);
					}
				} else {
					// 游客或当前设备，更新下设备的极光ID、时间即可
					custDeviceEntity.setUpdateTime(new Date());
					baseMapper.update(custDeviceEntity, Wrappers.<CustDeviceEntity>update().lambda().in(CustDeviceEntity::getId, custDevice.getId()));
				}
				return true;
			}
		} catch (Exception e) {
			log.error("设备上报异常", e);
		} finally {
			redisLockClient.unLock(uniKey, LockType.FAIR);
		}
		return false;
	}

	public void handelSwitchQuotAndCustLoginLog(String nowIp, CustLoginLogEntity lastestCustLoginLog, Long custId, String clientId, String deviceCode, String osType, String deviceModel, String appVersion, String sourceType) {
		new Thread(() -> {
			IpAddress nowIpAddress = assetInfoService.findIpAddress(nowIp);
			if (nowIpAddress != null && StringUtils.isNotBlank(lastestCustLoginLog.getRegionCode())
				&& !nowIpAddress.getRegionCode().equals(lastestCustLoginLog.getRegionCode())) {

				// 客户基本信息
				CustInfo custInfo = custInfoMapper.getCustInfo(custId);

				CustLoginLogEntity custLoginLog = BeanUtil.copy(lastestCustLoginLog, CustLoginLogEntity.class);
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

				if (StringUtils.isNotBlank(custInfo.getCellPhone())) {
					custLoginLog.setPhoneNumber(custInfo.getCellPhone());
					custLoginLog.setAreaCode(custInfo.getAreaCode());
				}

				if (nowIpAddress != null) {
					custLoginLog.setRegionCnName(nowIpAddress.getRegionCnName());
					custLoginLog.setRegionCode(nowIpAddress.getRegionCode());
					custLoginLog.setCountryCnName(nowIpAddress.getCountryCnName());
					custLoginLog.setCountryCode(nowIpAddress.getCountryCode());
				}
				custLoginLogService.save(custLoginLog);

				HashMap<String, Object> params = new HashMap<>();
				params.put("custId", custId);
				params.put("tenantId", tenantId);
				params.put("clientId", clientId);
				params.put("deviceCode", deviceCode);
				params.put("terminalId", StockCommon.varietiesDeviceEnum.APP.getCode());
				params.put("ip", nowIp);
				restTemplateUtil.postSend(OpenApiConstant.SWITCH_QUOT_LEVEL, Boolean.class, params);
			}
		}).start();
	}

	@Override
	public CustDeviceEntity getCustDevice(Long custId) {
		List<CustDeviceEntity> deviceList = baseMapper.selectList(Wrappers.<CustDeviceEntity>lambdaQuery()
			.eq(CustDeviceEntity::getCustId, custId).eq(CustDeviceEntity::getStatus, 1)
			.eq(CustDeviceEntity::getIsDeleted, 0).orderByDesc(CustDeviceEntity::getUpdateTime));
		if (CollectionUtils.isNotEmpty(deviceList)) {
			return deviceList.get(0);
		}
		return new CustDeviceEntity();
	}

	@Override
	public CustDeviceEntity getByDeviceCode(String deviceCode) {
		return baseMapper.selectOne(Wrappers.<CustDeviceEntity>lambdaQuery()
			.eq(CustDeviceEntity::getDeviceCode, deviceCode)
			.eq(BaseEntity::getIsDeleted, 0)
			.orderByDesc(BaseEntity::getCreateTime)
			.last("limit 1"));
	}

	private CustDeviceEntity toUserDevice(DeviceInfo deviceInfo, Long userId, String deviceCode, CustDeviceEntity userDevice, boolean isGuest) {
		userDevice.setCustId(userId);
		userDevice.setDeviceType(deviceInfo.getDeviceType());
		userDevice.setDeviceModel(deviceInfo.getDeviceModel());
		userDevice.setOsType(deviceInfo.getOsType());
		userDevice.setOsVersion(deviceInfo.getOsVersion());
		userDevice.setAppVersion(deviceInfo.getAppVersion());
		userDevice.setDeviceName(deviceInfo.getDeviceName());
		userDevice.setDeviceCode(deviceCode);
		userDevice.setChannel(deviceInfo.getChannel());
		userDevice.setStatus(CommonEnums.StatusEnum.YES.getCode());
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
	public List<CustDeviceEntity> getAllDeviceLangList(List<Long> custIds) {
		LambdaQueryWrapper<CustDeviceEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.select(CustDeviceEntity::getLang, CustDeviceEntity::getCustId);
		queryWrapper.eq(CustDeviceEntity::getStatus, CommonEnums.StatusEnum.YES.getCode());
		queryWrapper.eq(CustDeviceEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode());

		if (CollectionUtil.isNotEmpty(custIds)) {
			queryWrapper.in(CustDeviceEntity::getCustId, custIds);
		}
		return getBaseMapper().selectList(queryWrapper);
	}
}
