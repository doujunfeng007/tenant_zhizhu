package com.minigod.zero.manage.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BoardLinkRespVO implements Serializable{
	private static final long serialVersionUID = 1L;

	private List<BoardLinkRespVOData> posCodes;

	private Long version;

	@Data
	public static class BoardLinkRespVOData implements Serializable{

		private static final long serialVersionUID = 1L;
		/**
		 * 公告位
		 */
		@ApiModelProperty(value = "公告位")
		private String[] posCode;
		/**
		 * 公告id
		 */
		@ApiModelProperty(value = "公告id")
		private Long boardId;
		/**
		 * 1休市广告条
		 */
		@ApiModelProperty(value = "公告类型 [1-休市广告条 2新股信号公告]")
		private Integer boardType;
		/**
		 * 链接
		 */
		@ApiModelProperty(value = "链接")
		private String url;
		/**
		 * 内容
		 */
		@ApiModelProperty(value = "内容")
		private String desc;
		/**
		 * 优先级
		 */
		@ApiModelProperty(value = "优先级")
		private Integer priority;
		/**
		 * 1休市广告条
		 */
		@ApiModelProperty(value = "公告类型描述")
		private String tag;
		/**
		 * pc公告地址
		 */
		@ApiModelProperty(value = "pc公告地址")
		private String pcLink;
	}


}
