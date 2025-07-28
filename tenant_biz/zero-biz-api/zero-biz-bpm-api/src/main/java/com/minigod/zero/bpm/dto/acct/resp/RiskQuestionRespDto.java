package com.minigod.zero.bpm.dto.acct.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RiskQuestionRespDto implements Serializable {

	private Integer id;

	private String question;

	private Integer questionId;

	private Integer sort;

	private String lang;

	private Integer checkType;

	private Integer optType;

	private List<RiskQuestionOptRespDto> options;

}
