package com.minigod.zero.bpm.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OpenAccountOtherDisclosureProtocol implements Serializable {

	//编号
	private Integer disclosureCode;
	//标示符[0-否 1-是]
	private Integer disclosureIsTrue;
	//披露字段1,多组信息逗号隔开
	private String disclosure1;
	//披露字段2,多组信息逗号隔开
	private String disclosure2;
	//披露字段3,多组信息逗号隔开
	private String disclosure3;
	//披露字段4,多组信息逗号隔开
	private String disclosure4;

}
