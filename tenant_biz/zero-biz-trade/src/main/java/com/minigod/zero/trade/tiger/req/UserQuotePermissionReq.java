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
public class UserQuotePermissionReq implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 权限code
     */
    private String code;

    /**
     * 权限时长
     * MONTH
     * QUARTER
     * YEAR
     */
    private String duration;

    /**
     * 是否是专业用户
     */
    private boolean professional;



}
