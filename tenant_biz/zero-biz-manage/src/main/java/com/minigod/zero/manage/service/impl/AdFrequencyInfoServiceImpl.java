package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.manage.entity.AdFrequencyInfoEntity;
import com.minigod.zero.manage.mapper.AdFrequencyInfoMapper;
import com.minigod.zero.manage.service.IAdFrequencyInfoService;
import com.minigod.zero.manage.vo.AdFrequencyInfoVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  广告频率表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@Service
public class AdFrequencyInfoServiceImpl extends ServiceImpl<AdFrequencyInfoMapper, AdFrequencyInfoEntity> implements IAdFrequencyInfoService {

	@Override
	public IPage<AdFrequencyInfoVO> selectAdFrequencyInfoPage(IPage<AdFrequencyInfoVO> page, AdFrequencyInfoVO adFrequencyInfo) {
		return page.setRecords(baseMapper.selectAdFrequencyInfoPage(page, adFrequencyInfo));
	}

	@Override
	public AdFrequencyInfoEntity getAdInfoFrequencyByAdIdAndUserId(Long adId, Long userId) {
		return new LambdaQueryChainWrapper<>(baseMapper)
			.eq(AdFrequencyInfoEntity::getAdId, adId)
			.eq(AdFrequencyInfoEntity::getUserId, userId)
			.eq(AdFrequencyInfoEntity::getStatus, true)
			.one();
	}
}
