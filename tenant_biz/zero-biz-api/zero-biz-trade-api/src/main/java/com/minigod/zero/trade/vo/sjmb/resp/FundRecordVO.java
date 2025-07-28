package com.minigod.zero.trade.vo.sjmb.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName FundRecordVO.java
 * @Description TODO
 * @createTime 2023年09月26日 15:35:00
 */
@Data
public class FundRecordVO implements Serializable {

	private static final long serialVersionUID = 1L;

    private String businessDate;

    private String businessFlag;

    private String businessName;

    private String currency;

    private BigDecimal businessBalance =new BigDecimal("0");

    private String orderNo;

    private BigDecimal businessAmount =new BigDecimal("0");

    private String remark;
    private String exchangeType;


}
