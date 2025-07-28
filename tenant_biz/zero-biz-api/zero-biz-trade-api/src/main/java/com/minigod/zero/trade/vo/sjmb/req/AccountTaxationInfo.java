package com.minigod.zero.trade.vo.sjmb.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName AccountTaxationInfo.java
 * @Description 税务信息
 * @createTime 2024年04月17日 17:19:00
 */
@Data
public class AccountTaxationInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 国家
	 */
	private String taxCountry;

	/**
	 * 税务编号
	 */
	private String taxNumber;
}
