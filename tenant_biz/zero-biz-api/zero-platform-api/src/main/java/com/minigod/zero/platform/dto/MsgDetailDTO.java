package com.minigod.zero.platform.dto;

import lombok.Data;

/**
 * @author Zhe.Xiao
 */
@Data
public class MsgDetailDTO extends PlatformOpenCommonDTO {

	/**
	 * 消息ID
	 */
	private String msgId;

	/**
	 * 消息所属分组
	 */
	private Integer messageGroup;

}
