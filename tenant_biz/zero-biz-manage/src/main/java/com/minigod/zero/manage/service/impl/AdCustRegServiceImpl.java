package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.manage.entity.AdCustRegEntity;
import com.minigod.zero.manage.mapper.AdCustRegMapper;
import com.minigod.zero.manage.vo.AdCustRegVO;
import com.minigod.zero.manage.service.IAdCustRegService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 广告客户记录表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@Service
public class AdCustRegServiceImpl extends ServiceImpl<AdCustRegMapper, AdCustRegEntity> implements IAdCustRegService {

	@Override
	public IPage<AdCustRegVO> selectAdCustRegPage(IPage<AdCustRegVO> page, AdCustRegVO adCustReg) {
		return page.setRecords(baseMapper.selectAdCustRegPage(page, adCustReg));
	}

	@Override
	public AdCustRegEntity findUserAdReg(Long userId, Long adId) {
		return new LambdaQueryChainWrapper<>(baseMapper)
			.eq(AdCustRegEntity::getUserId, userId)
			.eq(AdCustRegEntity::getAdId, adId)
			.one();
	}
}
