package com.minigod.zero.cms.entity.oms;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 帮助中心目录配置表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
@Data
@TableName("oms_publish_content")
@ApiModel(value = "PublishContent对象", description = "帮助中心目录配置表")
public class PublishContentEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    @ApiModelProperty(value = "详情标题")
	@NotBlank(message = "详情标题不能为空")
    private String title;
    /**
     * 内容
     */
    @ApiModelProperty(value = "详情内容")
	@NotBlank(message = "详情内容不能为空")
    private String content;
    /**
     * 对应目录ID
     */
    @ApiModelProperty(value = "对应目录ID")
	@NotNull(message = "对应目录ID不能为空")
    private Long resourceId;
    /**
     * 是否可分享1:是 0:否
     */
    @ApiModelProperty(value = "是否可分享")
	@NotNull(message = "是否可分享不能为空")
    private Boolean isShare;
    /**
     * 是否是常见问题 1:是 0：否
     */
    @ApiModelProperty(value = "是否是常见问题")
	@NotNull(message = "是否是常见问题不能为空")
    private Boolean isCommon;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sortId;
    /**
     * 次数
     */
    @ApiModelProperty(value = "次数")
    private Integer clicks;
	/**
	 * 繁体-标题
	 */
	@ApiModelProperty(value = "繁体-详情标题")
	private String titleHant;
	/**
	 * 繁体-内容
	 */
	@ApiModelProperty(value = "繁体-详情内容")
	private String contentHant;
	/**
	 * 繁体-标题
	 */
	@ApiModelProperty(value = "繁体-详情标题")
	private String titleEn;
	/**
	 * 繁体-内容
	 */
	@ApiModelProperty(value = "繁体-详情内容")
	private String contentEn;
}
