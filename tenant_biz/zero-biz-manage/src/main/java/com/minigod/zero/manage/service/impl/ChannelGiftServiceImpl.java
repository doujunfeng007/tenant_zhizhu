package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.manage.entity.SnChannelGiftEntity;
import com.minigod.zero.manage.vo.ChannelGiftVO;
import com.minigod.zero.manage.vo.request.GiftSearchReqVO;
import com.minigod.zero.manage.mapper.SnChannelGiftMapper;
import com.minigod.zero.manage.service.IChannelGiftService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 渠道礼包服务实现
 *
 * @author eric
 * @since 2024-12-24 14:38:08
 */
@Slf4j
@Service
public class ChannelGiftServiceImpl extends BaseServiceImpl<SnChannelGiftMapper, SnChannelGiftEntity> implements IChannelGiftService {

	private final SnChannelGiftMapper snChannelGiftMapper;

	public ChannelGiftServiceImpl(SnChannelGiftMapper snChannelGiftMapper) {
		this.snChannelGiftMapper = snChannelGiftMapper;
	}

	/**
	 * 渠道礼包列表
	 *
	 * @param page
	 * @param searchReqVO
	 * @return
	 */
	@Override
	public IPage<ChannelGiftVO> queryChannelGiftList(IPage<ChannelGiftVO> page, GiftSearchReqVO searchReqVO) {
		return snChannelGiftMapper.queryPageChannelGift(page, searchReqVO);
	}

	/**
	 * 更新渠道礼包状态
	 *
	 * @param id
	 * @param status
	 */
	@Override
	public void updateGiftStatus(Long id, Integer status) {
		LambdaQueryWrapper<SnChannelGiftEntity> lqw = Wrappers.lambdaQuery();
		lqw.eq(SnChannelGiftEntity::getId, id);
		SnChannelGiftEntity entity = snChannelGiftMapper.selectOne(lqw);
		if (entity != null) {
			entity.setStatus(status);
			int rows = snChannelGiftMapper.updateById(entity);
			if (rows > 0) {
				log.info("更新渠道礼包状态成功, 主键ID:{}, 状态:{}", entity.getId(), status);
			}
		}
	}

	/**
	 * 删除渠道礼包
	 *
	 * @param id
	 * @return
	 */
	@Override
	public void deleteChannelGift(Long id) {
		LambdaQueryWrapper<SnChannelGiftEntity> lqw = Wrappers.lambdaQuery();
		lqw.eq(SnChannelGiftEntity::getId, id);
		SnChannelGiftEntity entity = snChannelGiftMapper.selectOne(lqw);
		if (entity != null) {
			entity.setIsDeleted(1);
			int rows = snChannelGiftMapper.updateById(entity);
			if (rows > 0) {
				log.info("删除渠道礼包成功, 主键ID:{}, 状态:{}", entity.getId());
			}
		}
	}
}
