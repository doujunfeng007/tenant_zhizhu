package com.minigod.zero.biz.common.vo.news.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MarketNewRespVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "文章编号")
	private Long newsId;// 文章编号
	@ApiModelProperty(value = "标题")
	private String title;// 标题
	@ApiModelProperty(value = "信息发布日期")
	private String date;// 信息发布日期
	@ApiModelProperty(value = "标签")
	private Integer tag;// 标签
	@ApiModelProperty(value = "列表显示图片")
	private String imgUrl;//列表显示图片
	@ApiModelProperty(value = "股票代码")
	private String stkCode;//股票代码
	@ApiModelProperty(value = "股票名称")
	private String stkName;//股票名称
	@ApiModelProperty(value = "概要")
	private String summary;//概要
	@ApiModelProperty(value = "自定义标签")
	private String label;//自定义标签
	@ApiModelProperty(value = "来源")
	private String media;//来源
	@ApiModelProperty(value = "展示时间")
	private String showDate;//展示时间
	@ApiModelProperty(value = "img数量")
	private int imgNum; //transient
	@ApiModelProperty(value = "是否是要闻")
	private Boolean isImportant;
	@ApiModelProperty(value = "发布时间")
	private Date issueTime;
	@ApiModelProperty(value = "是否置顶")
	private Boolean isTop;
	@ApiModelProperty(value = "是否头条")
	private Boolean isHeadline;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "类目")
	private String category;
	@ApiModelProperty(value = "存储资讯关联股票，用于全文索引搜索自选股资讯")
	private String gp;
}
