package com.minigod.zero.biz.common.vo.news.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class NewsInfoRespVO implements Serializable {
	private static final long serialVersionUID = 1520301891932295562L;
	@ApiModelProperty(value = "新闻id")
	private Long newsId;
	@ApiModelProperty(value = "标题")
	private String title;
	@ApiModelProperty(value = "信息发布日期")
	private String date;
	@ApiModelProperty(value = "股票代码")
	private String stkCode;
	@ApiModelProperty(value = "股票名称")
	private String stkName;
	@ApiModelProperty(value = "列表图片url")
	private String imgUrl;
	@ApiModelProperty(value = "从cjzx表里同步的分类栏目ID")
	private Integer infotreeid;
	@ApiModelProperty(value = "新闻来源")
	private String media;
	@ApiModelProperty(value = "标签")
	private Integer tag;
	@ApiModelProperty(value = "是否百科")
	private Boolean isWiki;
	@ApiModelProperty(value = "细分类别")
	private Integer secStype;
	@ApiModelProperty(value = "是否中华通")
	private Boolean isZht;
}
