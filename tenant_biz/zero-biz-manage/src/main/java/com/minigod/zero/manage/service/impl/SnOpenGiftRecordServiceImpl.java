package com.minigod.zero.manage.service.impl;

import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.manage.entity.SnOpenGiftRecordEntity;
import com.minigod.zero.manage.mapper.SnOpenGiftRecordMapper;
import com.minigod.zero.manage.service.ISnOpenGiftRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 开放渠道礼包记录维护服务接口实现
 *
 * @author eric
 * @since 2024-12-26 11:23:05
 */
@Slf4j
@Service
public class SnOpenGiftRecordServiceImpl extends BaseServiceImpl<SnOpenGiftRecordMapper, SnOpenGiftRecordEntity> implements ISnOpenGiftRecordService {
	/**
	 * 保存开放渠道礼包记录
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public boolean saveOpenGiftRecord(SnOpenGiftRecordEntity entity) {
		boolean result = this.save(entity);
		if (result) {
			log.info("保存开放渠道礼包记录成功, 主键ID:{}", entity.getId());
		} else {
			log.info("保存开放渠道礼包记录失败, 主键ID:{}", entity.getId());
		}
		return result;
	}
}
