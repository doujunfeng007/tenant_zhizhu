package com.minigod.zero.trade.tiger.req;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName EstimateQuantityReq.java
 * @Description TODO
 * @createTime 2025年02月24日 19:12:00
 */

@Data
public class EstimateQuantityReq {

      private String attrs;

      private String currency;

      private Boolean enableFractionalShare;

      private String expiry;

      /**
       *  订单限价，提交的订单需要按照以此价格或者更优的价格成交,限价单和止损限价单需要设置
       */
      private BigDecimal limitPrice;

      /**
       * 期权权利类型, 期权合约赋予持有人在特定时间段内，按照事先约定的价格买入或卖出标的资产的权利
       * CALL:持有者在特定时间内按约定价格购买标的资产的权利
       * PUT:持有者在特定时间内按约定价格卖出标的资产的权利
       */
      private String right;

      private String secType;

      private BigDecimal stopPrice;

      private String strike;

      private String symbol;

      private String accountId;

      /**
       * 订单买卖方向(SELL:卖 BUY:买)
       */
      private String action;

      private String orderType;

}
