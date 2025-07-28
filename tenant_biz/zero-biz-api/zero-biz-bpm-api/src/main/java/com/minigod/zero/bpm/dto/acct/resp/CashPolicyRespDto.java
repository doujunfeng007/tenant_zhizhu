package com.minigod.zero.bpm.dto.acct.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CashPolicyRespDto implements Serializable {

	private Long id;

	private Long custId;

	private Integer currency;

	private Integer busType;

	private String fundAccount;

	private Integer status;

	private BigDecimal extractionAmount;

	private String bankAccount;

	private String extAccountName;

	private String extAccount;

	private Date createdTime;

	private Integer extMethod;

}
