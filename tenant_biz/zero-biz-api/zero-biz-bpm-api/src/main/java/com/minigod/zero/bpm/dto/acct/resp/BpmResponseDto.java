package com.minigod.zero.bpm.dto.acct.resp;

import lombok.Data;

import java.io.Serializable;

@Data
public class BpmResponseDto implements Serializable {

	private Integer code;

	/**
	 * 错误信息
	 */
	private String message;

	/**
	 * 返回对象
	 */
	private Object result;
}
