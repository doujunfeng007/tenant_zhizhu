package com.minigod.zero.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 资讯新闻信息表 实体类
 *
 * @author 掌上智珠
 * @since 2022-11-24
 */
@Data
@TableName("cms_news_info")
@ApiModel(value = "NewsInfo对象", description = "资讯新闻信息表")
@EqualsAndHashCode(callSuper = true)
@Alias("cmsNewsInfoEntity")
public class NewsInfoEntity extends BaseEntity {

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    private Date issueTime;
    /**
     * 新闻来源
     */
    @ApiModelProperty(value = "新闻来源")
	@NotBlank(message = "新闻来源不能为空")
    private String newsSource;
    /**
     * 新闻标题
     */
    @ApiModelProperty(value = "新闻标题")
	@NotBlank(message = "新闻标题不能为空")
	@Size(max = 50, message = "新闻标题不能超过50个字")
    private String title;
    /**
     * 新闻内容
     */
    @ApiModelProperty(value = "新闻内容")
    private String content;
    /**
     * 来源地址
     */
    @ApiModelProperty(value = "网址")
	@Size(max = 200, message = "网址最多200字符")
    private String url;
    /**
     * 数据来源
     */
    @ApiModelProperty(value = "数据来源")
    private String dataSource;
    /**
     * 是否确认
     */
    @ApiModelProperty(value = "是否确认")
    private Boolean isConfirm;
    /**
     * 是否修改
     */
    @ApiModelProperty(value = "是否修改")
    private Boolean isRevise;
    /**
     * 是否要闻
     */
    @ApiModelProperty(value = "是否要闻")
    private Boolean isImportant;
    /**
     * 8-头条 9-热点资讯
     */
    @ApiModelProperty(value = "8-头条 9-热点资讯")
    private Integer tag;
    /**
     * 是否直播
     */
    @ApiModelProperty(value = "是否直播")
    private Boolean isLive;
    /**
     * 资讯来源的外部ID
     */
    @ApiModelProperty(value = "资讯来源的外部ID")
    private String extId;
    /**
     * 外部的时间
     */
    @ApiModelProperty(value = "外部的时间")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    private Date extTime;
    /**
     * 错误次数
     */
    @ApiModelProperty(value = "错误次数")
    private Integer errorTimes;
    /**
     * 存储资讯关联股票，用于全文索引搜索自选股资讯
     */
    @ApiModelProperty(value = "存储资讯关联股票，用于全文索引搜索自选股资讯")
    private String gp;
    /**
     * 资讯类型[6-要闻 7-快讯 22-普通资讯]
     */
    @ApiModelProperty(value = "资讯类型[6-要闻 7-快讯 22-普通资讯]")
	@NotNull(message = "资讯类型不能为空")
    private Integer infotreeid;
    /**
     * 列表图片url
     */
    @ApiModelProperty(value = "列表图片url")
    private String imgUrl;
    /**
     * 摘要
     */
    @ApiModelProperty(value = "摘要")
	@Size(max = 120, message = "概要不能超过120个字")
    private String summary;
    /**
     * 公告等文件的url
     */
    @ApiModelProperty(value = "公告等文件的url")
    private String pdfUrl;
    /**
     * 公告等文件的大小
     */
    @ApiModelProperty(value = "公告等文件的大小")
    private Long pdfSize;
    /**
     * 新闻作者
     */
    @ApiModelProperty(value = "新闻作者")
    private String authorName;
    /**
     * 外部标签
     */
    @ApiModelProperty(value = "外部标签")
    private String label;
    /**
     * 作者图像
     */
    @ApiModelProperty(value = "作者图像")
    private String authorImg;
    /**
     * 是否百科
     */
    @ApiModelProperty(value = "是否百科")
    private Boolean isWiki;
    /**
     * 是否置顶，0:否, 1:是
     */
    @ApiModelProperty(value = "是否置顶")
    private Boolean isTop;
    /**
     * 发布结束时间
     */
    @ApiModelProperty(value = "发布结束时间")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    private Date issueEndTime;
    /**
     * 渠道
     */
    @ApiModelProperty(value = "渠道")
    private Integer channelId;
    /**
     * 前端页面的跳转方式（1:内部浏览器，2:外部浏览器）
     */
    @ApiModelProperty(value = "前端页面的跳转方式（1:内部浏览器，2:外部浏览器）")
    private Integer jumpMode;
    /**
     * 显示方式（0:未设置，1:默认显示资讯内容，2:显示自定义链接）
     */
    @ApiModelProperty(value = "显示方式（0:未设置，1:默认显示资讯内容，2:显示自定义链接）")
    private Integer showType;
    /**
     * 自定义链接
     */
    @ApiModelProperty(value = "自定义链接")
    private String customUrl;
    /**
     * 结束发布时间
     */
    @ApiModelProperty(value = "结束发布时间")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    private Date endTime;

	/**
	 * 是否头条[0-否 1-是]
	 */
	@ApiModelProperty(value = "是否头条")
	private Boolean isHeadline;

	@ApiModelProperty(value = "繁体-新闻标题")
	@Size(max = 50, message = "繁体-新闻标题不能超过50个字")
	private String titleHant;

	@ApiModelProperty(value = "繁体-摘要")
	@Size(max = 120, message = "繁体-概要不能超过120个字")
	private String summaryHant;

	@ApiModelProperty(value = "繁体-新闻内容")
	private String contentHant;

	@ApiModelProperty(value = "英文-新闻标题")
	@Size(max = 150, message = "英文-新闻标题不能超过150个字母")
	private String titleEn;

	@ApiModelProperty(value = "英文-摘要")
	@Size(max = 120, message = "英文-概要不能超过120个字")
	private String summaryEn;

	@ApiModelProperty(value = "英文-新闻内容")
	private String contentEn;

	@ApiModelProperty(value = "类目")
	private String category;
	/**
	 * 自增id
	 */
	@ApiModelProperty("自增id")
	private Long logId;
}
