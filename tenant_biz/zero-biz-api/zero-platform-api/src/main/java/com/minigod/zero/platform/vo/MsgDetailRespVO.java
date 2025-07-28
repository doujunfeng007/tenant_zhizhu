package com.minigod.zero.platform.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Zhe.Xiao
 */
@Data
public class MsgDetailRespVO implements Serializable {

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 时间
	 */
	private Long ts;
}
