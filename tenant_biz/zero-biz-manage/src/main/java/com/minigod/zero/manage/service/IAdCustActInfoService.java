package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.manage.entity.AdCustActInfoEntity;
import com.minigod.zero.manage.vo.AdCustActInfoVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 广告用户活动信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public interface IAdCustActInfoService extends IService<AdCustActInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param adCustActInfo
	 * @return
	 */
	IPage<AdCustActInfoVO> selectAdCustActInfoPage(IPage<AdCustActInfoVO> page, AdCustActInfoVO adCustActInfo);

	/**
	 * 用户活动数据
	 *
	 * @param adId
	 * @param userId
	 * @return
	 */
	AdCustActInfoEntity findAdUserActInfo(Long adId, Long userId);

}
