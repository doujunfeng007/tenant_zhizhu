package com.minigod.zero.bpm.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OpenAccountTaxationInfoProtocol implements Serializable {

	//司法管辖区
	private String taxCountry;
	//税务编号
	private String taxNumber;
	//是否有税务编号[0=否 1=是]
	private Integer hasTaxNumber;
	//理由类型
	private String reasonType;

	//理由描述
	private String reasonDesc;
}
