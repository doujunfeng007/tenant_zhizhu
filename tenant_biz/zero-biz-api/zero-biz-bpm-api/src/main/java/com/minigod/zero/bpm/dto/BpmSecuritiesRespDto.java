package com.minigod.zero.bpm.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BpmSecuritiesRespDto implements Serializable {

	private Long custId;

	private String phoneArea;

	private String phoneNumber;

	private String email;

	private String custNameSpell;

	private Integer bankType;

	private Integer accountLevel;

	private String custName;

	private String bankAccountName;

	private String fundAccountType;

	private Integer isFundUnilateralAccount;

	private String nationality;

	private String bcanNo;

	private String bcanStatus;

	private String inviterId;

	private String clientType;

	private String givenNameSpell;

	private String familyNameSpell;

	private boolean needCA;

	private Integer northTrade;

	private String issueCountry;

	private String clientStatus;

	private String aecode;

}
