package com.minigod.zero.trade.auth.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 指纹token
 */

@Data
public class TradeTokenResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 指纹token
     */
    private String tradeToken;
}
