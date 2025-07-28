package com.minigod.zero.biz.task.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.biz.task.mapper.IpoSmsZqInfoMapper;
import com.minigod.zero.biz.task.service.IIpoSmsZqInfoService;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.trade.entity.IpoSmsZqInfoEntity;
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
	public IpoSmsZqInfoEntity queryObjectByBean(IpoSmsZqInfoEntity entity) {
		return baseMapper.selectOne(Condition.getQueryWrapper(entity));
	}
}
