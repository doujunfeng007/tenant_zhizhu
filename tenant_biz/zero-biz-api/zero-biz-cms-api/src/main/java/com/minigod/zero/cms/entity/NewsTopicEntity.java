package com.minigod.zero.cms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 新闻专题定义表 实体类
 *
 * @author 掌上智珠
 * @since 2022-11-24
 */
@Data
@TableName("cms_news_topic")
@Alias("cms_news_topic")
@ApiModel(value = "NewsTopic对象", description = "新闻专题定义表")
@EqualsAndHashCode(callSuper = true)
public class NewsTopicEntity extends BaseEntity {

    /**
     * 专题名称
     */
    @ApiModelProperty(value = "专题标题")
	@NotBlank(message = "专题标题不能为空")
	@Size(max = 10, message = "专题标题最多10个字")
    private String topicTitle;
    /**
     * 专题类型
     */
    @ApiModelProperty(value = "专题类型")
	@NotNull(message = "专题类型不能为空")
    private Integer topicType;
    /**
     * 专题描述
     */
    @ApiModelProperty(value = "专题副标题")
	@NotBlank(message = "专题副标题不能为空")
	@Size(max = 25, message = "专题副标题最多25个字")
    private String topicDesc;
    /**
     * 专题标签
     */
    @ApiModelProperty(value = "专题标签")
    private Long topicTag;
    /**
     * 专题图片
     */
    @ApiModelProperty(value = "专题图片")
    private String topicImg;
    /**
     * 专题排序
     */
    @ApiModelProperty(value = "专题排序")
    private Integer topicSort;
    /**
     * 是否推荐
     */
    @ApiModelProperty(value = "是否推荐")
	@NotNull(message = "是否推荐不能为空")
    private Boolean isRcd;
    /**
     * 推荐专题标签
     */
    @ApiModelProperty(value = "角标文案")
    private String rcdTag;
    /**
     * 推荐专题图片
     */
    @ApiModelProperty(value = "推荐专题图片")
    private String rcdImg;
    /**
     * 推荐开始时间
     */
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    @ApiModelProperty(value = "角标时效开始时间")
    private Date startTime;
    /**
     * 推荐结束时间
     */
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    @ApiModelProperty(value = "角标时效结束时间")
    private Date endTime;
    /**
     * 推荐列表排序
     */
    @ApiModelProperty(value = "推荐列表排序")
    private Integer rcdSort;
    /**
     * 操作人ID
     */
    @ApiModelProperty(value = "操作人ID")
    private Long oprId;
    /**
     * 操作人姓名
     */
    @ApiModelProperty(value = "操作人姓名")
    private String oprName;
	/**
	 * 繁体-专题名称
	 */
	@ApiModelProperty(value = "繁体-专题名称")
	private String topicTitleHant;
	/**
	 * 繁体-专题描述
	 */
	@ApiModelProperty(value = "繁体-专题描述")
	private String topicDescHant;
	/**
	 * 英文-专题名称
	 */
	@ApiModelProperty(value = "英文-专题名称")
	private String topicTitleEn;
	/**
	 * 英文-专题描述
	 */
	@ApiModelProperty(value = "英文-专题描述")
	private String topicDescEn;
}
