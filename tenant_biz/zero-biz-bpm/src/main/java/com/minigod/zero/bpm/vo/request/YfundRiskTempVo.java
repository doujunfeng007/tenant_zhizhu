package com.minigod.zero.bpm.vo.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class YfundRiskTempVo implements Serializable {

	private Long custId;
	private String tempData;
	private Date time;
}
