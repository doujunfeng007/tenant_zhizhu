package com.minigod.zero.trade.sjmb.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName LoginResp.java
 * @Description TODO
 * @createTime 2024年01月11日 10:17:00
 */
@Data
public class LoginResp implements Serializable {

    /**
     * 资金账号
     */
    private String accountId;

    /**
     * 用户token
     */
    private String token;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录结果
     */
    private Integer loginResult;
}
