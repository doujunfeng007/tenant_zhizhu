package com.minigod.zero.bpm.dto.acct.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class RiskEvaluationReqDto implements Serializable {

	private String riskScore;

	private Integer riskType;

	private Integer questionType;

	private String tempData;

	private String lang;
}
