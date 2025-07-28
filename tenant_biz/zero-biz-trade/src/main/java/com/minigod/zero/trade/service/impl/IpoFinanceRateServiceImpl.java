package com.minigod.zero.trade.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.trade.entity.IpoFinanceRateEntity;
import com.minigod.zero.trade.mapper.IpoFinanceRateMapper;
import com.minigod.zero.trade.service.IIpoFinanceRateService;
import com.minigod.zero.trade.vo.IpoFinanceRateVO;
import org.springframework.stereotype.Service;

/**
 * IPO认购融资利率 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-01-30
 */
@Service
public class IpoFinanceRateServiceImpl extends ServiceImpl<IpoFinanceRateMapper, IpoFinanceRateEntity> implements IIpoFinanceRateService {

	@Override
	public IPage<IpoFinanceRateVO> selectIpoFinanceRatePage(IPage<IpoFinanceRateVO> page, IpoFinanceRateVO ipoFinanceRate) {
		return page.setRecords(baseMapper.selectIpoFinanceRatePage(page, ipoFinanceRate));
	}

	@Override
	public IpoFinanceRateEntity getIpoFinanceRate(String assetId) {
		return new LambdaQueryChainWrapper<>(baseMapper).eq(IpoFinanceRateEntity::getAssetId, assetId).one();
	}

	@Override
	public void saveOrUpdIpoFinanceRate(IpoFinanceRateEntity ipoFinanceRate) {
		if (null == ipoFinanceRate.getId()) {
			baseMapper.insert(ipoFinanceRate);
		} else {
			baseMapper.updateById(ipoFinanceRate);
		}
	}
}
