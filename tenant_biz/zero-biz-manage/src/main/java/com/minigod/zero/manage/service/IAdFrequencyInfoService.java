package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.manage.entity.AdFrequencyInfoEntity;
import com.minigod.zero.manage.vo.AdFrequencyInfoVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  广告频率表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public interface IAdFrequencyInfoService extends IService<AdFrequencyInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param adFrequencyInfo
	 * @return
	 */
	IPage<AdFrequencyInfoVO> selectAdFrequencyInfoPage(IPage<AdFrequencyInfoVO> page, AdFrequencyInfoVO adFrequencyInfo);

	/**
	 * 根据广告id和用户id获取广告显示频率list
	 * @param adId
	 * @param userId
	 * @return
	 */
	AdFrequencyInfoEntity getAdInfoFrequencyByAdIdAndUserId(Long adId, Long userId);
}
