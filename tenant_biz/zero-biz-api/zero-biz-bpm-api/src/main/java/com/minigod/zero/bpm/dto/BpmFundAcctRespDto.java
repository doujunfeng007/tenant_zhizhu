package com.minigod.zero.bpm.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BpmFundAcctRespDto implements Serializable {

	private String fundAccount;

	private String fundAccountMain;

	private Integer fundOperType;

	private Integer riskType;

	private Date expiryDate;

	private Date nowDate;

	private Date evaluationDate;

	private Integer fundAccountType;

	private Integer retestSts;

}
