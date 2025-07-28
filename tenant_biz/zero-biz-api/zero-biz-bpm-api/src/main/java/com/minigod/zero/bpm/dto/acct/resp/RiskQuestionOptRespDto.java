package com.minigod.zero.bpm.dto.acct.resp;

import lombok.Data;

import java.io.Serializable;

@Data
public class RiskQuestionOptRespDto implements Serializable {

	private Integer optionId;

	private Integer sort;

	private Integer optionScore;

	private String optionValue;

}
