package com.minigod.zero.trade.sjmb.req.openapi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName LoginReq.java
 * @Description TODO
 * @createTime 2024年01月10日 19:04:00
 */
@Data
public class LoginReq  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accountId;

    private String password;

    private String tfaType;
}
