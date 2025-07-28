package com.minigod.zero.bpm.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class FundRiskQuestionOptionVo implements Serializable {
	private Integer questionId;
	private Integer optionId;
	private String  optionValue;
}
