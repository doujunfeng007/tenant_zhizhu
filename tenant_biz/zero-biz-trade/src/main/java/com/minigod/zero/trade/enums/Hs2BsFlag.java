package com.minigod.zero.trade.enums;

/**
 * 推送消息的成交方向类型
 */
public enum Hs2BsFlag {
    BUY("1", "B"), //买入
    SELL("2", "S"), //卖出
    ;
    /**
     * iGateway portfolio 使用的成交方向类型
     */
    private final String portfolio;
    /**
     * 推送消息使用的成交方向类型
     */
    private final String flag;

    Hs2BsFlag(String portfolio, String flag) {
        this.portfolio = portfolio;
        this.flag = flag;
    }

    public String getPortfolio() {
        return portfolio;
    }

    /**
     * iGateway portfolio 使用的成交方向类型 获取 推送消息时对应的类型
     *
     * @param portfolioOrderSide iGateway portfolio 使用的成交方向类型
     * @return String
     */
    public static String getByPortfolio(String portfolioOrderSide) {
        for (Hs2BsFlag type : values()) {
            if (type.portfolio.equals(portfolioOrderSide)) {
                return type.flag;
            }
        }
        return null;
    }

}
