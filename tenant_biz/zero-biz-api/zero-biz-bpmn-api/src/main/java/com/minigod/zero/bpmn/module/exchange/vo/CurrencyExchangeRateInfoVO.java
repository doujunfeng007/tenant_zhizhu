package com.minigod.zero.bpmn.module.exchange.vo;

import com.minigod.zero.bpmn.module.exchange.entity.CurrencyExchangeRateInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName CurrencyExchangeRateInfoVO.java
 * @Description TODO
 * @createTime 2024年03月16日 11:19:00
 */
@Data
public class CurrencyExchangeRateInfoVO extends CurrencyExchangeRateInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 目标币种[1-港币HKD 2-美元USD 3-人民币CNY]
	 */
	@ApiModelProperty(value = "目标币种[1-港币HKD 2-美元USD 3-人民币CNY]")
	private String buyCcyName;

	/**
	 * 原始币种[1-港币HKD 2-美元USD 3-人民币CNY]
	 */
	@ApiModelProperty(value = "原始币种[1-港币HKD 2-美元USD 3-人民币CNY]")
	private String sellCcyName;

	/**
	 * 目标币种+原始币种
	 */
	@ApiModelProperty(value = "目标币种+原始币种")
	private String buyCcyAndSellCcyName;

	/**
	 * 汇率状态[0-过期 1-正常]
	 */
	@ApiModelProperty(value = "汇率状态[0-过期 1-正常]")
	private String statusName;
}
