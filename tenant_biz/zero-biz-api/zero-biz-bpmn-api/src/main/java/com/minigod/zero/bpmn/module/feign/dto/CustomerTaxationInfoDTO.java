package com.minigod.zero.bpmn.module.feign.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author chen
 * @ClassName AccountTaxationInfoDTO.java
 * @Description TODO
 * @createTime 2024年10月17日 19:45:00
 */
@Data
public class CustomerTaxationInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 司法管辖区
	 */
	private String taxCountry;

	/**
	 * 税务编号
	 */
	private String taxNumber;

	/**
	 * 是否有税务编号[0=否 1=是]
	 */
	private Integer hasTaxNumber;

	/**
	 * 理由类型
	 */
	private String reasonType;

	/**
	 * 理由描述
	 */
	private String reasonDesc;

	/**
	 * 额外披露
	 */
	private String additionalDisclosures;

}
