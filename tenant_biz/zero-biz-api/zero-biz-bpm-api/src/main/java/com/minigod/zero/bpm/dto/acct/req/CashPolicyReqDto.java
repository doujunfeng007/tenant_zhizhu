package com.minigod.zero.bpm.dto.acct.req;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CashPolicyReqDto implements Serializable {

	private Integer id;

	private String clientName;

	private String clientNameSpell;

	private String tradeAccount;

	private String payType;

	private String policyNo;

	private String currency;

	private String remark;

	private String policyName;

	private String bankNo;

	private String fundAccount;

	private Long custId;

	private Integer status;

	private String bankName;

	private BigDecimal amount;
}
