package com.minigod.zero.trade.sjmb.common;

/**
 * 恒生3.0 功能号
 */
public enum SjmbFunctionsUrlEnum {


    /**
     * openApi
     */

    CLIENT_LOGIN( "/login","客户登录"),
    LOGIN_PAWWWORD_MOD("/login/password/mod","修改密码"),
    ORDER_PLACE("/order/place","下单"),
    ORDER_REPLACE("/order/replace","改单"),
    ORDER_CANCEL("/order/cancel","撤单"),



    /**
     * brokerapi
     */
    ACCOUNT_PASSWORD_MOD("/account/password/mod","修改交易密码"),
	ACCOUNT_PASSWORD_RESET("/account/password/reset","重置交易密码"),
    ASSET_QUERY("/asset","查询账号资产"),
    ASSET_DETAIL_QUERY("/asset/detail","查询账号资产明细"),
    POSITION_QUERY("/position","持仓列表查询"),
    ORDER_TODAY("/order/today","今日委托查询"),
    ORDER_HISTORY("/order/history","历史委托查询"),
    POSITION_OPEN_LIMIT("/position/open/limit","查询某合约最大开仓手数"),
	STOCK_FLOW("/position/flow","持仓流水"),
	CASH_FLOW("/cash","资金流水"),
	ACCOUNT_ADD_BATCH("/account/add/batch","批量添加个人账号"),
	DEPOSIT("/deposit/notice/direct","入金"),
	WITHDRAWAL("/withdrawal/direct","出金"),
	FREEZE("/locked/cash/lock","冻结"),
	UN_FREEZE("/locked/cash/unlock","解冻"),

    EN_US( "英文","");
    private String url;
    private String desc;

    SjmbFunctionsUrlEnum( String url, String desc) {
        this.url = url;
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
