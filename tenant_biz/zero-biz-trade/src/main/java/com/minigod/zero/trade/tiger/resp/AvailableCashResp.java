package com.minigod.zero.trade.tiger.resp;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class AvailableCashResp {

    private String currency;

    /**
     * 可出金额
     */
    private BigDecimal withdrawable;


}
