package com.minigod.zero.trade.afe.common;

/**
 * AFE接口请求类型和返回类型对应
 */
public enum AfeInterfaceTypeEnum {
    LOGIN("LOGIN_REQUEST", "LOGIN_RESPONSE"), // 登录
    HOLDING_QUERY("HOLDING_QUERY_REQUEST", "HOLDING_QUERY_RESPONSE"), // 持仓查询 包含资金相关信息
//	BUYING_POWER_QUERY("BUYING_POWER_QUERY_REQUEST", "BUYING_POWER_QUERY_RESPONSE"), // 资金汇总查询
	ORDER_SUMMARY_QUERY("ORDER_SUMMARY_QUERY_REQUEST", "ORDER_SUMMARY_RESPONSE"), // 订单查询
	ORDER_BOOK_HISTORY("ORDER_BOOK_HISTORY_QUERY", "ORDER_BOOK_HISTORY_RESPONSE"), // 订单查询
	ORDER_PLACEMENT("ORDER_PLACEMENT_REQUEST", "ORDER_PLACEMENT_RESPONSE"), // 下单
	ORDER_AMENDMENT("ORDER_AMENDMENT_REQUEST", "ORDER_AMENDMENT_RESPONSE"), // 改单
	ORDER_CANCELLATION("ORDER_CANCELLATION_REQUEST", "ORDER_CANCELLATION_RESPONSE"), // 撤单
	CHANGE_PASSWORD("CHANGE_PASSWORD_REQUEST","CHANGE_PASSWORD_RESPONSE"), // 修改密码
	RESET_PASSWORD("RESET_PASSWORD_REQUEST","RESET_PASSWORD_RESPONSE"), // 重置交易密码
	CASH_MOVEMENT_QUERY("CASH_MOVEMENT_QUERY_REQUEST","CASH_MOVEMENT_QUERY_RESPONSE"), // 资金流水
	STOCK_MOVEMENT_QUERY("STOCK_MOVEMENT_QUERY_REQUEST","STOCK_MOVEMENT_QUERY_RESPONSE"), // 股票流水
	LOGOUT("LOGOUT", "LOGOUT_RESPONSE"), // 退出登录
	KICKOUT_LOGON("KICKOUT_LOGON_REQUEST", "KICKOUT_RESPONSE"), // 下线

	/*-------------------------ipo接口----------------------------*/
	IPO_TICKET_DATA("EIPO_TICKET_DATA_REQUEST", "EIPO_TICKET_DATA_RESPONSE"), // 查看ipo列表
	IPO_PLACEMENT("EIPO_PLACEMENT_REQUEST", "EIPO_PLACEMENT_RESPONSE"), // 申请购买ipo
	IPO_CANCEL("EIPO_CANCEL_REQUEST", "EIPO_CANCEL_RESPONSE"), // 撤销ipo
	IPO_ORDER_BOOK_QUERY("EIPO_ORDER_BOOK_QUERY_REQUEST", "EIPO_ORDER_BOOK_RESPONSE"), // 认购记录
    ;
    private final String requestType;

    private final String responseType;

    public String getRequestType() {
        return requestType;
    }

    public String getResponseType() {
        return responseType;
    }

    AfeInterfaceTypeEnum(String requestType, String responseType) {
        this.requestType = requestType;
        this.responseType = responseType;
    }

    public static String getResponseType(String requestType) {
        for (AfeInterfaceTypeEnum type : values()) {
            if (type.requestType.equals(requestType)) {
                return type.responseType;
            }
        }
        return null;
    }

}
