package com.minigod.zero.trade.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.trade.entity.IpoGeniusInfoEntity;
import com.minigod.zero.trade.mapper.IpoGeniusInfoMapper;
import com.minigod.zero.trade.service.IIpoGeniusInfoService;
import com.minigod.zero.trade.vo.IpoGeniusInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * IPO打新牛人信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
@Service
public class IpoGeniusInfoServiceImpl extends ServiceImpl<IpoGeniusInfoMapper, IpoGeniusInfoEntity> implements IIpoGeniusInfoService {

	@Override
	public IPage<IpoGeniusInfoVO> selectIpoGeniusInfoPage(IPage<IpoGeniusInfoVO> page, IpoGeniusInfoVO ipoGeniusInfo) {
		return page.setRecords(baseMapper.selectIpoGeniusInfoPage(page, ipoGeniusInfo));
	}

	@Override
	public List<IpoGeniusInfoEntity> findIpoGeniusInfoList(boolean isStatus) {
		return new LambdaQueryChainWrapper<>(baseMapper).eq(IpoGeniusInfoEntity::getIsStatus, isStatus).list();
	}

	@Override
	public void saveOrUpdateIpoGeniusInfo(IpoGeniusInfoEntity ipoGeniusInfo) {
		if (null == ipoGeniusInfo.getId()) {
			baseMapper.insert(ipoGeniusInfo);
		} else {
			baseMapper.updateById(ipoGeniusInfo);
		}
	}
}
