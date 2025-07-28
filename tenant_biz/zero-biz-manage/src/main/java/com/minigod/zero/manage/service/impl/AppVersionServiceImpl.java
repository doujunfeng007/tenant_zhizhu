package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.biz.common.utils.Util;
import com.minigod.zero.manage.entity.AppVersionEntity;
import com.minigod.zero.manage.mapper.AppVersionMapper;
import com.minigod.zero.manage.service.IAppVersionService;
import com.minigod.zero.manage.vo.request.AppVersionVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Date;
import java.util.List;

/**
 * APP版本信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-04-24
 */
@Service
public class AppVersionServiceImpl extends ServiceImpl<AppVersionMapper, AppVersionEntity> implements IAppVersionService {

	@Override
	public IPage<AppVersionVO> selectAppVersionPage(IPage<AppVersionVO> page, AppVersionVO appVersion) {
		return page.setRecords(baseMapper.selectAppVersionPage(page, appVersion));
	}

	@Override
	public void updateBeforeCheckCode(Integer appCode, Integer deviceType, Integer osType, String versionNo, Integer toAll, Integer checkCode, String tenantId) {
		List<AppVersionEntity> list = null;
		LambdaQueryWrapper<AppVersionEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper
			.eq(AppVersionEntity::getAppCode, appCode)
			.eq(AppVersionEntity::getOsType, osType)
			.eq(AppVersionEntity::getDeviceType, deviceType)
			.eq(AppVersionEntity::getTenantId, tenantId);

		list = baseMapper.selectList(queryWrapper);
		if (null != list && list.size() > 0) {
			if (null != toAll) {//修改所有小于currentVersionNo的版本的checkcode值
				for (AppVersionEntity appVersion : list) {
					if (Util.compareVersion(appVersion.getVersionNo(), versionNo) == -1) {//版本号小于当前版本号
						appVersion.setCheckCode(checkCode);
						appVersion.setUpdateTime(new Date());
						baseMapper.updateById(appVersion);
					}

				}
			} else {//只修改上一版本的checkcode值
				String max = getMaxVersion(list, versionNo);//小于当前版本号currentVersionNo的最大版本号
				if (StringUtils.isNotEmpty(max)) {
					AppVersionEntity maxAppVersion = findAppVersionByVersion(appCode, deviceType, osType, max, tenantId);
					if (maxAppVersion != null) {
						maxAppVersion.setCheckCode(checkCode);//将上一版本的状态改变为chechcode值
						maxAppVersion.setUpdateTime(new Date());
						baseMapper.updateById(maxAppVersion);
					}
				}

			}
		}
	}

	@Override
	public AppVersionEntity findAppVersionByVersion(Integer appcode, Integer deviceType, Integer osType, String versionNo, String tenantId) {
		LambdaQueryWrapper<AppVersionEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper
			.eq(AppVersionEntity::getAppCode, appcode)
			.eq(AppVersionEntity::getDeviceType, deviceType)
			.eq(AppVersionEntity::getOsType, osType)
			.eq(AppVersionEntity::getVersionNo, versionNo)
			.eq(AppVersionEntity::getTenantId, tenantId);
		return baseMapper.selectOne(queryWrapper);
	}

	@Override
	public void updateBeforeStatus(Integer appCode, Integer deviceType, Integer osType, String versionNo, String tenantId) {
		LambdaQueryWrapper<AppVersionEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper
			.eq(AppVersionEntity::getAppCode, appCode)
			.eq(AppVersionEntity::getDeviceType, deviceType)
			.eq(AppVersionEntity::getOsType, osType)
			.eq(AppVersionEntity::getTenantId, tenantId);
		List<AppVersionEntity> list = baseMapper.selectList(queryWrapper);
		if (null != list && list.size() > 0) {
			String max = getMaxVersion(list, versionNo);
			if (StringUtils.isNotEmpty(max)) {
				AppVersionEntity maxAppVersion = findAppVersionByVersion(appCode, deviceType, osType, max, tenantId);
				//如果上一版本存在
				if (maxAppVersion != null) {
					//将上一版本的状态改变为chechcode值
					maxAppVersion.setIsNew(false);
					maxAppVersion.setUpdateTime(new Date());
					baseMapper.updateById(maxAppVersion);
				}
			}
		}
	}

	@Override
	public AppVersionEntity findAppVersionInfo(String versionNo, Integer deviceType, Integer osType, Integer appCode, String tenantId) {

		return baseMapper.selectOne(new LambdaQueryWrapper<AppVersionEntity>()
			.eq(AppVersionEntity::getVersionNo, versionNo)
			.eq(AppVersionEntity::getDeviceType, deviceType)
			.eq(AppVersionEntity::getOsType, osType)
			.eq(AppVersionEntity::getAppCode, appCode)
			.eq(AppVersionEntity::getStatus, 1));
	}

	@Override
	public AppVersionEntity findNewVersion(Integer deviceType, Integer osType, Integer appCode, String tenantId) {
		return baseMapper.selectOne(new LambdaQueryWrapper<AppVersionEntity>()
			.eq(AppVersionEntity::getDeviceType, deviceType).eq(AppVersionEntity::getOsType, osType)
			.eq(AppVersionEntity::getAppCode, appCode).eq(AppVersionEntity::getIsNew, 1)
			.eq(AppVersionEntity::getStatus, 1)
			.eq(AppVersionEntity::getTenantId, tenantId)
			.last(" limit 1"));
	}

	@Override
	public boolean findCodeByVersion(String versionNo,Integer osType, String tenantId) {
		List<AppVersionEntity> list = baseMapper.selectList(new LambdaQueryWrapper<AppVersionEntity>().gt(AppVersionEntity::getVersionNo, versionNo)
			.eq(AppVersionEntity::getOsType, osType)
			.eq(AppVersionEntity::getCheckCode, 3)
			.eq(AppVersionEntity::getTenantId, tenantId));
		return null == list || list.size() == 0 ? false : true;
	}

	public String getMaxVersion(List<AppVersionEntity> appVersionList, String currentVersionNo) {
		String max = null;
		if (CollectionUtils.isNotEmpty(appVersionList)) {
			for (AppVersionEntity appVersion : appVersionList) {
				if (Util.compareVersion(appVersion.getVersionNo(), currentVersionNo) == -1) {//版本号小于当前版本号
					if (max == null) {
						max = appVersion.getVersionNo();
					} else {
						if (Util.compareVersion(appVersion.getVersionNo(), max) == 1) {
							max = appVersion.getVersionNo();
						}
					}
				}
			}
		}
		return max;
	}
}
