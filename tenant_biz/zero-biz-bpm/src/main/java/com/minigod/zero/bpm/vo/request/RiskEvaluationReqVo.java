package com.minigod.zero.bpm.vo.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class RiskEvaluationReqVo implements Serializable {

	private Long custId;

	private Long id;

	private Integer questionType;

	private Integer questionId;

	private Integer optionId;


}
