package com.minigod.zero.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.system.entity.TradeKey;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.system.mapper.TradeKeyMapper;
import com.minigod.zero.system.service.ITradeKeyService;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author Chill
 */
@Service
public class TradeKeyServiceImpl extends BaseServiceImpl<TradeKeyMapper, TradeKey> implements ITradeKeyService {

	@Override
	public TradeKey getByTenantId(String tenantId,String counterType) {
		return getOne(Wrappers.<TradeKey>query().lambda().eq(TradeKey::getTenantId, tenantId)
			.eq(TradeKey::getCounterType, counterType));
	}
}
