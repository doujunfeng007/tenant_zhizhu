package com.minigod.zero.trade.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.trade.entity.IpoSmsZqInfoEntity;
import com.minigod.zero.trade.mapper.IpoSmsZqInfoMapper;
import com.minigod.zero.trade.service.IIpoSmsZqInfoService;
import com.minigod.zero.trade.vo.IpoSmsZqInfoVO;
import org.springframework.stereotype.Service;

/**
 * IPO已中签短信通知 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-02-02
 */
@Service
public class IpoSmsZqInfoServiceImpl extends ServiceImpl<IpoSmsZqInfoMapper, IpoSmsZqInfoEntity> implements IIpoSmsZqInfoService {

	@Override
	public IPage<IpoSmsZqInfoVO> selectIpoSmsZqInfoPage(IPage<IpoSmsZqInfoVO> page, IpoSmsZqInfoVO ipoSmsZqInfo) {
		return page.setRecords(baseMapper.selectIpoSmsZqInfoPage(page, ipoSmsZqInfo));
	}

	@Override
	public IpoSmsZqInfoEntity queryObjectByBean(IpoSmsZqInfoEntity entity) {
		return baseMapper.selectOne(Condition.getQueryWrapper(entity));
	}
}
