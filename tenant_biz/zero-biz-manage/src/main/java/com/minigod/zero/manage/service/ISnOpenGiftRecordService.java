package com.minigod.zero.manage.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.manage.entity.SnOpenGiftRecordEntity;

/**
 * 开放渠道礼包记录维护服务接口定义
 *
 * @author eric
 * @since 2024-12-26 11:23:05
 */
public interface ISnOpenGiftRecordService extends BaseService<SnOpenGiftRecordEntity> {
	/**
	 * 保存开放渠道礼包记录
	 *
	 * @param entity
	 * @return
	 */
	boolean saveOpenGiftRecord(SnOpenGiftRecordEntity entity);
}
