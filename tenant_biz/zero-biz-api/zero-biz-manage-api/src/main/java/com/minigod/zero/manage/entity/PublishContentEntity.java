package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Boolean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 帮助中心内容发布信息 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
@Data
@TableName("oms_publish_content")
@ApiModel(value = "PublishContent对象", description = "帮助中心内容发布信息")
public class PublishContentEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

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
     * 点击阅读次数
     */
    @ApiModelProperty(value = "点击阅读次数")
    private Integer clicks;
	/**
	 * 简体-详情标题
	 */
	@ApiModelProperty(value = "简体-详情标题")
	@NotBlank(message = "简体-详情标题不能为空")
	private String title;
	/**
	 * 繁体-标题
	 */
	@ApiModelProperty(value = "繁体-详情标题")
	private String titleHant;

	/**
	 * 英文-标题
	 */
	@ApiModelProperty(value = "英文-详情标题")
	private String titleEn;
	/**
	 * 简体-详情内容
	 */
	@ApiModelProperty(value = "简体-详情内容")
	@NotBlank(message = "简体-详情内容不能为空")
	private String content;
	/**
	 * 繁体-内容
	 */
	@ApiModelProperty(value = "繁体-详情内容")
	private String contentHant;
	/**
	 * 英文-内容
	 */
	@ApiModelProperty(value = "英文-详情内容")
	private String contentEn;
}
