package com.minigod.zero.trade.vo.request;

import lombok.Data;

@Data
public class BaseRequest {

    private static final long serialVersionUID = 1L;

    private String id; //请求唯一标识
    private String tradeAccount;
    private String fundAccount;
    private String password;
    private String opStation;//客户端调用opStation
	private String optionsAccount; //期权账号
	private String channel; // 柜台渠道
}
