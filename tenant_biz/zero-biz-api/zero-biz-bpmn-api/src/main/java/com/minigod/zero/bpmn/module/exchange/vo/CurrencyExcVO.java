package com.minigod.zero.bpmn.module.exchange.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName: com.minigod.zero.bpmn.module.exchange.vo.CurrencyExcVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/8/22 22:45
 * @Version: 1.0
 */
@Data
public class CurrencyExcVO {

	private String currency;
	private BigDecimal amount;
}
