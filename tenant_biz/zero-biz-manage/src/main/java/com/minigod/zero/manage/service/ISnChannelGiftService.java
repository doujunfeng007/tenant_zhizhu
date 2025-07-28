package com.minigod.zero.manage.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.manage.entity.SnChannelGiftEntity;

/**
 * 渠道礼包维护服务接口
 *
 * @author eric
 * @since 2024-12-26 11:04:01
 */
public interface ISnChannelGiftService extends BaseService<SnChannelGiftEntity> {
	/**
	 * 保存渠道礼包
	 *
	 * @param entity
	 * @return
	 */
	boolean saveChannelGift(SnChannelGiftEntity entity);

	/**
	 * 更新渠道礼包
	 *
	 * @param entity
	 * @return
	 */
	boolean updateChannelGift(SnChannelGiftEntity entity);
}
