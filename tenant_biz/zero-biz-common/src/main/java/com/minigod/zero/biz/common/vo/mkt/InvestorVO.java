package com.minigod.zero.biz.common.vo.mkt;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvestorVO {
	private String institutionName; //基石投资者名称
	private String shareHolding; //认购数
	private BigDecimal percentage; //比例

}
