package com.minigod.zero.bpm.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BpmTradeAcctRespDto implements Serializable {

	private String tradeAccount;

	private Integer acctType;

	private String capitalAccount;

	private String accountType;

	private String appPermission;

	private Integer authorStatus;

	private Long custId;

	private Integer riskLevel;
}
