package com.minigod.zero.cms.entity.oms;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 帮助中心内容发布信息 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
@Data
@TableName("oms_publish_item")
@ApiModel(value = "PublishItem对象", description = "帮助中心内容发布信息")
public class PublishItemEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /**
     * 目录名称
     */
    @ApiModelProperty(value = "菜单名称")
	@NotBlank(message = "菜单名称不能为空")
    private String name;
    /**
     * 父目录ID
     */
    @ApiModelProperty(value = "父目录ID")
    private Long parentId;
    /**
     * 是否为叶子节点 1:是 0:否
     */
    @ApiModelProperty(value = "是否为叶子节点")
    private Boolean isLeaf;
    /**
     * 图标
     */
    @ApiModelProperty(value = "菜单图片")
    private String picon;
    /**
     * 目录描述
     */
    @ApiModelProperty(value = "目录描述")
    private String message;
    /**
     * 是否可分享1:是 0:否
     */
    @ApiModelProperty(value = "是否可分享")
	@NotNull(message = "是否可分享不能为空")
    private Boolean isShare;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
	@NotNull(message = "排序不能为空")
    private Integer sortId;
	/**
	 * 繁体-目录名称
	 */
	@ApiModelProperty(value = "繁体-菜单名称")
	private String nameHant;
	/**
	 * 繁体-目录描述
	 */
	@ApiModelProperty(value = "繁体-目录描述")
	private String messageHant;
	/**
	 * 英文-目录名称
	 */
	@ApiModelProperty(value = "英文-菜单名称")
	private String nameEn;
	/**
	 * 英文-目录描述
	 */
	@ApiModelProperty(value = "英文-目录描述")
	private String messageEn;
}
