package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.AppVersionEntity;
import com.minigod.zero.manage.vo.request.AppVersionVO;

/**
 * APP版本信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-04-24
 */
public interface IAppVersionService extends IService<AppVersionEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param appVersion
	 * @return
	 */
	IPage<AppVersionVO> selectAppVersionPage(IPage<AppVersionVO> page, AppVersionVO appVersion);

	void updateBeforeCheckCode(Integer appCode, Integer deviceType, Integer osType, String versionNo, Integer toAll, Integer checkCode, String tenantId);

	AppVersionEntity findAppVersionByVersion(Integer appCode, Integer deviceType, Integer osType, String versionNo, String tenantId);

	void updateBeforeStatus(Integer appCode, Integer deviceType, Integer osType, String versionNo, String tenantId);

	AppVersionEntity findAppVersionInfo(String versionNo, Integer deviceType, Integer osType, Integer typeValue, String tenantId);

	AppVersionEntity findNewVersion(Integer deviceType, Integer osType, Integer typeValue, String tenantId);

	boolean findCodeByVersion(String versionNo, Integer osType, String tenantId);
}
