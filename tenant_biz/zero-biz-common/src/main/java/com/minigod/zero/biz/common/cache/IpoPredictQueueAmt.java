package com.minigod.zero.biz.common.cache;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class IpoPredictQueueAmt implements Serializable {

    private Long predictConfigId;

    private Integer typ;

    private BigDecimal amt;

    private Date nowTime;
}
