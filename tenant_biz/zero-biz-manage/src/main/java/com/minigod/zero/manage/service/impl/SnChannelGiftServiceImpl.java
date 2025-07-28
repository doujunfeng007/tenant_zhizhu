package com.minigod.zero.manage.service.impl;

import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.manage.entity.SnChannelGiftEntity;
import com.minigod.zero.manage.mapper.SnChannelGiftMapper;
import com.minigod.zero.manage.service.ISnChannelGiftService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 渠道礼包维护服务接口实现
 *
 * @author eric
 * @since 2024-12-26 11:04:01
 */
@Slf4j
@Service
public class SnChannelGiftServiceImpl extends BaseServiceImpl<SnChannelGiftMapper, SnChannelGiftEntity> implements ISnChannelGiftService {

	/**
	 * 保存渠道礼包
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public boolean saveChannelGift(SnChannelGiftEntity entity) {
		boolean result = this.save(entity);
		if (result) {
			log.info("保存渠道礼包成功, 主键ID:{}", entity.getId());
		} else {
			log.info("保存渠道礼包失败, 主键ID:{}", entity.getId());
		}
		return result;
	}

	/**
	 * 更新渠道礼包
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public boolean updateChannelGift(SnChannelGiftEntity entity) {
		boolean result = this.updateById(entity);
		if (result) {
			log.info("更新渠道礼包成功, 主键ID:{}", entity.getId());
		} else {
			log.info("更新渠道礼包失败, 主键ID:{}", entity.getId());
		}
		return result;
	}
}
