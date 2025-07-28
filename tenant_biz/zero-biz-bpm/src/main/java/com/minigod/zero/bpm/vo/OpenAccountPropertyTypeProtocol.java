package com.minigod.zero.bpm.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OpenAccountPropertyTypeProtocol implements Serializable {

	//财产种类
	private Integer propertyType;
	//财产金额
	private String propertyAmount;

}
