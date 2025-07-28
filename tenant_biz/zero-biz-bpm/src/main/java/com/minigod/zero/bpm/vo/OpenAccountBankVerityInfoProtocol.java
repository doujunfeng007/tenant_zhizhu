package com.minigod.zero.bpm.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OpenAccountBankVerityInfoProtocol implements Serializable {

	//用户名称
	private String clientName;
	//手机号码
	private String phoneNumber;
	//身份证号码
	private String idNo;
	//银行卡号
	private String bankCard;
	//状态[0-不通过 1-通过]
	private Integer verifyStatus;
	//验证次数
	private Integer verifyCount;
	//验证时间
	private Date verityTime;


}
