package com.minigod.zero.bpm.dto;

import lombok.Data;

/**
 * @author chen
 * @ClassName ExchangeQueryReq.java
 * @Description TODO
 * @createTime 2024年03月18日 10:56:00
 */
@Data
public class ExchangeQueryReq {

	private String applicationId;

	private Integer status;

	private Integer currencyOut;

	private Integer currencyIn;
}
