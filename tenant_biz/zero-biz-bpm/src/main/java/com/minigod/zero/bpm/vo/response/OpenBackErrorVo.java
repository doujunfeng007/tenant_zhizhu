package com.minigod.zero.bpm.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class OpenBackErrorVo implements Serializable {

	private String code;

	private String desc;

}
