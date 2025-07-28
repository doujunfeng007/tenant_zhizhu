package com.minigod.zero.trade.sjmb.req.openapi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName LoginPasswordModReq.java
 * @Description TODO
 * @createTime 2024年01月15日 14:30:00
 */
@Data
public class LoginPasswordModReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accountId;

    private String oldPassword;

    private String newPassword;
}
