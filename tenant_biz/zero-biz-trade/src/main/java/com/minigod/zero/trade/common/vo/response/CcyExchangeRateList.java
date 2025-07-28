package com.minigod.zero.trade.common.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 汇率
 * @author: Larry Lai
 * @date: 2020/4/22 11:01
 * @version: v1.0
 */

@Data
public class CcyExchangeRateList implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<CcyExchangeRate> listCcyExchangeRate;

}
