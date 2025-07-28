package com.minigod.zero.trade.tiger.enums;

import cn.hutool.http.Method;

/**
 * @author chen
 * @ClassName TigerFunctionsEnum.java
 * @Description tiger请求的url
 * @createTime 2025年02月18日 16:39:00
 */
public enum TigerFunctionsEnum {


    GET_TOKEN("GET_TOKEN", "/auth/token",Method.POST,"获取会话"),
    CREATE_USER("CREATE_USER", "/users",Method.POST,"创建用户"),
    CREATE_ACCOUNT("CREATE_ACCOUNT", "/accounts",Method.POST,"创建账户"),
    DEPOSIT("DEPOSIT", "/transfers/deposit-applications",Method.POST,"入金"),
    WITHDRAW("WITHDRAW", "/transfers/withdraw",Method.POST,"出金"),
    TRADING_ASSETS("TRADING_ASSETS", "/trading/assets",Method.GET,"获取账户资产"),
    ACCOUNT_POSITIONS("ACCOUNT_POSITIONS", "/trading/positions",Method.GET,"获取账户持仓"),
    MODIFY_ORDER("MODIFY_ORDER", "/trading/orders",Method.PATCH,"改单"),
    PLACE_ORDER("PLACE_ORDER", "/trading/orders",Method.POST,"下单"),
    CANCEL_ORDER("PLACE_ORDER", "/trading/orders",Method.DELETE,"撤单"),
    QUERY_ORDER("QUERY_ORDER", "/trading/orders",Method.GET,"查询订单"),
    ESTIMATE_QUANTITY("ESTIMATE_QUANTITY", "/trading/orders/estimate-quantity",Method.GET,"预估可买可卖"),
    QUOTE_PERMISSION("QUOTE_PERMISSION", "/user/quote/permission",Method.POST,"添加用户权限"),
    QUERY_QUOTE_PERMISSION("QUERY_QUOTE_PERMISSION", "/user/quote/permission/remain",Method.GET,"查询用户权限过期时间"),
//    FUND_FREEZE("FUND_FREEZE", "/trading/portfolio/locked-assets",Method.POST,"冻结"),
    FUND_FREEZE_NEW("FUND_FREEZE_NEW", "/trading/business/locked-assets",Method.POST,"新的冻结"),
    UN_FUND_FREEZE("UN_FUND_FREEZE", "/trading/portfolio/locked-assets",Method.DELETE,"解冻"),
    ACCOUNT_PNL("ACCOUNT_PNL", "/trading/pnls",Method.GET,"查询账户盈亏"),
    QUERY_AVAILABLE_CASH("QUERY_AVAILABLE_CASH", "/withdrawable/withdraw",Method.GET,"查询账户现金可出金额"),
    TRADING_TRANSACTIONS("TRADING_TRANSACTIONS", "/trading/transactions",Method.GET,"交易流水"),
    ;
    private String functionId;
    private String url;
    private Method method;
    private String desc;

    TigerFunctionsEnum(String functionId, String url, Method method, String desc) {
        this.functionId = functionId;
        this.url = url;
        this.method = method;
        this.desc = desc;
    }

    public static String getUrl(String functionId) {
        for (TigerFunctionsEnum i : TigerFunctionsEnum.values()) {
            if (i.getFunctionId().equals(functionId)) {
                return i.getUrl();
            }
        }
        return null;
    }

    public  String getFunctionId() {
        return functionId;
    }



    public String getUrl() {
        return url;
    }

    public Method getMethod() {
        return method;
    }
}
