package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.vo.ChannelGiftVO;
import com.minigod.zero.manage.vo.request.GiftSearchReqVO;

/**
 * 渠道礼包服务接口
 *
 * @author eric
 * @since 2024-12-24 13:36:08
 */
public interface IChannelGiftService {
	/**
	 * 渠道礼包列表
	 *
	 * @param page
	 * @param searchReqVO
	 * @return
	 */
	IPage<ChannelGiftVO> queryChannelGiftList(IPage<ChannelGiftVO> page, GiftSearchReqVO searchReqVO);

	/**
	 * 更新渠道礼包状态
	 *
	 * @param id
	 * @param status
	 */
	void updateGiftStatus(Long id, Integer status);

	/**
	 * 删除渠道礼包
	 *
	 * @param id
	 * @return
	 */
	void deleteChannelGift(Long id);
}
