package com.minigod.zero.bpmn.module.margincredit.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName CustMarginCreditVO.java
 * @Description TODO
 * @createTime 2024年03月09日 11:24:00
 */
@Data
public class CustMarginCreditVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 最大信用额度
	 */
	private BigDecimal maxExposure;

	/**
	 * 当前信用额度
	 */
	private BigDecimal creditValue;

	/**
	 * 申请信用额度
	 */
	private BigDecimal applyBalance;

	/**
	 * 币种
	 */
	private String currency;

	private String clientName;

	private String clientNameSpell;

	/**
	 * 已使用信用额度
	 */
	private BigDecimal useLineCredit =new BigDecimal("0");

	/**
	 * 信用比例
	 */
	private BigDecimal creditRating =new BigDecimal("0");

	private Long custId;

	private String fundAccount;
}
