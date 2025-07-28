/**
 * @Title: SysMsgRespVO.java
 * @Copyright: © 2015 sunline
 * @Company: sunline
 */

package com.minigod.zero.platform.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description
 *
 * @author Sunline
 * @date 2015-4-22 下午4:38:21
 * @version v1.0
 */
@Data
public class SysMsgRespVO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long version;
	private List<SysMsgRespVO_data> data;

	@Data
	public static class SysMsgRespVO_data{
		private Long msgId;
		private String url;
		private String msgTitle;
		private String msgType;
		private String msgContent;
		private Long ts;
		private Integer isRead;
		private Integer status;
		private String msgLev;
	}
}
