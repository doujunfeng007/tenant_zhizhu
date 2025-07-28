/**
 * @Title: MarketNewVO.java
 * @Copyright: © 2015 sunline
 * @Company: sunline
 */

package com.minigod.zero.biz.common.cache;


import com.minigod.zero.biz.common.vo.news.response.MarketNewRespVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class MarketNewsVO extends MarketNewRespVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "是否是要闻")
	private Boolean isImportant;// 是否是要闻
	@ApiModelProperty(value = "是否是直播")
	private Boolean isLive;// 是否是直播
	@ApiModelProperty(value = "新闻类型")
	private Integer treeid;
	@ApiModelProperty(value = "繁体-新闻标题")
	private String titleHant;
	@ApiModelProperty(value = "繁体-摘要")
	private String summaryHant;
	@ApiModelProperty(value = "繁体-新闻内容")
	private String contentHant;
	@ApiModelProperty(value = "英文-新闻标题")
	private String titleEn;
	@ApiModelProperty(value = "英文-摘要")
	private String summaryEn;
	@ApiModelProperty(value = "英文-新闻内容")
	private String contentEn;

}
