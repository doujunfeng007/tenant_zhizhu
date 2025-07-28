package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.manage.entity.AdCustActInfoEntity;
import com.minigod.zero.manage.mapper.AdCustActInfoMapper;
import com.minigod.zero.manage.vo.AdCustActInfoVO;
import com.minigod.zero.manage.service.IAdCustActInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 广告用户活动信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@Service
public class AdCustActInfoServiceImpl extends ServiceImpl<AdCustActInfoMapper, AdCustActInfoEntity> implements IAdCustActInfoService {

	@Override
	public IPage<AdCustActInfoVO> selectAdCustActInfoPage(IPage<AdCustActInfoVO> page, AdCustActInfoVO adCustActInfo) {
		return page.setRecords(baseMapper.selectAdCustActInfoPage(page, adCustActInfo));
	}

	@Override
	public AdCustActInfoEntity findAdUserActInfo(Long adId, Long userId) {
		return new LambdaQueryChainWrapper<>(baseMapper)
			.eq(AdCustActInfoEntity::getAdId, adId)
			.eq(AdCustActInfoEntity::getUserId, userId)
			.eq(AdCustActInfoEntity::getStatus, true)
			.one();
	}
}
