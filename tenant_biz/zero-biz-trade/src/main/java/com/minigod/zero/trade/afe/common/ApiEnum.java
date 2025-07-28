package com.minigod.zero.trade.afe.common;

/**
 * @ClassName: com.minigod.zero.trade.afe.common.IpoUriEnum
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/4/22 18:41
 * @Version: 1.0
 */
public enum ApiEnum {
	EIPO_CANCEL ( "EIPO_CANCEL_REQUEST","取消申购"),
	EIPO_ORDER_DETAIL_QUERY  ( "EIPO_ORDER_DETAIL_QUERY_REQUEST","ipo订单详情"),
	EIPO_TICKET_DATA ( "EIPO_TICKET_DATA_REQUEST","可认购列表"),
	EIPO_PLACEMENT ( "EIPO_PLACEMENT_REQUEST","认购请求"),
	EIPO_ORDER_BOOK_QUERY ( "EIPO_ORDER_BOOK_QUERY_REQUEST","用户申购信息列表"),
	CREATE_CASH_DEPOSIT ( "CreateCashDeposit","客戶資金存入(入金)"),
	CREATE_CASH_WITHDRAWAL ( "CreateCashWithdrawal","客戶資金提取(出金)"),
	CREATE_CASH_HOLD ( "CreateCashHold","资金冻结"),
	CREATE_CASH_RELEASE ( "CreateCashRelease","资金解冻"),

	MARGINABLE_INSTRUMENT ( "QueryMarginableInstrument","获取可融资股票列表"),
	MARGINABLE_INSTRUMENT_SETTING ( "UpdateInstrumentMarginRatio","设置股票可融资比例"),
	UPDATE_ACCOUNT_INFO ( "UpdateAccountInformation","账户资料设置"),



	;
	private String url;
	private String desc;

	ApiEnum(String url, String desc) {
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
