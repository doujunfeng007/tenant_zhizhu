package com.minigod.zero.trade.tiger.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName WithdrawalRequest.java
 * @Description TODO
 * @createTime 2025年02月21日 10:59:00
 */
@Data
public class UserQuotePermissionQueryReq implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long clientUid;


    /**
     * 权限code
     */
    private String code;




}
