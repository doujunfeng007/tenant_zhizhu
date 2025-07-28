package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.CustomOpenInfoEntity;

import java.util.List;

/**
 * 服务类
 *
 * @author Chill
 */
public interface ICustomOpenInfoService extends BaseService<CustomOpenInfoEntity> {
	/**
	 * 查询最新开户缓存数据
	 *
	 * @param userId
	 * @return
	 */
	CustomOpenInfoEntity selectCustomOpenInfo(Long userId);

	/**
	 * 是否可更新开户缓存数据
	 *
	 * @param userId
	 * @return
	 */
	boolean isCanUpdateOpenInfo(Long userId);

	/**
	 * 查询未推送的开户缓存数据
	 *
	 * @return
	 */
	List<CustomOpenInfoEntity> selectByIsPushedFalse();
}
