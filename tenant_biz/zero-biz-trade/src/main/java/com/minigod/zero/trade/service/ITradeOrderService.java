package com.minigod.zero.trade.service;

import com.minigod.zero.core.tool.api.R;

/**
 * 委托订单服务
 */
public interface ITradeOrderService {

	/**
	 * 保存交易订单
	 */
	R saveTradeOrder(String request);

    /**
     * 改单更新
     */
	R updateTradeOrder(String request);

    /**
     * 条件单更新
     */
	R updateTradeStrategyOrder(String request);

    /**
     * 撤单更新
     */
	R updateCancelTradeOrder(String request);

    /**
     * 分页查找委托单
     */
	R findPage(String request);

    /**
     * 查找委托单
     */
	R findList(String request);

    /**
     * 根据委托单号查找最新的一条记录
     */
	R getLatestTradeOrderInfo(String request);
}
