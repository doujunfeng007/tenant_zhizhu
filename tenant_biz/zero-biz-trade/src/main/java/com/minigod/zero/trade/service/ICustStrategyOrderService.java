package com.minigod.zero.trade.service;


import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.entity.CustStrategyOrderEntity;
import com.minigod.zero.trade.vo.strategy.CustStrategyOrderVO;

/**
 * @author:yanghu.luo
 * @create: 2023-08-02 14:04
 * @Description: 条件单服务
 */
public interface ICustStrategyOrderService extends BaseService<CustStrategyOrderEntity> {


	/**
	 *  条件单下单
	 */
	R<String> placeStrategyOrder(CustStrategyOrderVO request);


	/**
	 *  条件单下单失败更新状态
	 */
	R errorStrategyOrder(Long id, String errorMessage, boolean delete);


	/**
	 *  条件单下单更新过期时间
	 */
	R updateStrategyOrderExpirationTime(Long id, Long expirationTime);


	R updateStrategyOrder(CustStrategyOrderVO request);

    R cancelStrategyOrder(CustStrategyOrderVO request);

	/**
	 * 条件单触发
	 * @param cso
	 * @return
	 */
	R<CustStrategyOrderEntity> triggerStrategyOrder(CustStrategyOrderVO cso);
}
