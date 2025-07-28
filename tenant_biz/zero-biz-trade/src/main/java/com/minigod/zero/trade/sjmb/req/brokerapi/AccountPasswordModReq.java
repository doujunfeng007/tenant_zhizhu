package com.minigod.zero.trade.sjmb.req.brokerapi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName AccountPasswordModReq.java
 * @Description TODO
 * @createTime 2024年01月13日 10:19:00
 */
@Data
public class AccountPasswordModReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资金账号
     */
    private String accountId;
    /**
     * 新的密码
     */
    private String newPassword;
}
