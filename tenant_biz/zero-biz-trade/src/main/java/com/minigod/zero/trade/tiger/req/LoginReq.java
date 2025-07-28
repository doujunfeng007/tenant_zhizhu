package com.minigod.zero.trade.tiger.req;

import lombok.Data;

/**
 * @author chen
 * @ClassName LoginReq.java
 * @Description TODO
 * @createTime 2025年02月18日 11:34:00
 */
@Data
public class LoginReq {

    private String appKey;

    private String appSecret;

    private String grantType;
}
