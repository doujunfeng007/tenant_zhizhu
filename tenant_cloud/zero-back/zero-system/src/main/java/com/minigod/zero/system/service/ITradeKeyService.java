package com.minigod.zero.system.service;


import com.minigod.zero.system.entity.TradeKey;
import com.minigod.zero.core.mp.base.BaseService;

/**
 *  服务类
 *
 * @author Chill
 */
public interface ITradeKeyService extends BaseService<TradeKey> {

	/**
	 * 同归租户id查询交易详情
	 * @param tenantId
	 * @return
	 */
	TradeKey getByTenantId(String tenantId,String counterType);
}
