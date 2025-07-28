package com.minigod.zero.manage.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 菜单配置参数对象
 *
 * @author eric
 * @since 2024-09-27 15:04:08
 */
@Data
public class PublishItemRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID")
	private Long id;
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
	 * 图标
	 */
	@ApiModelProperty(value = "菜单图片")
	private String picon;
	/**
	 * 目录名称
	 */
	@ApiModelProperty(value = "菜单名称")
	@NotBlank(message = "菜单名称不能为空")
	private String name;
	/**
	 * 繁体-目录名称
	 */
	@ApiModelProperty(value = "繁体-菜单名称")
	private String nameHant;
	/**
	 * 英文-目录名称
	 */
	@ApiModelProperty(value = "英文-菜单名称")
	private String nameEn;
	/**
	 * 目录描述
	 */
	@ApiModelProperty(value = "目录描述")
	private String message;
	/**
	 * 繁体-目录描述
	 */
	@ApiModelProperty(value = "繁体-目录描述")
	private String messageHant;

	/**
	 * 英文-目录描述
	 */
	@ApiModelProperty(value = "英文-目录描述")
	private String messageEn;
	/**
	 * 业务状态
	 */
	@ApiModelProperty("业务状态")
	private Integer status;
}
