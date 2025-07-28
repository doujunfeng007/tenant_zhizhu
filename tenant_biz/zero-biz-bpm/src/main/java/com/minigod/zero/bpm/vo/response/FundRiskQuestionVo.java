package com.minigod.zero.bpm.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class FundRiskQuestionVo implements Serializable {
	private String question;
	private String optionValue;
	private Integer questionId;
}

