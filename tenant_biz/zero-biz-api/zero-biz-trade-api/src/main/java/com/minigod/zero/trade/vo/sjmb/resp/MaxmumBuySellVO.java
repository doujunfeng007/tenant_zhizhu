package com.minigod.zero.trade.vo.sjmb.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName MaxmumBuyVO.java
 * @Description TODO
 * @createTime 2023年10月09日 14:54:00
 */

@Data
public class MaxmumBuySellVO implements Serializable {

	private static final long serialVersionUID = 1L;


    private BigDecimal enableAmount =new BigDecimal("0"); // 综合购买力可买数量

    private BigDecimal cashAmount =new BigDecimal("0");  // 现金可买数量


    private BigDecimal enableSellAmount =new BigDecimal("0");// 可卖数量

    private BigDecimal enableBalance =new BigDecimal("0"); //购买力


}
