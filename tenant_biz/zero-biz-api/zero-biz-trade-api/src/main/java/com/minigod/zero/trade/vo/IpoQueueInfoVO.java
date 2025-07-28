package com.minigod.zero.trade.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class IpoQueueInfoVO implements Serializable {

    private String stockCode;

    private Integer num;

    private BigDecimal queueTotalAmt;

    private String nowDateStr;
}
