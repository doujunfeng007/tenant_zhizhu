package com.minigod.zero.trade.tiger.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.tiger.req.*;
import com.minigod.zero.trade.vo.sjmb.req.FundUnFreezeReq;

/**
 * @author chen
 * @ClassName TigerTradeService.java
 * @Description tiger调用
 * @createTime 2025年02月18日 11:00:00
 */
public interface TigerCommonService {
    /**
     * 获取tiger token
     * @return
     */
    R getToken();

    /**
     * 创建用户
     * @param req
     * @return
     */
	R createUser(CreateUserReq req);

    /**
     * 创建账户
     * @param req
     * @return
     */
	R createAccount(CreateAccountReq req);

    /**
     * 入金
     * @param req
     * @return
     */
	R deposit(DepositReq req);

    /**
     * 出金
     * @param req
     * @return
     */
	R withdrawal(WithdrawalReq req);

    /**
     * 查询资产
     * @param baseRequest
     * @return
     */
	R queryAssets(UserAccountQuery baseRequest);

    /**
     * 获取持仓
     * @param positionsReq
     * @return
     */
	R getAccountPositions(PositionsReq positionsReq);

    /**
     * 改单
     * @param accountId
     * @param updateOrderReq
     * @return
     */
	R updateOrder(String accountId, UpdateOrderReq updateOrderReq);

    /**
     * 下单
     * @param accountId
     * @param placeOrderReq
     * @return
     */
	R placeOrder(String accountId, PlaceOrderReq placeOrderReq);

    /**
     * 取消订单
     * @param cancelOrderReq
     * @return
     */
	R cancelOrder( CancelOrderReq cancelOrderReq);

    /**
     * 查询最大可买可卖
     * @param estimateQuantityReq
     * @return
     */
	R getMaxmumBuySell(EstimateQuantityReq estimateQuantityReq);

    /**
     * 订单查询
     * @param order
     * @return
     */
	R getOrders(OrderQueryReq order);

    /**
     * 设置权限
     * @param userId
     * @param userQuotePermission
     * @return
     */
	R userQuotePermission(String userId, UserQuotePermissionReq userQuotePermission);

    /**
     * 查询权限到期时间
     * @param userQuotePermission
     * @return
     */
	R userQuotePermissionQuery(UserQuotePermissionQueryReq userQuotePermission);

    /**
     * 冻结
     * @param fundFreezeReq
     * @return
     */
	R freeze(FundFreezeReq fundFreezeReq);

    /**
     * 解冻
     * @param fundUnFreezeReq
     * @return
     */
	R unFreeze(FundUnFreezeReq fundUnFreezeReq);

    /**
     * 查询账户盈亏
     * @param baseRequest
     * @return
     */
	R queryAccountPnl(UserAccountQuery baseRequest);

    /**
     * 查询账户可用资金
     * @param accountId
     * @param moneyType
     * @return
     */
	R queryAccountAvailableCash(Long accountId, String moneyType);

    /**
     * 期权流水
     * @param query
     * @return
     */
	R getStockRecord(TradeTransactionsReq query);

}
