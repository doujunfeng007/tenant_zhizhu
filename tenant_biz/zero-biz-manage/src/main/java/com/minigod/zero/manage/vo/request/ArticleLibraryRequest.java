package com.minigod.zero.manage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(value = "协议文章库新增/修改参数")
public class ArticleLibraryRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID")
	private Long id;
	/**
	 * KeyID
	 */
	@ApiModelProperty(value = "KeyID")
	private String keyId;

	/**
	 * 文章标识
	 */
	@ApiModelProperty(value = "文章标识")
	private String identification;
	/**
	 * 简体-关键词
	 */
	@ApiModelProperty(value = "简体-关键词")
	private String keyWord;
	/**
	 * 繁体-关键词
	 */
	@ApiModelProperty(value = "繁体-关键词")
	private String keyWordHant;
	/**
	 * 英文-关键词
	 */
	@ApiModelProperty(value = "英文-关键词")
	private String keyWordEn;
	/**
	 * 关联文章ID
	 */
	@ApiModelProperty(value = "关联文章ID")
	private Integer relatedId;
	/**
	 * 缩略图地址
	 */
	@ApiModelProperty(value = "缩略图地址")
	private String thumImg;

	/**
	 * 简体-摘要
	 */
	@ApiModelProperty(value = "简体-摘要")
	private String summary;
	/**
	 * 繁体-摘要
	 */
	@ApiModelProperty(value = "繁体-摘要")
	private String summaryHant;
	/**
	 * 英文-摘要
	 */
	@ApiModelProperty(value = "英文-摘要")
	private String summaryEn;

	/**
	 * 简体-标题
	 */
	@ApiModelProperty(value = "简体-标题")
	@NotBlank(message = "简体-标题不能为空")
	private String title;
	/**
	 * 繁体-标题
	 */
	@ApiModelProperty(value = "繁体-标题")
	private String titleHant;
	/**
	 * 英文-标题
	 */
	@ApiModelProperty(value = "英文-标题")
	private String titleEn;

	/**
	 * 简体-内容
	 */
	@ApiModelProperty(value = "简体-内容")
	@NotBlank(message = "简体-内容不能为空")
	private String content;
	/**
	 * 繁体-内容
	 */
	@ApiModelProperty(value = "繁体-内容")
	private String contentHant;
	/**
	 * 英文-内容
	 */
	@ApiModelProperty(value = "英文-内容")
	private String contentEn;

	/**
	 * 作者
	 */
	@ApiModelProperty(value = "作者")
	private String author;

	/**
	 * 修改人名
	 */
	@ApiModelProperty(value = "修改人名")
	private String updateUserName;
}
